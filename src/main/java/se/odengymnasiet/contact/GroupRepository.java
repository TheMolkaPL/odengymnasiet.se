package se.odengymnasiet.contact;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

public interface GroupRepository extends Repository<Group> {

    GroupRepository LOCAL = new LocalGroupRepository();
}

@RepositoryHandler(GroupRepository.class)
class LocalGroupRepository extends LocalRepository<Group>
                           implements GroupRepository {
}
