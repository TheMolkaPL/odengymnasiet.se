package se.odengymnasiet.admin;

import se.odengymnasiet.Application;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

public class AdminController extends Controller {

    public AdminController(Application application,
                           Request request,
                           Response response) {
        super(application, request, response);
    }

    @Route("/")
    public Object index() {
        return "Admin Control Panel";
    }

    @Route("/login")
    public Object login() {
        return "Log In";
    }

    @Route("/logout")
    public Object logout() {
        return "Log Out";
    }

    @Route("/reset")
    public Object reset() {
        return "Reset Password";
    }
}
