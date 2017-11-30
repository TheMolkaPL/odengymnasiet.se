package se.odengymnasiet.user;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

public interface UserRepository extends Repository<User> {

    UserRepository LOCAL = new LocalUserRepository();

    User findByEmail(String email);
}

@RepositoryHandler(UserRepository.class)
class LocalUserRepository extends LocalRepository<User>
                          implements UserRepository {

    @Override
    public User findByEmail(String email) {
        return this.container.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();
    }
}
