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

import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class LocalRepository<E extends Model> implements Repository<E> {

    protected final Map<ObjectId, E> container = new LinkedHashMap<>();

    @Override
    public long count() {
        return this.container.size();
    }

    @Override
    public boolean contains(ObjectId id) {
        return this.container.containsKey(id);
    }

    @Override
    public void delete(ObjectId id) {
        this.container.remove(id);
    }

    @Override
    public E find(ObjectId id) {
        return this.container.get(id);
    }

    @Override
    public Collection<E> findAll() {
        return this.container.values();
    }

    @Override
    public void update(E model) {
        this.container.replace(model.getId(), model);
    }

    @Override
    public void save(E model) {
        this.container.put(model.getId(), model);
    }
}
