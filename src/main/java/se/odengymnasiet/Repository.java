package se.odengymnasiet;

import org.bson.types.ObjectId;
import se.odengymnasiet.mongo.Model;

import java.util.Collection;

public interface Repository<T extends Model> {
    void delete(ObjectId id);

    default void delete(T model) {
        this.delete(model.getId());
    }

    T find(ObjectId id);

    Collection<T> findAll();

    void update(T model);

    void save(T model);
}
