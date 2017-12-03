package se.odengymnasiet.route;

import spark.Route;
import spark.RouteImpl;
import spark.Service;
import spark.route.HttpMethod;

public enum RequestMethod {

    GET("GET", HttpMethod.get),
    HEAD("HEAD", HttpMethod.head),
    POST("POST", HttpMethod.post),
    PUT("PUT", HttpMethod.put),
    DELETE("DELETE", HttpMethod.delete),
    CONNECT("CONNECT", HttpMethod.connect),
    OPTIONS("OPTIONS", HttpMethod.options),
    TRACE("TRACE", HttpMethod.trace),
    PATCH("PATCH", HttpMethod.patch),
    ;

    private final String verb;
    private final spark.route.HttpMethod method;

    RequestMethod(String verb, spark.route.HttpMethod method) {
        this.verb = verb;
        this.method = method;
    }

    public String getVerb() {
        return this.verb;
    }

    public void installRoute(Service http, String path, Route route) {
        while (path.length() != 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        http.addRoute(this.method, RouteImpl.create(path, route));
    }
}
