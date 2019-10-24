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

package se.odengymnasiet;

import org.slf4j.Logger;

import java.util.function.Consumer;

/**
 * Something that can be a database and save and manipulate the data.
 */
public abstract class Database {

    private final Application application;
    private final Logger logger;

    public Database(Application app, Logger logger) {
        this.application = app;
        this.logger = logger;
    }

    public void connect() throws Exception {
    }

    public void disconnect() throws Exception {
    }

    public Application getApplication() {
        return this.application;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void installDefaultRepositories() {
        RepositoryContainer container = this.application.getRepositories();
        this.installDefaultRepositories(container::install);
    }

    public void installDefaultRepositories(Consumer<Repository> consumer) {
    }
}
