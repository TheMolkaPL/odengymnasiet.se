package se.odengymnasiet.route;

import spark.Request;
import spark.Response;

public interface RouteExecutor {

    Object execute(String route,
                   Request request,
                   Response response) throws Exception;
}
