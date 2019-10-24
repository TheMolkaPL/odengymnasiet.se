/*
 * Copyright 2019 Aleksander Jagiełło <themolkapl@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.odengymnasiet.student;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.article.NavigationItem;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class StudentsController extends Controller<StudentsManifest> {

    public StudentsController(Application app,
                              StudentsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        StudentsManifest manifest = this.getManifest();

        // article
        String articlePath = ArticlePaths.students() + "/";
        Article article = manifest.getArticleRepository()
                .findByPath(articlePath);
        if (article == null) {
            article = Article.NULL;
        }

        // pages
        List<NavigationItem> pages = NavigationItem.list(
                manifest.getArticleRepository(),
                articlePath,
                articlePath,
                "För elever");

        // student services
        List<StudentService> studentServices = new ArrayList<>(
                manifest.getStudentServiceRepository().findAll());

        Attributes attributes = Attributes.create()
                .add("article", article)
                .add("pages", pages)
                .add("studentServices", studentServices);
        return this.ok("students/index", attributes, article.getTitle());
    }

    @HttpRoute("/:page")
    public Object page() {
        StudentsManifest manifest = this.getManifest();

        String path = this.getRequest().params(":page");

        // article
        String articlePath = ArticlePaths.students(path.toLowerCase());
        Article article = manifest.getArticleRepository()
                .findByPath(articlePath);
        if (article == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // pages
        List<NavigationItem> pages = NavigationItem.list(
                manifest.getArticleRepository(),
                ArticlePaths.students() + "/",
                articlePath,
                "För elever");

        // student services
        List<StudentService> studentServices = new ArrayList<>(
                manifest.getStudentServiceRepository().findAll());

        Attributes attributes = Attributes.create()
                .add("article", article)
                .add("pages", pages)
                .add("studentServices", studentServices);
        return this.ok("students/index", attributes, article.getTitle());
    }
}
