package se.odengymnasiet.controller;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

public class IndexController extends Controller {
    public IndexController(Application application,
                           Request request,
                           Response response) {
        super(application, request, response);
    }

    @Route("/")
    public Object index() {
        return this.ok("index", Attributes.create(), null);
    }

    @Route("/about")
    public Object about() {
        return this.ok("about", Attributes.create(), "Om Odengymnasiet");
    }

    @Route("/contact")
    public Object contact() {
        return this.ok("contact", Attributes.create(), "Kontakt");
    }
}
