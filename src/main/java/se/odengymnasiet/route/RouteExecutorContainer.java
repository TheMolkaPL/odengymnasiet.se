package se.odengymnasiet.route;

import se.odengymnasiet.Application;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RouteExecutorContainer {

    private final Application application;

    private final Map<String, RouteExecutor> container;

    public RouteExecutorContainer(Application app) {
        this.application = app;

        this.container = new LinkedHashMap<>();
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
