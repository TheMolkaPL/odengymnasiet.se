package se.odengymnasiet;

import org.bson.types.ObjectId;
import se.odengymnasiet.mongo.Model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class LocalRepository<E extends Model> implements Repository<E> {

    protected final Map<ObjectId, E> container = new LinkedHashMap<>();

    @Override
    public void delete(ObjectId id) {
        this.container.remove(id);
    }

    @Override
    public E find(ObjectId id) {
        return this.container.get(id);
    }

    @Override
    public Collection<E> findAll() {
        return this.container.values();
    }

    @Override
    public void update(E model) {
        this.container.replace(model.getId(), model);
    }

    @Override
    public void save(E model) {
        this.container.put(model.getId(), model);
    }
}
