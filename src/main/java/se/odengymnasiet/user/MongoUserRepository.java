package se.odengymnasiet.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("users")
@RepositoryHandler(UserRepository.class)
public class MongoUserRepository extends MongoRepository<User>
                                 implements UserRepository {

    public MongoUserRepository(MongoDatabase database,
                               MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public User deserialize(Document data) {
        return User.deserialize(data);
    }

    @Override
    public User findByEmail(String email) {
        return this.getCollection().find(eq(User.FIELD_EMAIL, email))
                .map(this::deserialize)
                .first();
    }
}
