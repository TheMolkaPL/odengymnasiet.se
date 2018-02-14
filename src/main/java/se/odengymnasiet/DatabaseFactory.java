package se.odengymnasiet;

import org.jdom2.Element;
import org.slf4j.Logger;

/**
 * Factory for a {@link Database} object.
 */
public interface DatabaseFactory {

    Database newDatabase(Application app, Logger logger, Element configuration);
}
