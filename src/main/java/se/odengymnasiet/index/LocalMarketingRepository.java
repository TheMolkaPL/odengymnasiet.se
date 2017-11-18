package se.odengymnasiet.index;

import org.bson.types.ObjectId;
import se.odengymnasiet.RepositoryHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RepositoryHandler(MarketingRepository.class)
public class LocalMarketingRepository implements MarketingRepository {
    private final Map<ObjectId, Marketing> container = new HashMap<>();

    @Override
    public void delete(ObjectId id) {
        this.container.remove(id);
    }

    @Override
    public Marketing find(ObjectId id) {
        return this.container.get(id);
    }

    @Override
    public Collection<Marketing> findAll() {
        return this.container.values();
    }

    @Override
    public Collection<Marketing> findAllDeployed() {
        Collection<Marketing> results = new ArrayList<>();
        this.container.forEach((objectId, marketing) -> {
            if (marketing.isDeployed()) {
                results.add(marketing);
            }
        });

        return results;
    }

    @Override
    public void update(Marketing model) {
        this.container.put(model.getId(), model);
    }

    @Override
    public void save(Marketing model) {
        this.container.put(model.getId(), model);
    }
}
