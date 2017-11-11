package se.odengymnasiet.route;

import spark.Route;
import spark.Service;

import java.util.function.Consumer;
import java.util.function.Function;

public enum RequestMethod {
    GET(http -> path -> route -> http.get(path, route)),
    POST(http -> path -> route -> http.post(path, route)),
    PUT(http -> path -> route -> http.put(path, route)),
    DELETE(http -> path -> route -> http.delete(path, route)),
    OPTIONS(http -> path -> route -> http.options(path, route)),
    ;

    private final Function<Service, Function<String, Consumer<Route>>> route;

    RequestMethod(Function<Service, Function<String, Consumer<Route>>> route) {
        this.route = route;
    }

    public void installRoute(Service http, String path, Route route) {
        this.route.apply(http).apply(path).accept(route);
    }
}
