package se.odengymnasiet.contact;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

public class ContactController extends Controller {

    public ContactController(Application app,
                             Request request,
                             Response response) {
        super(app, request, response);
    }

    @Route("/")
    public Object index() {
        return this.ok("contact/index", Attributes.create(), "Kontakt");
    }

    @Route("/staff")
    public Object staff() {
        return this.ok("contact/staff", Attributes.create(), "Personal");
    }

    @Route("/application")
    public Object application() {
        return this.ok("contact/application", Attributes.create(), "Ansökan");
    }
}
