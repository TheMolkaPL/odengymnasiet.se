package se.odengymnasiet.document;

import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class DBDocument extends Document {
    public static final String FIELD_ID = "_id";
    public static final String FIELD_CREATED_AT = "created_at";

    public DBDocument() {
    }

    public DBDocument(ObjectId id) {
        this.put(FIELD_ID, id);
    }
}
