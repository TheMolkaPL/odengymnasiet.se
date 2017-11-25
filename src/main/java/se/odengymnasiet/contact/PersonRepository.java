package se.odengymnasiet.contact;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

public interface PersonRepository extends Repository<Person> {

    PersonRepository LOCAL = new LocalPersonRepository();
}

@RepositoryHandler(PersonRepository.class)
class LocalPersonRepository extends LocalRepository<Person>
                            implements PersonRepository {
}
