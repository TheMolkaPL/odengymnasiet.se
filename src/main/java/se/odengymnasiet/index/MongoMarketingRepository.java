package se.odengymnasiet.index;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static se.odengymnasiet.index.Marketing.*;

@RepositoryHandler(MarketingRepository.class)
@MongoCollectionName("marketings")
public class MongoMarketingRepository extends MongoRepository
                                      implements MarketingRepository {
    public MongoMarketingRepository(MongoDatabase database,
                                    MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public void delete(ObjectId id) {
        this.getCollection().deleteOne(eq(id));
    }

    @Override
    public Marketing find(ObjectId id) {
        Document document = this.getCollection().find(eq(id)).first();
        if (document != null) {
            return Marketing.deserialize(document);
        }

        return null;
    }

    @Override
    public Collection<Marketing> findAll() {
        Collection<Marketing> results = new ArrayList<>();
        this.getCollection().find().forEach((Consumer<Document>) document ->
                results.add(Marketing.deserialize(document)));
        return results;
    }

    @Override
    public Collection<Marketing> findAllDeployed() {
        Collection<Marketing> results = new ArrayList<>();
        this.getCollection().find(eq(FIELD_DEPLOYED, true))
                .forEach((Consumer<Document>) document ->
                        results.add(Marketing.deserialize(document)));
        return results;
    }

    @Override
    public void update(Marketing model) {
        this.getCollection().updateOne(eq(model.getId()), model.serialize());
    }

    @Override
    public void save(Marketing model) {
        this.getCollection().insertOne(model.serialize());
    }
}
