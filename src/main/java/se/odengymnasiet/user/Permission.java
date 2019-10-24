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

package se.odengymnasiet.user;

import java.util.Objects;

/**
 * Unique permission object which requires a unique name (identifier) and an
 * optional value.
 */
public class Permission {

    private final String name;
    private PermissionValue defaultValue;

    private Permission(String name, PermissionValue defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Permission &&
                Objects.equals(this.name, ((Permission) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String getName() {
        return this.name;
    }

    public PermissionValue getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(PermissionValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static Permission of(String name) {
        return of(name, PermissionValue.ABSTAIN);
    }

    public static Permission of(String name, PermissionValue defaultValue) {
        return new Permission(name, defaultValue);
    }
}
