package se.odengymnasiet.admin;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.route.RequestMethod;
import se.odengymnasiet.user.UserRepository;
import spark.Redirect;
import spark.Request;
import spark.Response;
import spark.Session;

public class DefaultAdminController
        extends AdminController<DefaultAdminManifest> {

    private final UserRepository userRepository;

    public DefaultAdminController(Application app,
                                  DefaultAdminManifest manifest,
                                  Request request,
                                  Response response) {
        super(app, manifest, request, response);

        this.userRepository = manifest.getUserRepository();
    }

    @HttpRoute("/")
    public Object index() {
        return "Admin Control Panel";
    }

    @HttpRoute("/dashboard")
    public Object dashboard() {
        return this.ok("admin/dashboard", Attributes.create(), "Dashboard");
    }

    @HttpRoute("/login")
    public Object login() {
        Attributes attributes = Attributes.create();
        return this.ok("admin/login", attributes, "Dashboard",
                Controller.DEFAULT_LAYOUT_NAME);
    }

    @HttpRoute(value = "/login", methods = RequestMethod.POST)
    public Object loginPost() {
        return this.getResponse().body();
    }

    @HttpRoute("/logout")
    public Object logout() {
        Session session = this.getRequest().session(false);
        if (session != null) {
            session.invalidate();
        }

        int statusCode = Redirect.Status.TEMPORARY_REDIRECT.intValue();
        this.getResponse().redirect("/", statusCode);
        return this.getResponse().body();
    }

    @HttpRoute("/reset-password")
    public Object resetPassword() {
        Attributes attributes = Attributes.create();
        return this.ok("admin/reset_password", attributes, "Glömt lösenord",
                Controller.DEFAULT_LAYOUT_NAME);
    }

    @HttpRoute(value = "/reset-password", methods = RequestMethod.POST)
    public Object resetPasswordPost() {
        return this.getResponse().body();
    }
}
