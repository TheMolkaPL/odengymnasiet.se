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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RepositoryContainer {

    private final Application application;

    private final Map<Class<? extends Repository>, Repository> container;

    public RepositoryContainer(Application app) {
        this.application = app;

        this.container = new LinkedHashMap<>();
    }

    public boolean install(Repository<?> repository) {
        RepositoryHandler handler = repository.getClass()
                .getDeclaredAnnotation(RepositoryHandler.class);
        if (handler == null) {
            return false;
        }

        Class<? extends Repository> clazz = handler.value();
        if (this.container.containsKey(clazz)) {
            return false;
        }

        this.container.put(clazz, repository);
        this.application.getLogger().info(
                "Repository {} has been installed to {}.",
                clazz.getSimpleName(),
                repository.getClass().getSimpleName());
        return true;
    }

    public Set<Class<? extends Repository>> keys() {
        return this.container.keySet();
    }

    public <T extends Repository> T of(Class<T> repository) {
        return (T) this.container.get(repository);
    }

    public Collection<Repository> values() {
        return this.container.values();
    }
}
