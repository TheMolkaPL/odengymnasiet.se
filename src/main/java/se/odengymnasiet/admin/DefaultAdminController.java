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

package se.odengymnasiet.admin;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.route.RequestMethod;
import spark.Redirect;
import spark.Request;
import spark.Response;
import spark.Session;

public class DefaultAdminController
        extends AdminController<DefaultAdminManifest> {

    public DefaultAdminController(Application app,
                                  DefaultAdminManifest manifest,
                                  Request request,
                                  Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        return "Admin Control Panel";
    }

    @HttpRoute("/dashboard")
    public Object dashboard() {
        return this.ok("admin/dashboard", Attributes.create(), "Dashboard");
    }

    @HttpRoute("/login")
    public Object login() {
        Attributes attributes = Attributes.create();
        return this.ok("admin/login", attributes, "Dashboard",
                Controller.DEFAULT_LAYOUT_NAME);
    }

    @HttpRoute(value = "/login", methods = RequestMethod.POST)
    public Object loginPost() {
        return this.getResponse().body();
    }

    @HttpRoute("/logout")
    public Object logout() {
        Session session = this.getRequest().session(false);
        if (session != null) {
            session.invalidate();
        }

        int statusCode = Redirect.Status.TEMPORARY_REDIRECT.intValue();
        this.getResponse().redirect("/", statusCode);
        return this.getResponse().body();
    }

    @HttpRoute("/reset-password")
    public Object resetPassword() {
        Attributes attributes = Attributes.create();
        return this.ok("admin/reset_password", attributes, "Glömt lösenord",
                Controller.DEFAULT_LAYOUT_NAME);
    }

    @HttpRoute(value = "/reset-password", methods = RequestMethod.POST)
    public Object resetPasswordPost() {
        return this.getResponse().body();
    }
}
