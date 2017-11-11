package models;

import se.odengymnasiet.mongo.DBDocument;

public class Program extends DBDocument {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_URL = "url";
    public static final String FIELD_DESCRIPTION = "description";

    public String getTitle() {
        return this.getString(FIELD_TITLE);
    }

    public void setTitle(String title) {
        this.put(FIELD_TITLE, title);
    }

    public String getUrl() {
        return this.getString(FIELD_URL);
    }

    public void setUrl(String url) {
        this.put(FIELD_URL, url);
    }

    public String getDescription() {
        return this.getString(FIELD_DESCRIPTION);
    }

    public void setDescription(String description) {
        this.put(FIELD_DESCRIPTION, description);
    }
}
