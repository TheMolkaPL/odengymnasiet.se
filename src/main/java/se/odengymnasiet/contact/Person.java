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

package se.odengymnasiet.contact;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A single person working in the school. The person can be contacted and is
 * displayed on the /contact/staff page.
 */
public class Person extends Model implements Comparable<Person> {

    public static final String FIELD_FIRST_NAME = "first_name";
    public static final String FIELD_LAST_NAME = "last_name";
    public static final String FIELD_CONTACTABLE = "contactable";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_GROUPS = "groups";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_TELEPHONE = "telephone";

    private String firstName;
    private String lastName;
    private boolean contactable;
    private int priority;
    private List<PersonGroup> groups;
    private String email;
    private String telephone;

    public Person() {
    }

    public Person(ObjectId id) {
        super(id);
    }

    public Person(Document data) {
        super(data);
    }

    @Override
    public int compareTo(Person o) {
        int compare = Integer.compare(o.getPriority(), this.getPriority());
        if (compare != 0) {
            return compare;
        }

        return (this.getFirstName() + " " + this.getLastName())
                .compareToIgnoreCase(o.getFirstName() + " " + o.getLastName());
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getPriority() {
        return this.priority;
    }

    public List<PersonGroup> getGroups() {
        return this.groups;
    }

    public List<Document> getGroupsSerialized() {
        return this.getGroups().stream()
                .map(PersonGroup::serialize)
                .collect(Collectors.toList());
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String getTelephonePretty() {
        String telephone = this.getTelephone();
        String raw = telephone.substring(
                telephone.length() - 9, telephone.length());

        return 0 + raw.substring(0, 1) + " " +
                raw.substring(1, 4) + " " +
                raw.substring(4, 7) + " " +
                raw.substring(7, 9);
    }

    public boolean isContactable() {
        return this.contactable;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContactable(boolean contactable) {
        this.contactable = contactable;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setGroups(List<PersonGroup> groups) {
        this.groups = groups;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_FIRST_NAME, this.getFirstName());
        data.put(FIELD_LAST_NAME, this.getLastName());
        data.put(FIELD_CONTACTABLE, this.isContactable());
        data.put(FIELD_PRIORITY, this.getPriority());
        data.put(FIELD_GROUPS, this.getGroupsSerialized());
        data.put(FIELD_EMAIL, this.getEmail());
        data.put(FIELD_TELEPHONE, this.getTelephone());
        return super.serialize(data);
    }

    public static Person deserialize(Document data) {
        Person person = new Person(data);
        person.setFirstName(data.getString(FIELD_FIRST_NAME));
        person.setLastName(data.getString(FIELD_LAST_NAME));
        person.setContactable(data.getBoolean(FIELD_CONTACTABLE));
        person.setPriority(data.getInteger(FIELD_PRIORITY));
        person.setGroups(deserializeGroups(data.get(FIELD_GROUPS,
                                                    List.class)));
        person.setEmail(data.getString(FIELD_EMAIL));
        person.setTelephone(data.getString(FIELD_TELEPHONE));
        return person;
    }

    private static List<PersonGroup> deserializeGroups(List<Document> data) {
        return data.stream()
                .map(PersonGroup::deserialize)
                .collect(Collectors.toList());
    }

    public static class PersonGroup extends Model {

        public static final String FIELD_GROUP_ID = "group_id";
        public static final String FIELD_ROLE = "role";
        public static final String FIELD_FOCUSED = "focused";

        private ObjectId groupId;
        private String role;
        private boolean focused;

        public PersonGroup() {
            super();
        }

        public PersonGroup(ObjectId id) {
            super(id);
        }

        public PersonGroup(Document data) {
            super(data);
        }

        public ObjectId getGroupId() {
            return this.groupId;
        }

        public String getRole() {
            return this.role;
        }

        public boolean isFocused() {
            return this.focused;
        }

        public void setGroupId(ObjectId groupId) {
            this.groupId = groupId;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        @Override
        public Document serialize(Document data) {
            data.put(FIELD_GROUP_ID, this.getGroupId());
            data.put(FIELD_ROLE, this.getRole());
            data.put(FIELD_FOCUSED, this.isFocused());
            return super.serialize(data);
        }

        public static PersonGroup deserialize(Document data) {
            PersonGroup group = new PersonGroup(data);
            group.setGroupId(data.getObjectId(FIELD_GROUP_ID));
            group.setRole(data.getString(FIELD_ROLE));
            group.setFocused(data.getBoolean(FIELD_FOCUSED));
            return group;
        }
    }
}
