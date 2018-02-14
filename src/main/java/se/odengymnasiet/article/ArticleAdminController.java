package se.odengymnasiet.article;

import se.odengymnasiet.Application;
import se.odengymnasiet.admin.AdminController;
import spark.Request;
import spark.Response;

public class ArticleAdminController
        extends AdminController<ArticleAdminManifest> {

    public ArticleAdminController(Application app,
                                  ArticleAdminManifest manifest,
                                  Request request,
                                  Response response) {
        super(app, manifest, request, response);
    }
}
