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

import org.bson.types.ObjectId;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.sql.SQLRepository;
import se.odengymnasiet.sql.SQLTable;
import se.odengymnasiet.sql.SQLTableName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RepositoryHandler(PersonRepository.class)
@SQLTableName("persons")
public class SQLPersonRepository extends SQLRepository<Person>
                                 implements PersonRepository {

    public SQLPersonRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected Person createModel() {
        return new Person();
    }

    @Override
    public Person deserialize(ResultSet resultSet) throws SQLException {
        Person person = super.deserialize(resultSet);
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setContactable(resultSet.getBoolean("contactable"));
        person.setPriority(resultSet.getInt("priority"));
        person.setGroups(this.findGroups(resultSet.getInt("id")));
        person.setEmail(resultSet.getString("email"));
        person.setTelephone(resultSet.getString("telephone"));
        return person;
    }

    @Override
    public Collection<Person> findContactable() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE contactable=?;");
            statement.setBoolean(1, true);

            ResultSet resultSet = statement.executeQuery();

            List<Person> persons = new ArrayList<>();
            while (resultSet.next()) {
                persons.add(this.deserialize(resultSet));
            }

            return persons;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    private List<Person.PersonGroup> findGroups(int personId) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * " +
                    "FROM person_groups " +
                    "INNER JOIN groups " +
                    "ON person_groups.group_id = groups.id " +
                    "WHERE person_groups.person_id=?;");
            statement.setInt(1, personId);

            ResultSet resultSet = statement.executeQuery();

            List<Person.PersonGroup> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserializeGroup(resultSet));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    private Person.PersonGroup deserializeGroup(ResultSet resultSet)
            throws SQLException {
        Person.PersonGroup group = new Person.PersonGroup();
        group.setGroupId(new ObjectId(resultSet.getString("groups.object_id")));
        group.setRole(resultSet.getString("person_groups.role"));
        group.setFocused(resultSet.getBoolean("person_groups.focused"));
        return group;
    }
}
