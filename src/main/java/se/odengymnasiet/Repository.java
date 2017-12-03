package se.odengymnasiet;

import org.bson.types.ObjectId;

import java.util.Collection;

public interface Repository<E extends Model> {

    long count();

    boolean contains(ObjectId id);

    default boolean contains(E model) {
        return this.contains(model.getId());
    }

    void delete(ObjectId id);

    default void delete(E model) {
        this.delete(model.getId());
    }

    E find(ObjectId id);

    Collection<E> findAll();

    void update(E model);

    void save(E model);
}
