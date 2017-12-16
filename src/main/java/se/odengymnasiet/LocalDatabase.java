package se.odengymnasiet;

import org.slf4j.Logger;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.contact.GroupRepository;
import se.odengymnasiet.contact.PersonRepository;
import se.odengymnasiet.falafel.FalafelRepository;
import se.odengymnasiet.index.MarketingRepository;
import se.odengymnasiet.openhouse.OpenHouseRepository;
import se.odengymnasiet.program.ProgramRepository;
import se.odengymnasiet.student.StudentServiceRepository;
import se.odengymnasiet.user.UserRepository;

import java.util.Arrays;
import java.util.function.Consumer;

public class LocalDatabase extends Database {

    public LocalDatabase(Application app, Logger logger) {
        super(app, logger);
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
                ArticleRepository.LOCAL,
                FalafelRepository.LOCAL,
                GroupRepository.LOCAL,
                MarketingRepository.LOCAL,
                OpenHouseRepository.LOCAL,
                PersonRepository.LOCAL,
                ProgramRepository.LOCAL,
                StudentServiceRepository.LOCAL,
                UserRepository.LOCAL
        ).forEach(consumer::accept);
    }
}
