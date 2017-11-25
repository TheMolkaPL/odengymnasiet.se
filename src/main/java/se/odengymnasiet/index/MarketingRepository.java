package se.odengymnasiet.index;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface MarketingRepository extends Repository<Marketing> {

    MarketingRepository LOCAL = new LocalMarketingRepository();

    Collection<Marketing> findAllDeployed();
}

@RepositoryHandler(MarketingRepository.class)
class LocalMarketingRepository extends LocalRepository<Marketing>
                               implements MarketingRepository {

    @Override
    public Collection<Marketing> findAllDeployed() {
        return this.container.values().stream()
                .filter(Marketing::isDeployed)
                .collect(Collectors.toList());
    }
}
