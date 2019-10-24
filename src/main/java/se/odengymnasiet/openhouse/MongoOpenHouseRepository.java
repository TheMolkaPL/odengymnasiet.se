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

package se.odengymnasiet.openhouse;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("open_houses")
@RepositoryHandler(OpenHouseRepository.class)
public class MongoOpenHouseRepository extends MongoRepository<OpenHouse> implements OpenHouseRepository {

    public MongoOpenHouseRepository(MongoDatabase database,
                                    MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public OpenHouse deserialize(Document data) {
        return OpenHouse.deserialize(data);
    }

    @Override
    public Collection<OpenHouse> findAllComing() {
        Instant now = Instant.now();
        return this.getCollection().find(gte(OpenHouse.FIELD_START, now))
                .map(this::deserialize)
                .into(new ArrayList<>());
    }

    @Override
    public Collection<OpenHouse> findAllDeployed() {
        return this.getCollection().find(eq(OpenHouse.FIELD_DEPLOYED, true))
                .map(this::deserialize)
                .into(new ArrayList<>());
    }

    @Override
    public Collection<OpenHouse> findAllDeployedComing() {
        Bson where = and(eq(OpenHouse.FIELD_DEPLOYED, true),
                         gte(OpenHouse.FIELD_START, Instant.now()));

        return this.getCollection().find(where)
                .map(this::deserialize)
                .into(new ArrayList<>());
    }
}
