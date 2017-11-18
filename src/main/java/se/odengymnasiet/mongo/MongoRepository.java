package se.odengymnasiet.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoRepository {
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoRepository(MongoDatabase database,
                           MongoCollection<Document> collection) {
        this.database = database;
        this.collection = collection;
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public MongoCollection<Document> getCollection() {
        return this.collection;
    }
}
