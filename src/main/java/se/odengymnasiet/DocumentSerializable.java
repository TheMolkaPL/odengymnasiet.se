package se.odengymnasiet;

import org.bson.Document;

/**
 * Something that can be serialized into a {@link Document}. Note that the
 * {@link Document} class is provided by the MongoDB Java Driver, but it's
 * global over the application. All the implemented databases (inherit
 * {@link Database}) are forced to use the {@link Document} class objects.
 */
public interface DocumentSerializable {

    default Document serialize() {
        return this.serialize(new Document());
    }

    Document serialize(Document data);
}
