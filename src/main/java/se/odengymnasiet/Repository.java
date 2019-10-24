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

/**
 * A repository is manipulating the data inside the provided database.
 * @param <E> The Data Object Model class which this class manipulates on.
 */
public interface Repository<E extends Model> {

    long count();

    boolean contains(ObjectId id);

    default boolean contains(E model) {
        return this.contains(model.getId());
    }

    void delete(ObjectId id);

    default void delete(E model) {
        this.delete(model.getId());
    }

    E find(ObjectId id);

    Collection<E> findAll();

    void update(E model);

    void save(E model);
}
