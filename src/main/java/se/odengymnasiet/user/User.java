package se.odengymnasiet.user;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

public class User extends Model {

    public static final String FIELD_PERSON_ID = "person_id";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_PASSWORD_SALT = "password_salt";

    private ObjectId personId;
    private String email;
    private char[] password;
    private char[] passwordSalt;

    public User() {
        super();
    }

    public User(ObjectId id) {
        super(id);
    }

    public User(Document data) {
        super(data);
    }

    public ObjectId getPersonId() {
        return this.personId;
    }

    public String getEmail() {
        return this.email;
    }

    public char[] getPassword() {
        return this.password;
    }

    public char[] getPasswordSalt() {
        return this.passwordSalt;
    }

    public void setPersonId(ObjectId personId) {
        this.personId = personId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setPasswordSalt(char[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_PERSON_ID, this.getPersonId());
        data.put(FIELD_EMAIL, this.getEmail());
        data.put(FIELD_PASSWORD, String.valueOf(this.getPassword()));
        data.put(FIELD_PASSWORD_SALT, String.valueOf(this.getPasswordSalt()));
        return super.serialize(data);
    }

    public static User deserialize(Document data) {
        User user = new User(data);
        user.setPersonId(data.getObjectId(FIELD_PERSON_ID));
        user.setEmail(data.getString(FIELD_EMAIL));
        user.setPassword(data.getString(FIELD_PASSWORD).toCharArray());
        user.setPasswordSalt(data.getString(FIELD_PASSWORD_SALT).toCharArray());
        return user;
    }
}
