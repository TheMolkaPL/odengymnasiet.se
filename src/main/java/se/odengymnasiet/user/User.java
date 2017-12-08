package se.odengymnasiet.user;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

public class User extends Model {

    public static final String FIELD_PERSON_ID = "person_id";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_PASSWORD_SALT = "password_salt";
    public static final String FIELD_ROOT = "root";
    public static final String FIELD_SUSPENDED = "suspended";

    private ObjectId personId;
    private String email;
    private char[] password;
    private char[] passwordSalt;
    private boolean root;
    private boolean suspended;

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

    public boolean isRoot() {
        return this.root;
    }

    public boolean isSuspended() {
        return this.suspended;
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

    public void setRoot(boolean root) {
        this.root = root;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_PERSON_ID, this.getPersonId());
        data.put(FIELD_EMAIL, this.getEmail());
        data.put(FIELD_PASSWORD, String.valueOf(this.getPassword()));
        data.put(FIELD_PASSWORD_SALT, String.valueOf(this.getPasswordSalt()));
        data.put(FIELD_ROOT, this.isRoot());
        data.put(FIELD_SUSPENDED, this.isSuspended());
        return super.serialize(data);
    }

    public static User deserialize(Document data) {
        User user = new User(data);
        user.setPersonId(data.getObjectId(FIELD_PERSON_ID));
        user.setEmail(data.getString(FIELD_EMAIL));
        user.setPassword(data.getString(FIELD_PASSWORD).toCharArray());
        user.setPasswordSalt(data.getString(FIELD_PASSWORD_SALT).toCharArray());
        user.setRoot(data.getBoolean(FIELD_ROOT));
        user.setSuspended(data.getBoolean(FIELD_SUSPENDED));
        return user;
    }
}
