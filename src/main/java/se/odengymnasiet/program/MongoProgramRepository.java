package se.odengymnasiet.program;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import static com.mongodb.client.model.Filters.*;

@MongoCollectionName("programs")
@RepositoryHandler(ProgramRepository.class)
public class MongoProgramRepository extends MongoRepository<Program>
                                    implements ProgramRepository {

    public MongoProgramRepository(MongoDatabase database,
                                  MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public Program deserialize(Document data) {
        return Program.deserialize(data);
    }

    @Override
    public Program findByPath(String path) {
        return this.getCollection().find(eq(Program.FIELD_PATH, path))
                .map(this::deserialize)
                .first();
    }
}
