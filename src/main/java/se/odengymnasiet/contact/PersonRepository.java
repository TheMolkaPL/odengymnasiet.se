package se.odengymnasiet.contact;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface PersonRepository extends Repository<Person> {

    PersonRepository LOCAL = new LocalPersonRepository();

    Collection<Person> findContactable();
}

@RepositoryHandler(PersonRepository.class)
class LocalPersonRepository extends LocalRepository<Person>
                            implements PersonRepository {
    @Override
    public Collection<Person> findContactable() {
        return this.container.values().stream()
                .filter(Person::isContactable)
                .collect(Collectors.toList());
    }
}
