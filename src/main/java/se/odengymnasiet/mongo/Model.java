package se.odengymnasiet.mongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.DocumentSerializable;

import java.time.Instant;

public abstract class Model implements DocumentSerializable {
    public static final String FIELD_ID = "_id";
    public static final String FIELD_UPDATED_AT = "updated_at";

    private ObjectId id;
    private Instant updatedAt;

    public Model() {
        this(ObjectId.get());
    }

    public Model(ObjectId id) {
        this.id = id;
    }

    public Model(Document data) {
        this(data.getObjectId(FIELD_ID));
        this.setUpdatedAt(data.get(FIELD_UPDATED_AT, Instant.class));
    }

    public ObjectId getId() {
        return this.id;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public final Document serialize() {
        return this.serialize(new Document());
    }

    @Override
    public Document serialize(Document data) {
        if (this.getId() == null) {
            this.setId(ObjectId.get());
        }

        if (this.getUpdatedAt() == null) {
            this.setUpdatedAt(this.getId().getDate().toInstant());
        }

        data.put(FIELD_ID, this.getId());
        data.put(FIELD_UPDATED_AT, this.getUpdatedAt());
        return data;
    }
}
