package se.odengymnasiet.route;

import se.odengymnasiet.Application;

import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Container which stores a map where the key is the route (as a
 * {@link String}), and the value is its executor (as a {@link RouteExecutor}).
 */
public class RouteExecutorContainer {

    private final Application application;

    private final SortedMap<String, RouteExecutor> container;

    public RouteExecutorContainer(Application app) {
        this.application = app;

        this.container = new TreeMap<>();
    }

    public boolean install(String path, RouteExecutor executor) {
        if (this.container.containsKey(path)) {
            return false;
        }

        this.container.put(path, executor);
        return true;
    }

    public Set<String> keys() {
        return this.container.keySet();
    }

    public RouteExecutor of(String path) {
        return this.container.get(path);
    }

    public Collection<RouteExecutor> values() {
        return this.container.values();
    }
}
