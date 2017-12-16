package se.odengymnasiet.admin;

import se.odengymnasiet.Application;
import se.odengymnasiet.Controller;
import spark.Request;
import spark.Response;

public class AdminController<E extends AdminManifest> extends Controller<E> {

    public static final String ADMIN_DEFAULT_LAYOUT_NAME = "admin";

    public AdminController(Application app,
                           E manifest,
                           Request request,
                           Response response) {
        super(app, manifest, request, response);
    }

    @Override
    public String getDefaultLayout() {
        return ADMIN_DEFAULT_LAYOUT_NAME;
    }
}
