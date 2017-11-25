package se.odengymnasiet.route;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RouteManager {

    public static final String ROUTES_FILE_PATH = "routes.xml";
    public static final RequestMethod REQUEST_METHOD = RequestMethod.GET;

    private final Map<String, Target> routes = new LinkedHashMap<>();

    public void findRouteMethods(String path, Class<?> from) {
        for (Method method : from.getDeclaredMethods()) {
            Route route = method.getDeclaredAnnotation(Route.class);
            if (route != null) {
                String fullPath = path + route.value();
                if (!path.isEmpty() && fullPath.endsWith("/")) {
                    fullPath = fullPath.substring(0, fullPath.length() - 1);
                }

                Accept accept = method.getDeclaredAnnotation(Accept.class);
                RequestMethod[] requestMethods = {REQUEST_METHOD};
                if (accept != null) {
                    requestMethods = accept.value();
                }

                this.routes.put(fullPath, new Target(method, requestMethods));
            }
        }
    }

    public Map<String, Target> getRoutes() {
        return new LinkedHashMap<>(this.routes);
    }

    public void readFile() {
        Document document;
        SAXBuilder builder = new SAXBuilder();
        try {
            document = builder.build(this.getClass().getClassLoader()
                    .getResourceAsStream(ROUTES_FILE_PATH));
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            return;
        }

        Element index = document.getRootElement().getChild("route");
        XMLRoute route = this.readRouteControllers(index);

        if (route != null) {
            this.installRoute("", route);
        }
    }

    private void installRoute(String path, XMLRoute route) {
        this.findRouteMethods(path + route.path, route.clazz);

        for (XMLRoute child : route.children) {
            this.installRoute(path, child);
        }
    }

    private XMLRoute readRouteControllers(Element xml) {
        try {
            XMLRoute route = new XMLRoute();
            route.clazz = Class.forName(xml.getAttributeValue("class"));
            route.setPath(xml.getAttributeValue("path"));

            xml.getChildren("route").forEach(element -> {
                XMLRoute child = readRouteControllers(element);
                if (child != null) {
                    route.children.add(child);
                }
            });

            return route;
        } catch (Exception e) {
            return null;
        }
    }

    private class XMLRoute {
        Class<?> clazz;
        String path;
        List<XMLRoute> children = new LinkedList<>();

        void setPath(String path) {
            if (path != null) {
                this.path = path;
            } else {
                this.path = "";
            }
        }
    }

    public class Target {
        private Method method;
        private RequestMethod[] requestMethods;

        public Target(Method method, RequestMethod[] requestMethods) {
            this.method = method;
            this.requestMethods = requestMethods;
        }

        public Method getMethod() {
            return this.method;
        }

        public RequestMethod[] getRequestMethods() {
            return this.requestMethods;
        }
    }
}
