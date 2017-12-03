package se.odengymnasiet.student;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

public class StudentsController extends Controller<StudentsManifest> {

    public StudentsController(Application app,
                              StudentsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        return this.ok("students/index", Attributes.create(), "För elever");
    }
}
