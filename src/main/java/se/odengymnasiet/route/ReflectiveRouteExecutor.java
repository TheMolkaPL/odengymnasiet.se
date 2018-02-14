package se.odengymnasiet.route;

import se.odengymnasiet.Controller;
import se.odengymnasiet.Manifest;
import spark.Request;
import spark.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A {@link RouteExecutor} which executes requests by invoked by the given
 * {@link Method} in the constructor.
 * @param <E> The {@link Controller} which this method is in.
 */
public class ReflectiveRouteExecutor<E extends Controller>
        implements RouteExecutor {

    private final Manifest<E> manifest;
    private final Method method;

    public ReflectiveRouteExecutor(Manifest<E> manifest, Method method) {
        this.manifest = manifest;
        this.method = method;
    }

    @Override
    public Object execute(String route,
                          Request request,
                          Response response) throws Exception {
        try {
            Object obj = this.getManifest().newController(request, response);
            if (obj != null) {
                return this.getMethod().invoke(obj);
            }
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof Exception) {
                throw (Exception) target;
            } else {
                throw new Exception(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.status(404); // Not found
        return response.status();
    }

    public Manifest<E> getManifest() {
        return this.manifest;
    }

    public Method getMethod() {
        return this.method;
    }
}
