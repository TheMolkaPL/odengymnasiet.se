package se.odengymnasiet.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import se.odengymnasiet.Application;
import se.odengymnasiet.Database;
import se.odengymnasiet.Repository;
import se.odengymnasiet.index.MongoMarketingRepository;
import se.odengymnasiet.program.MongoProgramRepository;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.function.Consumer;

public class MongoDBDatabase extends Database {
    private final MongoClient client;
    private final MongoDatabase database;

    public MongoDBDatabase(Application application,
                           Logger logger,
                           MongoClient client,
                           String databaseName) {
        super(application, logger);

        this.client = client;
        this.database = client.getDatabase(databaseName);
    }

    @Override
    public void connect() throws Exception {
    }

    @Override
    public void disconnect() throws Exception {
        this.getClient().close();
    }

    @Override
    public void installDefaultRepositories(Consumer<Repository> consumer) {
        Arrays.asList(
                MongoMarketingRepository.class,
                MongoProgramRepository.class
        ).forEach(clazz -> {
            try {
                consumer.accept(this.loadRepository(clazz));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
    }

    public MongoClient getClient() {
        return this.client;
    }

    public String getCollectionName(String defaultName) {
        return defaultName;
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public Repository loadRepository(Class<? extends Repository> repository)
            throws ReflectiveOperationException {
        MongoCollectionName collectionName = repository
                .getDeclaredAnnotation(MongoCollectionName.class);
        if (collectionName == null) {
            return null;
        }

        Constructor<?> constructor = repository
                .getConstructor(MongoDatabase.class, MongoCollection.class);
        constructor.setAccessible(true);

        String name = this.getCollectionName(collectionName.value());
        return (Repository) constructor.newInstance(
                this.getDatabase(), this.getDatabase().getCollection(name));
    }
}
