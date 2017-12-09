package se.odengymnasiet.student;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("menus")
@RepositoryHandler(FalafelRepository.class)
public class MongoFalafelRepository extends MongoRepository<Falafel>
                                    implements FalafelRepository {

    public MongoFalafelRepository(MongoDatabase database,
                                  MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public Falafel deserialize(Document data) {
        return Falafel.deserialize(data);
    }

    @Override
    public Collection<Falafel> findFor(int year, int week) {
        Bson where = and(eq(Falafel.FIELD_YEAR, year),
                         eq(Falafel.FIELD_WEEK, week));

        return this.getCollection().find(where)
                .map(this::deserialize)
                .into(new ArrayList<>());
    }
}
