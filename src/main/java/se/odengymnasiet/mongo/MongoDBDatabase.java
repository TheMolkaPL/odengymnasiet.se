package se.odengymnasiet.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import se.odengymnasiet.Application;
import se.odengymnasiet.Database;
import se.odengymnasiet.Repository;
import se.odengymnasiet.contact.MongoGroupRepository;
import se.odengymnasiet.contact.MongoPersonRepository;
import se.odengymnasiet.index.MongoArticleRepository;
import se.odengymnasiet.index.MongoMarketingRepository;
import se.odengymnasiet.openhouse.MongoOpenHouseRepository;
import se.odengymnasiet.program.MongoProgramRepository;
import se.odengymnasiet.student.MongoFalafelRepository;
import se.odengymnasiet.student.MongoStudentServiceRepository;
import se.odengymnasiet.user.MongoUserRepository;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class MongoDBDatabase extends Database {

    private final MongoClient client;
    private final MongoDatabase database;

    public MongoDBDatabase(Application app,
                           Logger logger,
                           MongoClient client,
                           String databaseName) {
        super(app, logger);

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
                MongoArticleRepository.class,
                MongoFalafelRepository.class,
                MongoGroupRepository.class,
                MongoMarketingRepository.class,
                MongoOpenHouseRepository.class,
                MongoPersonRepository.class,
                MongoProgramRepository.class,
                MongoStudentServiceRepository.class,
                MongoUserRepository.class
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
        if (name == null) {
            return null;
        }

        MongoDatabase database = this.getDatabase();
        if (!database.listCollectionNames()
                .into(new ArrayList<>()).contains(name)) {
            this.getLogger().info("Creating new '" + name + "' collection...");
            database.createCollection(name);
        }

        return (Repository) constructor.newInstance(
                database, database.getCollection(name));
    }
}
