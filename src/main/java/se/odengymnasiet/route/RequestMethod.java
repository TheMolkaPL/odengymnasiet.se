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

package se.odengymnasiet.route;

import spark.Route;
import spark.RouteImpl;
import spark.Service;
import spark.route.HttpMethod;

/**
 * All HTTP verbs.
 */
public enum RequestMethod {

    GET("GET", HttpMethod.get),
    HEAD("HEAD", HttpMethod.head),
    POST("POST", HttpMethod.post),
    PUT("PUT", HttpMethod.put),
    DELETE("DELETE", HttpMethod.delete),
    CONNECT("CONNECT", HttpMethod.connect),
    OPTIONS("OPTIONS", HttpMethod.options),
    TRACE("TRACE", HttpMethod.trace),
    PATCH("PATCH", HttpMethod.patch),
    ;

    private final String verb;
    private final spark.route.HttpMethod method;

    RequestMethod(String verb, spark.route.HttpMethod method) {
        this.verb = verb;
        this.method = method;
    }

    public String getVerb() {
        return this.verb;
    }

    public void installRoute(Service http, String path, Route route) {
        while (path.length() != 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        http.addRoute(this.method, RouteImpl.create(path, route));
    }
}
