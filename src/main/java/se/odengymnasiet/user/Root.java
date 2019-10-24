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
import se.odengymnasiet.contact.Person;

import java.util.Collections;
import java.util.List;

/**
 * @deprecated Not used anymore. See {@link se.odengymnasiet.user.User#root}
 */
@Deprecated
public class Root extends User {

    public static final ObjectId USER_ID =
            new ObjectId("5a207d4f6f7b3e68f34a0f82");
    public static final ObjectId PERSON_ID =
            new ObjectId("5a207d4f6f7b3e68f34a0f83");
    public static final ObjectId PERSON_GROUP_ID =
            new ObjectId("5a207d4f6f7b3e68f34a0f84");
    public static final ObjectId GROUP_ID =
            new ObjectId("5a207d4f6f7b3e68f34a0f85");

    public static Person PERSON = new RootPerson();
    public static Root ROOT = new Root();

    private boolean invalidated;

    private Root() {
        super(USER_ID);
    }

    @Override
    public void setId(ObjectId id) {
    }

    public void invalidate() {
        if (this.invalidated) {
            throw new IllegalStateException("Root is already invalidated!");
        }

        this.invalidated = true;
    }

    public boolean isInvalidated() {
        return this.invalidated;
    }

    public static class RootPerson extends Person {

        private final List<PersonGroup> groups =
                Collections.singletonList(new PersonGroup(PERSON_GROUP_ID) {

                    @Override
                    public ObjectId getGroupId() {
                        return GROUP_ID;
                    }

                    @Override
                    public String getRole() {
                        return "Admin";
                    }

                    @Override
                    public boolean isFocused() {
                        return false;
                    }

                    @Override
                    public void setId(ObjectId id) {
                    }
                });

        private RootPerson() {
            super(PERSON_ID);
        }

        @Override
        public String getFirstName() {
            return "";
        }

        @Override
        public String getLastName() {
            return "Admin";
        }

        @Override
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        @Override
        public List<PersonGroup> getGroups() {
            return this.groups;
        }

        @Override
        public List<Document> getGroupsSerialized() {
            return super.getGroupsSerialized();
        }

        @Override
        public String getEmail() {
            return "";
        }

        @Override
        public String getTelephone() {
            return "";
        }

        @Override
        public boolean isContactable() {
            return false;
        }

        @Override
        public void setId(ObjectId id) {
        }
    }
}
