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
import se.odengymnasiet.Controller;
import spark.Request;
import spark.Response;

public class AdminController<E extends AdminManifest> extends Controller<E> {

    public static final String ADMIN_DEFAULT_LAYOUT_NAME = "admin";

    public AdminController(Application app,
                           E manifest,
                           Request request,
                           Response response) {
        super(app, manifest, request, response);
    }

    @Override
    public String getDefaultLayout() {
        return ADMIN_DEFAULT_LAYOUT_NAME;
    }
}
