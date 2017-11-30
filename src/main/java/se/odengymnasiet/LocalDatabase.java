package se.odengymnasiet;

import org.slf4j.Logger;
import se.odengymnasiet.contact.GroupRepository;
import se.odengymnasiet.contact.PersonRepository;
import se.odengymnasiet.index.MarketingRepository;
import se.odengymnasiet.program.ProgramRepository;
import se.odengymnasiet.user.UserRepository;

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
                GroupRepository.LOCAL,
                MarketingRepository.LOCAL,
                PersonRepository.LOCAL,
                ProgramRepository.LOCAL,
                UserRepository.LOCAL
        ).forEach(repository -> this.getApplication()
                .installRepository(repository));
    }
}
