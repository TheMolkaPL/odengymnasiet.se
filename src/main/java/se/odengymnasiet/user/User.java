package se.odengymnasiet.user;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.mongo.Model;

public class User extends Model {
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_FIRST_NAME = "first_name";
    public static final String FIELD_LAST_NAME = "last_name";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_PASSWORD_SALT = "password_salt";

    public User() {
        super();
    }

    public User(ObjectId id) {
        super(id);
    }

    public User(Document data) {
        super(data);
    }
}
