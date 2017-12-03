package se.odengymnasiet;

import org.slf4j.Logger;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller<E extends Manifest> {

    private final Application application;

    private final E manifest;
    private final Request request;
    private final Response response;

    public Controller(Application app, E manifest,
                      Request request, Response response) {
        this.application = app;

        this.manifest = manifest;
        this.request = request;
        this.response = response;
    }

    public Application getApplication() {
        return this.application;
    }

    public Logger getLogger() {
        return this.getManifest().getLogger();
    }

    public E getManifest() {
        return this.manifest;
    }

    public Request getRequest() {
        return this.request;
    }

    public Response getResponse() {
        return this.response;
    }

    //
    // Rendering Views
    //

    public static final String VIEWS_DIRECTORY = "/views";
    public static final String LAYOUTS_DIRECTORY = "/layouts";

    public String ok(String view) {
        return this.ok(view, null);
    }

    public String ok(String view, String title) {
        return this.ok(view, null, title);
    }

    public String ok(String view,
                     Map<String, Object> attributes,
                     String title) {
        return this.ok(view, attributes, title, null);
    }

    public String ok(String view,
                     Map<String, Object> attributes,
                     String title,
                     String layout) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        if (layout == null) {
            layout = "application";
        }

        String appNav = this.getRequest().pathInfo().split("/", 3)[1];
        if (appNav.isEmpty()) {
            appNav = "index";
        }

        Attributes layoutAttributes = Attributes.create()
                .add("title", title)
                .add("body", this.render(view, attributes).trim())
                .add("app_nav", appNav.toLowerCase());

        return this.render(LAYOUTS_DIRECTORY + "/" + layout, layoutAttributes);
    }

    public String render(String view) {
        return this.render(view, null);
    }

    public String render(String view, Map<String, Object> attributes) {
        return this.getApplication().renderView(view, attributes);
    }
}
