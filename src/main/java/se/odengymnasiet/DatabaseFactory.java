package se.odengymnasiet;

import org.jdom2.Element;
import org.slf4j.Logger;

public interface DatabaseFactory {

    Database newDatabase(Application app,
                         Logger logger,
                         Element configuration);
}
