package se.odengymnasiet.contact;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

public class ContactController extends Controller<ContactManifest> {

    public ContactController(Application app,
                             ContactManifest manifest,
                             Request request,
                             Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        return this.ok("contact/index", Attributes.create(), "Kontakt");
    }

    @HttpRoute("/staff")
    public Object staff() {
        return this.ok("contact/staff", Attributes.create(), "Personal");
    }

    @HttpRoute("/application")
    public Object application() {
        return this.ok("contact/application", Attributes.create(), "Ansökan");
    }

    private class DefaultGroup extends Group {

        @Override
        public String getName() {
            return "Default Group";
        }

        @Override
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }
}
