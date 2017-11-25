package se.odengymnasiet;

import org.bson.types.ObjectId;
import se.odengymnasiet.mongo.Model;

import java.util.Collection;

public interface Repository<E extends Model> {

    void delete(ObjectId id);

    default void delete(E model) {
        this.delete(model.getId());
    }

    E find(ObjectId id);

    Collection<E> findAll();

    void update(E model);

    void save(E model);
}
