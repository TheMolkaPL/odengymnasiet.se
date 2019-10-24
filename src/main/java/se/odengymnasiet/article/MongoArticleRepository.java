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

package se.odengymnasiet.article;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("articles")
@RepositoryHandler(ArticleRepository.class)
public class MongoArticleRepository extends MongoRepository<Article>
                                    implements ArticleRepository {

    public MongoArticleRepository(MongoDatabase database,
                                  MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public Article deserialize(Document data) {
        return Article.deserialize(data);
    }

    @Override
    public Collection<Article> findAllByStartingPath(String startingPath) {
        Pattern pattern = Pattern.compile("^" + startingPath);

        return this.getCollection().find(regex(Article.FIELD_PATH, pattern))
                .map(this::deserialize)
                .into(new ArrayList<>());
    }

    @Override
    public Article findByPath(String path) {
        return this.getCollection().find(eq(Article.FIELD_PATH, path))
                .map(this::deserialize)
                .first();
    }
}
