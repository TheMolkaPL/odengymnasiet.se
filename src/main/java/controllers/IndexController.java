package controllers;

import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class IndexController extends Controller {
    public IndexController(Request request, Response response) {
        super(request, response);
    }

    @Route("/")
    public Object index() {
        Map<String, Object> attributes = new HashMap<>();
        return ok("index", attributes, "Welcome");
    }

    @Route("/about")
    public Object about() {
        return "About";
    }

    @Route("/contact")
    public Object contact() {
        return "Contact";
    }

    @Route("/staff")
    public Object staff() {
        return "Staff";
    }
}
