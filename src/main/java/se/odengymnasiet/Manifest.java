package se.odengymnasiet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import se.odengymnasiet.index.Index;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.route.ReflectiveRouteExecutor;
import se.odengymnasiet.route.RequestMethod;
import se.odengymnasiet.route.RouteExecutor;
import se.odengymnasiet.route.RouteExecutorContainer;
import spark.Request;
import spark.Response;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Manifest<E extends Controller> {

    private Application application;

    private boolean initialized = false;
    private boolean index;
    private Class<E> master;
    private String name;
    private Class<? extends Manifest> parent;
    private String route;
    private final Map<RequestMethod, RouteExecutorContainer> routes;

    public Manifest() {
        this.routes = new HashMap<>();
    }

    public Application getApplication() {
        return this.application;
    }

    public Logger getLogger() {
        return this.getApplication().getLogger();
    }

    public Class<E> getMasterClass() {
        return this.master;
    }

    public String getName() {
        return this.name;
    }

    public Class<? extends Manifest> getParent() {
        return this.parent;
    }

    public String getRoute() {
        return this.route;
    }

    public Map<RequestMethod, RouteExecutorContainer> getRoutes() {
        return this.routes;
    }

    public boolean isIndex() {
        return this.index;
    }

    public Object newController(Request request,
                                Response response) throws Exception {
        try {
            Constructor<?> constructor = this.getMasterClass().getConstructor(
                    Application.class, this.getClass(),
                    Request.class, Response.class);
            constructor.setAccessible(true);

            return constructor.newInstance(
                    this.getApplication(), this, request, response);
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof Exception) {
                throw (Exception) target;
            } else {
                throw new Exception(target);
            }
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //
    // Events
    //

    public void load() {
    }

    public void enable() {
    }

    public void disable() {
    }

    //
    // Installers
    //

    public void installRepositories(RepositoryContainer repositories) {
    }

    //
    // Initializer
    //

    public final Manifest<E> init(Application app) {
        ManifestInfo info = this.getClass().getDeclaredAnnotation(
                ManifestInfo.class);
        if (info == null) {
            throw new IllegalStateException(this.getClass().getSimpleName() +
                    " must be @" + ManifestInfo.class.getSimpleName() +
                    " decorated!");
        }

        Index index = this.getClass().getDeclaredAnnotation(Index.class);

        return this.init(app, index != null, (Class<E>) info.master(),
                info.name(), info.parent(), info.route());
    }

    public final Manifest<E> init(Application app, boolean index, Class<E> master,
                           String name, Class<? extends Manifest> parent,
                           String route) {
        if (this.initialized) {
            throw new IllegalStateException("Already initialized!");
        }

        if (parent.equals(this.getClass())) {
            parent = null;
        }

        this.initialized = true;
        this.application = app;

        this.index = index;
        this.master = master;
        this.name = name;
        this.parent = parent;
        this.route = route;

        // Insert all HTTP request methods into the routes map.
        for (RequestMethod requestMethod : RequestMethod.values()) {
            this.routes.put(requestMethod, new RouteExecutorContainer(app));
        }

        this.findAndInstallRoutes();

        try {
            this.load();
        } catch (Throwable th) {
            this.getLogger().error("Could not handle 'load' event on " +
                    this.getName() + ".", th);
        }

        return this;
    }

    private void installRoute(Method method, HttpRoute info) throws Exception {
        for (RequestMethod requestMethod : info.methods()) {
            RouteExecutorContainer container = this.routes.get(requestMethod);
            RouteExecutor executor = new ReflectiveRouteExecutor<>(this,method);

            if (!container.install(info.value(), executor)) {
                this.getLogger().info("Could not install [{}] route '{}' " +
                                "from {} because it already exists!",
                        StringUtils.join(info.methods(), ", "),
                        this.getRoute() + info.value(),
                        method.getDeclaringClass().getSimpleName());
            }
        }
    }

    private void findAndInstallRoutes() {
        for (Method method : this.getMasterClass().getDeclaredMethods()) {
            try {
                method.setAccessible(true);

                HttpRoute info = method.getDeclaredAnnotation(HttpRoute.class);
                if (info != null) {
                    this.installRoute(method, info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
