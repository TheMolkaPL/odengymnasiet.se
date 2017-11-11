package models;

import se.odengymnasiet.mongo.DBDocument;

public class User extends DBDocument {
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_FIRST_NAME = "first_name";
    public static final String FIELD_LAST_NAME = "last_name";
    public static final String FIELD_PASSWORD = "password";

    public String getEmail() {
        return this.getString(FIELD_EMAIL);
    }

    public void setEmail(String email) {
        this.put(FIELD_EMAIL, email);
    }

    public String getFirstName() {
        return this.getString(FIELD_FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        this.put(FIELD_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return this.getString(FIELD_LAST_NAME);
    }

    public void setLastName(String lastName) {
        this.put(FIELD_LAST_NAME, lastName);
    }

    public String getPassword() {
        return this.getString(FIELD_PASSWORD);
    }

    public void setPassword(String password) {
        this.put(FIELD_PASSWORD, password);
    }
}
