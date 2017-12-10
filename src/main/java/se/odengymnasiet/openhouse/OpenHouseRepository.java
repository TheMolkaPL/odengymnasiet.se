package se.odengymnasiet.openhouse;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

public interface OpenHouseRepository extends Repository<OpenHouse> {

    OpenHouseRepository LOCAL = new LocalOpenHouseRepository();

    Collection<OpenHouse> findAllComing();

    Collection<OpenHouse> findAllDeployed();

    Collection<OpenHouse> findAllDeployedComing();
}

@RepositoryHandler(OpenHouseRepository.class)
class LocalOpenHouseRepository extends LocalRepository<OpenHouse>
                               implements OpenHouseRepository {

    @Override
    public Collection<OpenHouse> findAllComing() {
        Instant now = Instant.now();
        return this.container.values().stream()
                .filter(openHouse -> openHouse.getStart().isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OpenHouse> findAllDeployed() {
        return this.container.values().stream()
                .filter(OpenHouse::isDeployed)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OpenHouse> findAllDeployedComing() {
        Instant now = Instant.now();
        return this.container.values().stream()
                .filter(OpenHouse::isDeployed)
                .filter(openHouse -> openHouse.getStart().isBefore(now))
                .collect(Collectors.toList());
    }
}
