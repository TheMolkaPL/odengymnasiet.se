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

import java.util.Objects;

public class Namespace implements Comparable<Namespace> {

    private static final Namespace DEFAULT_NAMESPACE = getNamespace("_def_ns_");

    private final String namespace;

    private Namespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public int compareTo(Namespace o) {
        return this.namespace.compareToIgnoreCase(o.namespace);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Namespace &&
                Objects.equals(this.namespace, ((Namespace) obj).namespace);
    }

    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.namespace);
    }

    public static Namespace defaultNamespace() {
        return DEFAULT_NAMESPACE;
    }

    public static Namespace getNamespace(String namespace) {
        return new Namespace(namespace);
    }
}
