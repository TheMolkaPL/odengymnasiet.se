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

package se.odengymnasiet.form;

public class Attribute {

    private final String attribute;
    private boolean required;

    private Attribute(String attribute) {
        this(attribute, false);
    }

    private Attribute(String attribute, boolean required) {
        this.attribute = attribute;
        this.required = required;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public static Attribute getAttribute(String attribute) {
        return getAttribute(attribute, false);
    }

    public static Attribute getAttribute(String attribute, boolean required) {
        return new Attribute(attribute, false);
    }

    public static Attribute require(String attribute) {
        Attribute value = getAttribute(attribute);
        value.setRequired(true);
        return value;
    }

    public static Attribute optional(String attribute) {
        Attribute value = getAttribute(attribute);
        value.setRequired(false);
        return value;
    }
}
