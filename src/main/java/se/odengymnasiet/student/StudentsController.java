package se.odengymnasiet.student;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

public class StudentsController extends Controller {

    public StudentsController(Application app,
                              Request request,
                              Response response) {
        super(app, request, response);
    }

    @Route("/")
    public Object index() {
        return this.ok("students/index", Attributes.create(), "För elever");
    }
}
