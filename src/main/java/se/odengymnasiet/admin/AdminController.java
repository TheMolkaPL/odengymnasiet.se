package se.odengymnasiet.admin;

import se.odengymnasiet.Application;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

public class AdminController extends Controller {

    public AdminController(Application app,
                           AdminManifest manifest,
                           Request request,
                           Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        return "Admin Control Panel";
    }

    @HttpRoute("/dashboard")
    public Object dashboard() {
        return "Dashboard";
    }

    @HttpRoute("/login")
    public Object login() {
        return "Log In";
    }

    @HttpRoute("/logout")
    public Object logout() {
        return "Log Out";
    }

    @HttpRoute("/reset")
    public Object reset() {
        return "Reset Password";
    }
}
