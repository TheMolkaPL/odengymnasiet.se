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

package se.odengymnasiet.index;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("marketings")
@RepositoryHandler(MarketingRepository.class)
public class MongoMarketingRepository extends MongoRepository<Marketing>
                                      implements MarketingRepository {

    public MongoMarketingRepository(MongoDatabase database,
                                    MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public Marketing deserialize(Document data) {
        return Marketing.deserialize(data);
    }

    @Override
    public Collection<Marketing> findAllDeployed() {
        return this.getCollection().find(eq(Marketing.FIELD_DEPLOYED, true))
                .map(this::deserialize)
                .into(new ArrayList<>());
    }
}
