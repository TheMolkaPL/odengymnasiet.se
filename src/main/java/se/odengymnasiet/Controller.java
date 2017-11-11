package se.odengymnasiet;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller {
    private final Request request;
    private final Response response;

    public Controller(Request request, Response response) {
        this.request = request;
        this.response = response;
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

    public static String ok(String view) {
        return ok(view, null);
    }

    public static String ok(String view, String title) {
        return ok(view, null, title);
    }

    public static String ok(String view,
                            Map<String, Object> attributes,
                            String title) {
        return ok(view, attributes, title, null);
    }

    public static String ok(String view,
                            Map<String, Object> attributes,
                            String title,
                            String layoutDirectory) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        if (title == null) {
            title = "";
        }

        if (layoutDirectory != null) {
            layoutDirectory += "/";
        } else {
            layoutDirectory = "";
        }

        Map<String, Object> layoutAttributes = new HashMap<>();
        layoutAttributes.put("title", title);
        layoutAttributes.put("body", render(view, attributes).trim());

        return render(layoutDirectory + "_layout", layoutAttributes);
    }

    public static String render(String view) {
        return render(view, null);
    }

    public static String render(String view, Map<String, Object> attributes) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        ClassLoader classLoader = Controller.class.getClassLoader();

        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        config.setClassLoaderForTemplateLoading(classLoader, VIEWS_DIRECTORY);
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());

        ModelAndView modelAndView = new ModelAndView(attributes, view + ".ftl");
        return new FreeMarkerEngine(config).render(modelAndView);
    }
}
