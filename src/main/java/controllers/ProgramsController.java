package controllers;

import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ProgramsController extends Controller {
    public ProgramsController(Request request, Response response) {
        super(request, response);
    }

    @Route("/")
    public Object index() {
        return ok("programs/index");
    }

    @Route("/:program")
    public Object program() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", this.getRequest().params(":program"));
        return ok("programs/program", attributes, this.getRequest().params(":program"));
    }
}
