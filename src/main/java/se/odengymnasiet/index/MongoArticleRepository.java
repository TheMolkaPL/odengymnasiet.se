package se.odengymnasiet.index;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

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
    public Article findByPath(String path) {
        return this.getCollection().find(eq(Article.FIELD_PATH, path))
                .map(this::deserialize)
                .first();
    }
}
