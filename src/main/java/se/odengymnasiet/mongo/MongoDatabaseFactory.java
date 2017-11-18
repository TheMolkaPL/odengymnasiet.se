package se.odengymnasiet.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.slf4j.Logger;
import se.odengymnasiet.Application;
import se.odengymnasiet.Database;
import se.odengymnasiet.DatabaseFactory;
import se.odengymnasiet.mongo.codec.InstantCodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MongoDatabaseFactory implements DatabaseFactory {
    public static final String DEFAULT_HOST;
    public static final int DEFAULT_PORT;
    public static final ServerAddress DEFAULT_SERVER_ADDRESS;
    public static final String DEFAULT_DATABASE;
    public static final String DEFAULT_USERNAME;
    public static final String DEFAULT_PASSWORD;

    static {
        DEFAULT_HOST = ServerAddress.defaultHost();
        DEFAULT_PORT = ServerAddress.defaultPort();
        DEFAULT_SERVER_ADDRESS = new ServerAddress(DEFAULT_HOST, DEFAULT_PORT);
        DEFAULT_DATABASE = "odengymnasiet";
        DEFAULT_USERNAME = "username";
        DEFAULT_PASSWORD = "password";
    }

    public MongoDatabaseFactory() {
    }

    @Override
    public Database newDatabase(Application application,
                                Logger logger,
                                Element configuration) {
        List<ServerAddress> addresses = this.serverAddresses(configuration);
        if (addresses.isEmpty()) {
            addresses.add(DEFAULT_SERVER_ADDRESS);
        }

        String database = configuration.getChildTextTrim("database");
        if (database == null) {
            database = DEFAULT_DATABASE;
        }

        MongoCredential credential = this.credential(configuration, database);

        List<MongoCredential> credentials = Collections.emptyList();
        if (credential != null) {
            credentials = Collections.singletonList(credential);
        }

        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(CodecRegistries.fromRegistries(
                        MongoClient.getDefaultCodecRegistry(),
                        CodecRegistries.fromCodecs(this.codecs())))
                .build();

        MongoClient client = new MongoClient(addresses, credentials, options);
        return new MongoDBDatabase(application, logger, client, database);
    }

    private List<ServerAddress> serverAddresses(Element configuration) {
        List<ServerAddress> addresses = new ArrayList<>();
        configuration.getChildren("server").forEach(element -> {
            Attribute hostAttribute = element.getAttribute("host");
            Attribute portAttribute = element.getAttribute("port");

            String host = DEFAULT_HOST;
            if (hostAttribute != null) {
                host = hostAttribute.getValue();
            }

            int port = DEFAULT_PORT;
            if (portAttribute != null) {
                try {
                    port = portAttribute.getIntValue();
                } catch (DataConversionException ignored) {
                }
            }

            if (!host.equalsIgnoreCase(DEFAULT_HOST) && port != DEFAULT_PORT) {
                addresses.add(new ServerAddress(host, port));
            }
        });

        return addresses;
    }

    private MongoCredential credential(Element configuration, String database) {
        String username = configuration.getChildTextTrim("username");
        if (username == null) {
            username = DEFAULT_USERNAME;
        }

        String password = configuration.getChildTextTrim("password");
        if (password == null) {
            password = DEFAULT_PASSWORD;
        }

        if (!username.equalsIgnoreCase(DEFAULT_USERNAME) &&
                !password.equalsIgnoreCase(DEFAULT_PASSWORD)) {
            return MongoCredential.createCredential(username,
                                                    database,
                                                    password.toCharArray());
        }

        return null;
    }

    private Codec<?>[] codecs() {
        return new Codec[] {
                new InstantCodec()
        };
    }
}
