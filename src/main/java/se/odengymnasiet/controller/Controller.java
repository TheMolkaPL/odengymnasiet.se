package se.odengymnasiet.controller;

import freemarker.template.Configuration;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller {
    private final Application application;
    private final Request request;
    private final Response response;

    public Controller(Application app, Request request, Response response) {
        this.application = app;
        this.request = request;
        this.response = response;
    }

    public Application getApplication() {
        return this.application;
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

        Attributes layoutAttributes = Attributes.create()
                .add("title", title)
                .add("body", this.render(view, attributes).trim());

        return this.render(LAYOUTS_DIRECTORY + "/" + layout, layoutAttributes);
    }

    public String render(String view) {
        return this.render(view, null);
    }

    public String render(String view, Map<String, Object> attributes) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        ClassLoader classLoader = Controller.class.getClassLoader();

        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        config.setClassLoaderForTemplateLoading(classLoader, VIEWS_DIRECTORY);
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());
        config.setWhitespaceStripping(true);

        String viewsPath = this.getApplication().getConfiguration().viewsPath();
        if (viewsPath != null) {
            try {
                config.setDirectoryForTemplateLoading(new File(viewsPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ModelAndView modelAndView = new ModelAndView(attributes, view + ".ftl");
        return new FreeMarkerEngine(config).render(modelAndView);
    }
}
