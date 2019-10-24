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

package se.odengymnasiet.openhouse;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.program.Program;
import se.odengymnasiet.route.HttpRoute;
import spark.Redirect;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpenHouseController extends Controller<OpenHouseManifest> {

    public OpenHouseController(Application app,
                               OpenHouseManifest manifest,
                               Request request,
                               Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        int temporaryRedirect = Redirect.Status.TEMPORARY_REDIRECT.intValue();

        List<OpenHouse> openHouses = new ArrayList<>(this.getManifest()
                .getOpenHouseRepository().findAllDeployedComing());
        if (openHouses.isEmpty()) {
            // no open houses found, redirect to index
            this.getResponse().redirect("/", temporaryRedirect);
            return this.getResponse().body();
        } else if (openHouses.size() == 1) {
            // one open house found - redirect to it
            OpenHouse openHouse = openHouses.get(0);
            this.getResponse().redirect("/open-house/" +
                    openHouse.getId().toHexString(), temporaryRedirect);
            return this.getResponse().body();
        } else {
            // more than one open houses found, display them
            Attributes attributes = Attributes.create()
                    .add("openHouses", openHouses);
            return this.ok("open-houses/index", attributes, "Öppet hus");
        }
    }

    @HttpRoute("/:open-house")
    public Object openHouse() {
        OpenHouseManifest manifest = this.getManifest();

        String path = this.getRequest().params(":open-house");
        ObjectId objectId;
        try {
            objectId = new ObjectId(path);
        } catch (IllegalArgumentException e) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // open house
        OpenHouse openHouse = manifest.getOpenHouseRepository().find(objectId);
        if (openHouse == null || !openHouse.isDeployed() ||
                openHouse.getPrograms().isEmpty()) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // programs
        List<Program> programs = new ArrayList<>();
        openHouse.getPrograms().forEach(programId -> {
            Program program = manifest.getProgramRepository().find(programId);
            if (program != null) {
                programs.add(program);
            }
        });
        Collections.sort(programs);

        // contact
        Article contact = manifest.getArticleRepository()
                .findByPath(ArticlePaths.contact());

        Attributes attributes = Attributes.create()
                .add("openHouse", openHouse)
                .add("programs", programs)
                .add("contact", contact);
        return this.ok("open-houses/open_house", attributes,
                this.openHousePageTitle(openHouse.getStartTime()));
    }

    private String openHousePageTitle(LocalDateTime start) {
        return "Öppet hus den " + start.getDayOfMonth() + "/" +
                start.getMonthValue() + " " + start.getYear();
    }
}
