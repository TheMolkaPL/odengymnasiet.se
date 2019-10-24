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

import se.odengymnasiet.Application;

import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Container which stores a map where the key is the route (as a
 * {@link String}), and the value is its executor (as a {@link RouteExecutor}).
 */
public class RouteExecutorContainer {

    private final Application application;

    private final SortedMap<String, RouteExecutor> container;

    public RouteExecutorContainer(Application app) {
        this.application = app;

        this.container = new TreeMap<>();
    }

    public boolean install(String path, RouteExecutor executor) {
        if (this.container.containsKey(path)) {
            return false;
        }

        this.container.put(path, executor);
        return true;
    }

    public Set<String> keys() {
        return this.container.keySet();
    }

    public RouteExecutor of(String path) {
        return this.container.get(path);
    }

    public Collection<RouteExecutor> values() {
        return this.container.values();
    }
}
