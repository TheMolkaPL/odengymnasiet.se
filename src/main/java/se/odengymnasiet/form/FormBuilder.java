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

import org.apache.commons.lang3.builder.Builder;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class FormBuilder implements Builder<Form> {

    private Request request;
    private Response response;

    private final SortedMap<Namespace, List<Attribute>> attributes;

    public FormBuilder() {
        this.attributes = new TreeMap<>();
    }

    @Override
    public Form build() {
        return new Form(this.request(), this.response(), this.attributes());
    }

    public SortedMap<Namespace, List<Attribute>> attributes() {
        return this.attributes;
    }

    public List<Attribute> namespace() {
        return this.namespace(Namespace.defaultNamespace());
    }

    public List<Attribute> namespace(Namespace namespace) {
        return this.attributes.getOrDefault(namespace, Collections.emptyList());
    }

    public FormBuilder namespace(Attribute attribute) {
        return this.namespace(Namespace.defaultNamespace(), attribute);
    }

    public FormBuilder namespace(Namespace namespace, Attribute attribute) {
        return this.namespace(namespace, Collections.singletonList(attribute));
    }

    public FormBuilder namespace(List<Attribute> attributes) {
        return this.namespace(Namespace.defaultNamespace(), attributes);
    }

    public FormBuilder namespace(Namespace namespace,
                                 List<Attribute> attributes) {
        if (this.attributes.containsKey(namespace)) {
            this.attributes.get(namespace).addAll(attributes);
        } else {
            this.attributes.put(namespace, new ArrayList<>(attributes));
        }

        return this;
    }

    public Set<Namespace> namespaces() {
        return this.attributes.keySet();
    }

    public Request request() {
        return this.request;
    }

    public FormBuilder request(Request request) {
        this.request = request;
        return this;
    }

    public Response response() {
        return this.response;
    }

    public FormBuilder response(Response response) {
        this.response = response;
        return this;
    }

    public FormBuilder require(String attribute) {
        return this.require(Attribute.getAttribute(attribute));
    }

    public FormBuilder require(Attribute attribute) {
        attribute.setRequired(true);

        this.namespace(attribute);
        return this;
    }

    public FormBuilder optional(String attribute) {
        return this.optional(Attribute.getAttribute(attribute));
    }

    public FormBuilder optional(Attribute attribute) {
        attribute.setRequired(false);

        this.namespace(attribute);
        return this;
    }
}
