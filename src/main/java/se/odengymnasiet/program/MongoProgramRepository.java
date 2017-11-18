package se.odengymnasiet.program;

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

import static com.mongodb.client.model.Filters.*;
import static se.odengymnasiet.program.Program.*;

@RepositoryHandler(ProgramRepository.class)
@MongoCollectionName("programs")
public class MongoProgramRepository extends MongoRepository
                                    implements ProgramRepository {
    public MongoProgramRepository(MongoDatabase database,
                                  MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public void delete(ObjectId id) {
        this.getCollection().deleteOne(eq(id));
    }

    @Override
    public Program find(ObjectId id) {
        Document document = this.getCollection().find(eq(id)).first();
        if (document != null) {
            return Program.deserialize(document);
        }

        return null;
    }

    @Override
    public Collection<Program> findAll() {
        Collection<Program> results = new ArrayList<>();
        this.getCollection().find().forEach((Consumer<Document>) document ->
                results.add(Program.deserialize(document)));

        return results;
    }

    @Override
    public Program findByPath(String path) {
        Document document = this.getCollection()
                .find(eq(FIELD_PATH, path)).first();
        if (document != null) {
            return Program.deserialize(document);
        }

        return null;
    }

    @Override
    public void update(Program model) {
        this.getCollection().replaceOne(eq(model.getId()), model.serialize());
    }

    @Override
    public void save(Program model) {
        this.getCollection().insertOne(model.serialize());
    }
}
