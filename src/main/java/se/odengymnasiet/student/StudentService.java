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

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

/**
 * Simple URL references to different services (eg. email, SchoolSoft). The
 * references are currently displayed on the navbar and on the /students page.
 */
public class StudentService extends Model
                            implements Comparable<StudentService> {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_URL = "url";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_NAVBAR = "navbar";

    private String name;
    private String url;
    private int priority;
    private boolean navbar;

    public StudentService() {
        super();
    }

    public StudentService(ObjectId id) {
        super(id);
    }

    public StudentService(Document data) {
        super(data);
    }

    @Override
    public int compareTo(StudentService o) {
        int compare = Integer.compare(o.getPriority(), this.getPriority());
        if (compare != 0) {
            return compare;
        }

        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isNavbar() {
        return this.navbar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setNavbar(boolean navbar) {
        this.navbar = navbar;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_NAME, this.getName());
        data.put(FIELD_URL, this.getUrl());
        data.put(FIELD_PRIORITY, this.getPriority());
        data.put(FIELD_NAVBAR, this.isNavbar());
        return super.serialize(data);
    }

    public static StudentService deserialize(Document data) {
        StudentService service = new StudentService(data);
        service.setName(data.getString(FIELD_NAME));
        service.setUrl(data.getString(FIELD_URL));
        service.setPriority(data.getInteger(FIELD_PRIORITY));
        service.setNavbar(data.getBoolean(FIELD_NAVBAR));
        return service;
    }
}
