package se.odengymnasiet;

import org.bson.Document;

public interface DocumentSerializable {
    default Document serialize() {
        return this.serialize(new Document());
    }

    Document serialize(Document data);
}
