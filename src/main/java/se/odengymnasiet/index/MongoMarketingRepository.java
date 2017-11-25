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
