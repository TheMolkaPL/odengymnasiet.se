package se.odengymnasiet.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Repository;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;

public abstract class MongoRepository<E extends Model>
        implements Repository<E> {

    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoRepository(MongoDatabase database,
                           MongoCollection<Document> collection) {
        this.database = database;
        this.collection = collection;
    }

    public abstract E deserialize(Document data);

    public Document serialize(E model) {
        return model.serialize();
    }

    @Override
    public void delete(ObjectId id) {
        this.collection.deleteOne(eq(id));
    }

    @Override
    public E find(ObjectId id) {
        return this.collection.find(eq(id))
                .map(this::deserialize)
                .first();
    }

    @Override
    public Collection<E> findAll() {
        return this.collection.find()
                .map(this::deserialize)
                .into(new ArrayList<>());
    }

    @Override
    public void update(E model) {
        BasicDBObject update = new BasicDBObject("$set", this.serialize(model));
        this.collection.updateOne(eq(model.getId()), update);
    }

    @Override
    public void save(E model) {
        this.collection.insertOne(this.serialize(model));
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public MongoCollection<Document> getCollection() {
        return this.collection;
    }
}
