package se.odengymnasiet.mongo;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;

public abstract class DBDocument extends Document {
    public static final String FIELD_ID = "_id";
    public static final String FIELD_SINCE = "since";

    public DBDocument() {
    }

    public DBDocument(ObjectId id) {
        this.put(FIELD_ID, id);
    }

    public ObjectId getId() {
        return this.getObjectId(FIELD_ID);
    }

    public void setId(ObjectId id) {
        this.put(FIELD_ID, id);
    }

    public Instant getSince() {
        return DBFields.toInstant(this.getDate(FIELD_SINCE));
    }

    public void setSince(Instant since) {
        this.put(FIELD_SINCE, DBFields.toDate(since));
    }
}
