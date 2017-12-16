package se.odengymnasiet.admin;

import se.odengymnasiet.Application;
import se.odengymnasiet.route.HttpRoute;
import spark.Redirect;
import spark.Request;
import spark.Response;

public class TopicsController extends AdminController<TopicsManifest> {

    public TopicsController(Application app,
                            TopicsManifest manifest,
                            Request request,
                            Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        int statusCode = Redirect.Status.MOVED_PERMANENTLY.intValue();
        this.getResponse().redirect("/admin/dashboard", statusCode);
        return this.getResponse().body();
    }
}
