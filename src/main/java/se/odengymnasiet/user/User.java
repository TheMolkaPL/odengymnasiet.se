/*
 * Copyright 2019 Aleksander Jagiełło <themolkapl@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.odengymnasiet.user;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

/**
 * User which can login into the admin panel.
 */
public class User extends Model implements Permissible {

    public static final String FIELD_PERSON_ID = "person_id";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_PASSWORD_SALT = "password_salt";
    public static final String FIELD_ROOT = "root";
    public static final String FIELD_PERMISSIONS = "permissions";
    public static final String FIELD_SUSPENDED = "suspended";

    private ObjectId personId;
    private String email;
    private char[] password;
    private char[] passwordSalt;
    private boolean root;
    private UserPermissions permissions;
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

    @Override
    public PermissionValue permission(Permission permission) {
        return this.getPermissions().permission(permission);
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

    public UserPermissions getPermissions() {
        return this.permissions;
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

    public void setPermissions(UserPermissions permissions) {
        this.permissions = permissions;
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
        data.put(FIELD_PERMISSIONS, this.getPermissions().serialize());
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
        user.setPermissions(UserPermissions.deserialize(user,
                data.get(FIELD_PERMISSIONS, Document.class)));
        user.setSuspended(data.getBoolean(FIELD_SUSPENDED));
        return user;
    }
}
