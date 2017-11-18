package se.odengymnasiet;

import org.slf4j.Logger;
import se.odengymnasiet.index.LocalMarketingRepository;
import se.odengymnasiet.program.LocalProgramRepository;

import java.util.Arrays;
import java.util.function.Consumer;

public class LocalDatabase extends Database {
    public LocalDatabase(Application application, Logger logger) {
        super(application, logger);
    }

    @Override
    public void connect() throws Exception {
    }

    @Override
    public void disconnect() throws Exception {
    }

    @Override
    public void installDefaultRepositories(Consumer<Repository> consumer) {
        Arrays.asList(
                new LocalMarketingRepository(),
                new LocalProgramRepository()
        ).forEach(repository ->
                this.getApplication().installRepository(repository)
        );
    }
}
