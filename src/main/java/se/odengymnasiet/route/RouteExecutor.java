package se.odengymnasiet.route;

import spark.Request;
import spark.Response;

/**
 * Something that can execute routes and handle requests.
 */
public interface RouteExecutor {

    Object execute(String route,
                   Request request,
                   Response response) throws Exception;
}
