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

import org.apache.commons.lang3.StringUtils;

public class Topic implements Comparable<Topic> {

    private final String path;
    private final String name;

    private Topic(String path, String name) {
        this.path = path;
        this.name = name;
    }

    @Override
    public int compareTo(Topic o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }

    public static Topic create(String path, String name) {
        return new Topic(normalizePath(path), StringUtils.capitalize(name));
    }

    public static String normalizePath(String input) {
        return input.toLowerCase();
    }
}
