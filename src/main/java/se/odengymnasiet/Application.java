package se.odengymnasiet;

import freemarker.template.Version;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.odengymnasiet.route.RequestMethod;
import se.odengymnasiet.route.RouteManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;
import spark.TemplateEngine;
import spark.servlet.SparkApplication;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class Application implements SparkApplication {

    public static void main(String[] args) {
        Application application = new Application(args);
        application.start();
    }

    public static final String PUBLIC_ASSETS_PATH = "/public";

    private final String[] args;
    private final Configuration configuration;
    private Database database;
    private final Logger logger;
    private final Map<Class<? extends Repository>, Repository> repositoryMap;
    private final RouteManager routes;
    private boolean running = false;
    private final Service service;
    private TemplateEngine templateEngine;

    public Application(String[] args) {
        this.args = args;
        this.configuration = new Configuration();
        this.logger = LoggerFactory.getLogger(Application.class);
        this.repositoryMap = new HashMap<>();
        this.routes = new RouteManager();
        this.service = Service.ignite();
    }

    @Override
    public void init() {
        this.start();
    }

    @Override
    public void destroy() {
        this.stop();
    }

    public void start() {
        if (this.running) {
            throw new IllegalStateException("Already started!");
        }

        this.logger.info("Starting the server...");
        long initTime = System.currentTimeMillis();

        SleepForeverThread sleepForeverThread = new SleepForeverThread();
        sleepForeverThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (this.running) {
                this.stop();
            }
        }, "Application Shutdown Hook"));

        this.configuration.readFile();
        this.routes.readFile();

        this.database = this.loadDatabase(this.getConfiguration().database());
        this.templateEngine = this.loadTemplateEngine();

        Service http = this.getService()
                .ipAddress(this.configuration.httpHost())
                .port(this.configuration.httpPort())
                .threadPool(this.configuration.httpMaxThreads(),
                            this.configuration.httpMinThreads(),
                            this.configuration.httpIdleTimeout());

        String assetsPath = this.configuration.httpAssetsPath();
        if (assetsPath != null) {
            http.externalStaticFileLocation(assetsPath);
        } else {
            http.staticFileLocation(PUBLIC_ASSETS_PATH);
        }

        http.initExceptionHandler((e) -> {
            this.logger.error("Exception caught: " + e.getMessage(), e);
            System.exit(-1);
        });

        http.notFound((request, response) -> {
            return response.status();
        });

        http.internalServerError((request, response) -> {
            return response.status();
        });

        http.init();
        http.awaitInitialization();

        this.getDatabase().installDefaultRepositories();

        this.routes.getRoutes().forEach((path, target) -> {
            RequestMethod[] requestMethods = target.getRequestMethods();
            for (RequestMethod method : requestMethods) {
                method.installRoute(http, path, this.route(target.getMethod()));
            }
        });

        try {
            this.getDatabase().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.running = true;

        long initTimeTook = System.currentTimeMillis() - initTime;
        this.logger.info("Server started in " + initTimeTook +
                " ms on port " + http.port() + ".");
    }

    public void stop() {
        if (!this.running) {
            throw new IllegalStateException("Already Stopped!");
        }

        this.logger.info("Stopping the server...");
        long initTime = System.currentTimeMillis();

        this.getService().stop();

        try {
            this.getDatabase().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.running = false;

        long initTimeTook = System.currentTimeMillis() - initTime;
        this.logger.info("Server stopped in " + initTimeTook + " ms.");
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public Database getDatabase() {
        return this.database;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public <T extends Repository> T getRepository(Class<T> repository) {
        return (T) this.repositoryMap.get(repository);
    }

    public RouteManager getRoutes() {
        return this.routes;
    }

    public Service getService() {
        return this.service;
    }

    public TemplateEngine getTemplateEngine() {
        return this.templateEngine;
    }

    public boolean installRepository(Repository<?> repository) {
        RepositoryHandler handler = repository.getClass()
                .getDeclaredAnnotation(RepositoryHandler.class);
        if (handler == null) {
            return false;
        }

        Class<? extends Repository> clazz = handler.value();
        if (this.repositoryMap.containsKey(clazz)) {
            return false;
        }

        this.repositoryMap.put(clazz, repository);
        this.getLogger().info("Repository {} has been installed as {}.",
                clazz.getSimpleName(),
                repository.getClass().getSimpleName());
        return true;
    }

    public boolean isRunning() {
        return this.running;
    }

    public String renderView(String view, Map<String, Object> attributes) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        return this.renderView(new ModelAndView(attributes, view + ".ftl"));
    }

    public String renderView(ModelAndView modelAndView) {
        return this.getTemplateEngine().render(modelAndView);
    }

    public Route route(Method method) {
        try {
            Constructor<?> constructor = method.getDeclaringClass()
                    .getConstructor(Application.class,
                                    Request.class,
                                    Response.class);
            constructor.setAccessible(true);

            return (request, response) -> {
                Object obj = constructor.newInstance(this, request, response);

                method.setAccessible(true);
                return method.invoke(obj);
            };
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

            return (request, response) -> {
                response.status(500);
                return response.status();
            };
        }
    }

    private Database loadDatabase(Element configuration) {
        if (configuration == null) {
            configuration = new Element("database");
        }

        String factory = configuration.getAttributeValue("factory");
        Logger databaseLogger = this.getLogger();

        Database value = null;
        if (factory != null) {
            try {
                Class<?> factoryClass = Class.forName(factory);
                if (factoryClass != null) {
                    Object object = factoryClass.newInstance();
                    if (object instanceof DatabaseFactory) {
                        value = ((DatabaseFactory) object).newDatabase(
                                this, databaseLogger, configuration);
                    }
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        if (value == null) {
            value = new LocalDatabase(this, databaseLogger);
        }

        return value;
    }

    private TemplateEngine loadTemplateEngine() {
        Version version = freemarker.template.Configuration.VERSION_2_3_23;
        freemarker.template.Configuration config =
                new freemarker.template.Configuration(version);

        ClassLoader classLoader = Application.class.getClassLoader();

        config.setClassLoaderForTemplateLoading(classLoader,
                                                Controller.VIEWS_DIRECTORY);
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());
        config.setWhitespaceStripping(true);

        String viewsPath = this.getConfiguration().viewsPath();
        if (viewsPath != null) {
            try {
                config.setDirectoryForTemplateLoading(new File(viewsPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new FreeMarkerEngine(config);
    }
}
