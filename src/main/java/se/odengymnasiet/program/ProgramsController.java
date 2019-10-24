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

package se.odengymnasiet.program;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.article.NavigationItem;
import se.odengymnasiet.form.Attribute;
import se.odengymnasiet.form.Form;
import se.odengymnasiet.form.Namespace;
import se.odengymnasiet.openhouse.OpenHouse;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.route.RequestMethod;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProgramsController extends Controller<ProgramsManifest> {

    public static final String PROGRAM_INDEX_TITLE = "Om utbildningen";

    public ProgramsController(Application app,
                              ProgramsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        List<Program> programs = new ArrayList<>(this.getManifest()
                .getProgramRepository().findAll());
        Collections.sort(programs);

        Attributes attributes = Attributes.create()
                .add("programs", programs);
        return this.ok("programs/index", attributes, "Våra utbildningar");
    }

    @HttpRoute("/:program")
    public Object program() {
        ProgramsManifest manifest = this.getManifest();

        String path = this.getRequest().params(":program");
        Program program = manifest.getProgramRepository().findByPath(path);

        if (program == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // article
        String articlePath = ArticlePaths.programs(program.getPath()) + "/";
        Article article = manifest.getArticleRepository()
                .findByPath(articlePath);
        if (article == null) {
            article = Article.NULL;
        }

        // pages
        List<NavigationItem> pages = NavigationItem.list(
                manifest.getArticleRepository(),
                ArticlePaths.programs(program.getPath()) + "/",
                articlePath,
                PROGRAM_INDEX_TITLE);

        // open houses
        List<OpenHouse> openHouses = new ArrayList<>(
                manifest.getOpenHouseRepository().findAllDeployedComing());
        openHouses = openHouses.stream()
                .filter(openHouse -> {
                    ObjectId programId = program.getId();
                    return openHouse.getPrograms().contains(programId);
                })
                .collect(Collectors.toList());
        Collections.sort(openHouses);

        Attributes attributes = Attributes.create()
                .add("program", program)
                .add("article", article)
                .add("pages", pages)
                .add("openHouses", openHouses);
        return this.ok("programs/program", attributes, program.getTitle());
    }

    @HttpRoute("/:program/:page")
    public Object programPage() {
        ProgramsManifest manifest = this.getManifest();

        String path = this.getRequest().params(":program");
        String page = this.getRequest().params(":page");
        Program program = manifest.getProgramRepository().findByPath(path);

        Attributes attributes = Attributes.create()
                .add("program", program);

        if (program == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        } else if (page.equalsIgnoreCase("application")) {
            if (program.isOpen()) {
                return this.ok("programs/program_application", attributes,
                        "Anmälan till " + program.getTitle());
            } else {
                return this.ok("programs/program_application_closed",attributes,
                        "Anmälan till " + program.getTitle() + " är stängd");
            }
        }

        // article
        String articlePath = ArticlePaths.programs(program.getPath(),
                                                   page.toLowerCase());
        Article article = manifest.getArticleRepository()
                .findByPath(articlePath);
        if (article == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // pages
        List<NavigationItem> pages = NavigationItem.list(
                manifest.getArticleRepository(),
                ArticlePaths.programs(program.getPath()) + "/",
                articlePath,
                PROGRAM_INDEX_TITLE);

        // open houses
        List<OpenHouse> openHouses = new ArrayList<>(
                manifest.getOpenHouseRepository().findAllDeployedComing());
        openHouses = openHouses.stream()
                .filter(openHouse -> {
                    ObjectId programId = program.getId();
                    return openHouse.getPrograms().contains(programId);
                })
                .collect(Collectors.toList());
        Collections.sort(openHouses);

        attributes.add("article", article)
                  .add("pages", pages)
                  .add("openHouses", openHouses);
        return this.ok("programs/program", attributes, program.getTitle());
    }

    private List<NavigationItem> navigationItems(
            Program program, String now) {
        String path = ArticlePaths.programs(program.getPath()) + "/";
        List<Article> articles = new ArrayList<>(this.getManifest()
                .getArticleRepository().findAllByStartingPath(path));

        Optional<Article> indexMaybe = articles.stream()
                .filter(article -> article.getPath().equals(path))
                .findFirst();

        Article index;
        if (indexMaybe.isPresent()) {
            index = indexMaybe.get();
        } else {
            articles.add(index = Article.copyOf(Article.NULL));
        }

        index.setPath(path);
        index.setPriority(Integer.MAX_VALUE);
        index.setTitle("Om utbildningen");

        List<NavigationItem> items = new ArrayList<>();
        articles.forEach(article -> {
            String target = article.getPath();
            boolean active = article.getPath().equals(now);

            if (article.getPath().equals(path)) {
                target = target.substring(0, target.length() - 1);
            }

            items.add(new NavigationItem(article, target, active));
        });

        Collections.sort(items);
        return items;
    }

    @HttpRoute(value = "/:program/application", methods = RequestMethod.POST)
    public Object programApplicationPost() {
        String path = this.getRequest().params(":program");
        Program program = this.getManifest().getProgramRepository()
                .findByPath(path);

        Attributes attributes = Attributes.create()
                .add("program", program);

        if (program == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        } else if (!program.isOpen()) {
            return this.ok("programs/program_application_closed", attributes,
                    "Anmälan till " + program.getTitle() + " är stängd");
        }

        Form form = this.applicationForm();
        form.validate();

        return this.ok("programs/program_application_ok", attributes,
                "Tack för din anmälan till " + program.getTitle());
    }

    private Form applicationForm() {
        Namespace studentNamespace = Namespace.getNamespace("student");
        Namespace guardianNamespace = Namespace.getNamespace("guardian");
        Namespace schoolNamespace = Namespace.getNamespace("school");
        Namespace gradesNamespace = Namespace.getNamespace("grades");
        Namespace personNamespace = Namespace.getNamespace("person");
        Namespace feedbackNamespace = Namespace.getNamespace("feedback");

        return Form.builder()
                .request(this.getRequest()).response(this.getResponse())
                .namespace(studentNamespace, Arrays.asList(
                        Attribute.require("first_name"),
                        Attribute.require("last_name")))
                .namespace(guardianNamespace, Arrays.asList(
                        Attribute.require("first_name"),
                        Attribute.require("last_name")))
                .namespace(schoolNamespace, Arrays.asList(
                        Attribute.require(""),
                        Attribute.require("")))
                .namespace(gradesNamespace, Arrays.asList(
                        Attribute.require(""),
                        Attribute.require("")))
                .namespace(personNamespace, Arrays.asList(
                        Attribute.require(""),
                        Attribute.require("")))
                .namespace(feedbackNamespace, Arrays.asList(
                        Attribute.require(""),
                        Attribute.require("")))
                .build();
    }
}
