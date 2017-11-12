package se.odengymnasiet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.odengymnasiet.route.RequestMethod;
import se.odengymnasiet.route.RouteManager;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class Application {
    public static void main(String[] args) {
        Application application = new Application(args);
        application.start();
    }

    public static final String PUBLIC_ASSETS_PATH = "/public";

    private final String[] args;
    private final Configuration configuration;
    private final Logger logger;
    private final RouteManager routes;
    private final Service service;
    private boolean running = false;

    public Application(String[] args) {
        this.args = args;
        this.configuration = new Configuration();
        this.logger = LoggerFactory.getLogger(Application.class);
        this.routes = new RouteManager();
        this.service = Service.ignite();
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

        this.routes.getRoutes().forEach((path, target) -> {
            RequestMethod[] requestMethods = target.getRequestMethods();
            for (RequestMethod method : requestMethods) {
                method.installRoute(http, path, this.route(target.getMethod()));
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

        this.logger.info("Stopping the server...");
        long initTime = System.currentTimeMillis();

        this.getService().stop();

        this.running = false;

        long initTimeTook = System.currentTimeMillis() - initTime;
        this.logger.info("Server stopped in " + initTimeTook + " ms.");
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public RouteManager getRoutes() {
        return this.routes;
    }

    public Service getService() {
        return this.service;
    }

    public boolean isRunning() {
        return this.running;
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
}
