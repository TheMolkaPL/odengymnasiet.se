package se.odengymnasiet;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Configuration of the application and the Spark server (which is
 * {@link spark.Service}).
 */
public class Configuration {

    public static final String CONFIGURATION_FILE_PATH = "application.xml";

    public void readFile() {
        Document document;
        SAXBuilder builder = new SAXBuilder();
        try {
            File file = new File(CONFIGURATION_FILE_PATH);
            if (!file.exists()) {
                Files.copy(this.getClass().getClassLoader().getResourceAsStream(
                        CONFIGURATION_FILE_PATH), file.toPath());
            }

            document = builder.build(file);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            return;
        }

        Element root = document.getRootElement();

        try {
            {
                Element http = root.getChild("http");
                this.httpHost(http.getAttributeValue("host"));
                this.httpPort(Integer.parseInt(http.getAttributeValue("port")));

                this.httpMaxThreads(Integer.parseInt(
                        http.getChildText("max-threads")));
                this.httpMinThreads(Integer.parseInt(
                        http.getChildText("min-threads")));
                this.httpIdleTimeout(Integer.parseInt(
                        http.getChildText("idle-timeout")));

                this.httpAssetsPath(http.getChildText("assets-path"));
            }
            {
                Element database = root.getChild("database");
                if (database != null) {
                    this.database(database);
                }
            }
            {
                Element views = root.getChild("views");
                if (views != null) {
                    this.viewsPath(views.getAttributeValue("path"));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private String httpHost;
    public String httpHost() {
        return this.httpHost;
    }
    public void httpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    private int httpPort;
    public int httpPort() {
        return this.httpPort;
    }
    public void httpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    private int httpMaxThreads;
    public int httpMaxThreads() {
        return this.httpMaxThreads;
    }
    public void httpMaxThreads(int httpMaxThreads) {
        this.httpMaxThreads = httpMaxThreads;
    }

    private int httpMinThreads;
    public int httpMinThreads() {
        return this.httpMinThreads;
    }
    public void httpMinThreads(int httpMinThreads) {
        this.httpMinThreads = httpMinThreads;
    }

    private int httpIdleTimeout;
    public int httpIdleTimeout() {
        return this.httpIdleTimeout;
    }
    public void httpIdleTimeout(int httpIdleTimeout) {
        this.httpIdleTimeout = httpIdleTimeout;
    }

    private String httpAssetsPath;
    public String httpAssetsPath() {
        return this.httpAssetsPath;
    }
    public void httpAssetsPath(String httpAssetsPath) {
        this.httpAssetsPath = httpAssetsPath;
    }

    private Element database;
    public Element database() {
        return this.database;
    }
    public void database(Element database) {
        this.database = database;
    }

    private String viewsPath;
    public String viewsPath() {
        return this.viewsPath;
    }
    public void viewsPath(String viewsPath) {
        this.viewsPath = viewsPath;
    }
}
