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

package se.odengymnasiet.article;

import org.apache.commons.lang3.StringUtils;

/**
 * Hardcoded paths to hardcoded articles on the website.
 */
public class ArticlePaths {

    // /about
    private static final String ABOUT = "about";
    // /contact and /open-house/{id}
    private static final String CONTACT = "contact";
    // all programs related articles
    private static final String PROGRAMS = "programs";
    // all students related articles
    private static final String STUDENTS = "students";

    private ArticlePaths() {
    }

    public static String about() {
        return ABOUT;
    }

    public static String about(String... pages) {
        return path(about(), pages);
    }

    public static String contact() {
        return CONTACT;
    }

    public static String contact(String... pages) {
        return path(contact(), pages);
    }

    public static String programs() {
        return PROGRAMS;
    }

    public static String programs(String... pages) {
        return path(programs(), pages);
    }

    public static String students() {
        return STUDENTS;
    }

    public static String students(String... pages) {
        return path(students(), pages);
    }

    private static String path(String root, String... pages) {
        return pages != null ? root + "/" + StringUtils.join(pages, "/") : root;
    }
}
