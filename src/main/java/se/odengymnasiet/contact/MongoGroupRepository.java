package se.odengymnasiet.contact;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

@MongoCollectionName("groups")
@RepositoryHandler(GroupRepository.class)
public class MongoGroupRepository extends MongoRepository<Group>
                                  implements GroupRepository {

    public MongoGroupRepository(MongoDatabase database,
                                MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public Group deserialize(Document data) {
        return Group.deserialize(data);
    }
}
