package se.odengymnasiet;

import org.slf4j.Logger;

import java.util.function.Consumer;

public class Database {

    private final Application application;
    private final Logger logger;

    public Database(Application app, Logger logger) {
        this.application = app;
        this.logger = logger;
    }

    public void connect() throws Exception {
    }

    public void disconnect() throws Exception {
    }

    public Application getApplication() {
        return this.application;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void installDefaultRepositories() {
        RepositoryContainer container = this.application.getRepositories();
        this.installDefaultRepositories(container::install);
    }

    public void installDefaultRepositories(Consumer<Repository> consumer) {
    }
}
