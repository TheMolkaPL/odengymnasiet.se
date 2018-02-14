package se.odengymnasiet;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.odengymnasiet.admin.DefaultAdminManifest;
import se.odengymnasiet.admin.TopicsManifest;
import se.odengymnasiet.article.ArticleAdminManifest;
import se.odengymnasiet.contact.ContactManifest;
import se.odengymnasiet.index.IndexManifest;
import se.odengymnasiet.openhouse.OpenHouseManifest;
import se.odengymnasiet.program.ProgramsManifest;
import se.odengymnasiet.route.RequestMethod;
import se.odengymnasiet.route.RouteExecutorContainer;
import se.odengymnasiet.student.StudentsManifest;
import se.odengymnasiet.util.DateTimeUtils;
import spark.ModelAndView;
import spark.Service;
import spark.TemplateEngine;
import spark.servlet.SparkApplication;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The website application main class.
 */
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
    private ManifestManager manifests;
    private final RepositoryContainer repositories;
    private boolean running = false;
    private final Service service;
    private TemplateEngine templateEngine;

    public Application(String[] args) {
        this.args = args;
        this.configuration = new Configuration();
        this.logger = LoggerFactory.getLogger(Application.class);
        this.manifests = new ManifestManager(this);
        this.repositories = new RepositoryContainer(this);
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

        this.database = this.loadDatabase(this.getConfiguration().database());
        this.templateEngine = this.loadTemplateEngine();

        this.registerDefaultManifests();

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
            System.exit(-1); // I'm not sure about this.
        });

        http.notFound((request, response) -> {
            ErrorPage errorPage = new ErrorPage(this);
            errorPage.setCode(response.status());
            errorPage.setMessage(ErrorPage.MESSAGE_NOT_FOUND);

            response.body(errorPage.render());
            return response.body();
        });

        http.internalServerError((request, response) -> {
            ErrorPage errorPage = new ErrorPage(this);
            errorPage.setCode(response.status());
            errorPage.setMessage(ErrorPage.MESSAGE_INTERNAL_ERROR);

            response.body(errorPage.render());
            return response.body();
        });

        http.init();
        http.awaitInitialization();

        this.getDatabase().installDefaultRepositories();

        this.getManifests().getManifests().forEach(manifest -> {
            try {
                manifest.installRepositories(this.getRepositories());
            } catch (Throwable th) {
                this.getLogger().error("Could not install repositories", th);
            }
        });

        this.loadRoutes();

        try {
            this.getDatabase().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getManifests().getManifests().forEach(manifest -> {
            try {
                manifest.enable();
            } catch (Throwable th) {
                this.getLogger().error("Could not handle 'enable' event on " +
                        manifest.getName() + ".", th);
            }
        });

        this.running = true;

        long initTimeTook = System.currentTimeMillis() - initTime;
        this.logger.info("Server started in " + initTimeTook +
                " ms on port " + http.port() + ".");
    }

    public void stop() {
        if (!this.running) {
            throw new IllegalStateException("Already Stopped!");
        }

        this.running = false;

        this.logger.info("Stopping the server...");
        long initTime = System.currentTimeMillis();

        this.getManifests().getManifests().forEach(manifest -> {
            try {
                manifest.disable();
            } catch (Throwable th) {
                this.getLogger().error("Could not handle 'disable' event on " +
                        manifest.getName() + ".", th);
            }
        });

        this.getService().stop();

        try {
            this.getDatabase().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public ManifestManager getManifests() {
        return this.manifests;
    }

    public RepositoryContainer getRepositories() {
        return this.repositories;
    }

    public Service getService() {
        return this.service;
    }

    public TemplateEngine getTemplateEngine() {
        return this.templateEngine;
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

    private void loadRoutes() {
        ManifestManager manifests = this.getManifests();

        for (RequestMethod requestMethod : RequestMethod.values()) {
            manifests.getManifests().forEach(manifest -> {
                String path = this.fetchManifestParentRoute(manifest);
                this.loadRoutesFromManifest(requestMethod, manifest, path);
            });
        }
    }

    private String fetchManifestParentRoute(Manifest<?> manifest) {
        String route = "";

        if (manifest != null) {
            Class<? extends Manifest> parent = manifest.getParent();
            if (parent != null) {
                Manifest<?> parentClass = this.getManifests().byClass(parent);
                if (parentClass != null) {
                    route += this.fetchManifestParentRoute(parentClass);
                    route += parentClass.getRoute();
                }
            }
        }

        return route;
    }

    private void loadRoutesFromManifest(RequestMethod requestMethod,
                                        Manifest<?> manifest,
                                        String parentRoute) {
        RouteExecutorContainer routes = manifest.getRoutes().get(requestMethod);
        routes.keys().forEach(path -> {
            String route = parentRoute + manifest.getRoute() + path;
            requestMethod.installRoute(this.getService(), route,
                    (request, response) -> routes.of(path)
                            .execute(path, request, response));
        });
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

        try {
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            config.setSharedVariable("DateTimeUtils", staticModels
                    .get(DateTimeUtils.class.getName()));
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }

        return new FreeMarkerEngine(config);
    }

    private void registerDefaultManifests() {
        Arrays.asList(
                ContactManifest.class,
                DefaultAdminManifest.class,
                IndexManifest.class,
                OpenHouseManifest.class,
                ProgramsManifest.class,
                StudentsManifest.class,
                TopicsManifest.class
        ).forEach(manifest -> this.getManifests().registerManifest(manifest));

        this.registerTopicManifests();
    }

    private void registerTopicManifests() {
        Arrays.asList(
                ArticleAdminManifest.class
        ).forEach(manifest -> this.getManifests().registerManifest(manifest));
    }
}
