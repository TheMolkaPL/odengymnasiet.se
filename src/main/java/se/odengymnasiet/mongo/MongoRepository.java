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

package se.odengymnasiet.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;
import se.odengymnasiet.Repository;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;

/**
 * Class to support simple collection manipulation of the data inside a
 * MongoDB's collection. A very simple wrapper between {@link Repository}
 * and {@link MongoCollection}.
 * @param <E> Data Object Model of this repository.
 */
public abstract class MongoRepository<E extends Model>
        implements Repository<E> {

    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoRepository(MongoDatabase database,
                           MongoCollection<Document> collection) {
        this.database = database;
        this.collection = collection;
    }

    public abstract E deserialize(Document data);

    public Document serialize(E model) {
        return model.serialize();
    }

    @Override
    public long count() {
        return this.collection.count();
    }

    @Override
    public boolean contains(ObjectId id) {
        return this.collection.find(eq(id)).first() != null;
    }

    @Override
    public void delete(ObjectId id) {
        this.collection.deleteOne(eq(id));
    }

    @Override
    public E find(ObjectId id) {
        return this.collection.find(eq(id))
                .map(this::deserialize)
                .first();
    }

    @Override
    public Collection<E> findAll() {
        return this.collection.find()
                .map(this::deserialize)
                .into(new ArrayList<>());
    }

    @Override
    public void update(E model) {
        Document update = Model.filterUpdate(this.serialize(model));
        this.collection.updateOne(eq(model.getId()),
                new BasicDBObject("$set", update));
    }

    @Override
    public void save(E model) {
        this.collection.insertOne(this.serialize(model));
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public MongoCollection<Document> getCollection() {
        return this.collection;
    }
}
