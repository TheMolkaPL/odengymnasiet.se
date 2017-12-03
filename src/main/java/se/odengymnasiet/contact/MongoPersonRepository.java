package se.odengymnasiet.contact;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("persons")
@RepositoryHandler(PersonRepository.class)
public class MongoPersonRepository extends MongoRepository<Person>
                                   implements PersonRepository {

    public MongoPersonRepository(MongoDatabase database,
                                 MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public Person deserialize(Document data) {
        return Person.deserialize(data);
    }

    @Override
    public Collection<Person> findContactable() {
        return this.getCollection().find(eq(Person.FIELD_CONTACTABLE, true))
                .map(this::deserialize)
                .into(new ArrayList<>());
    }
}
