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

import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.sql.SQLRepository;
import se.odengymnasiet.sql.SQLTable;
import se.odengymnasiet.sql.SQLTableName;

import java.sql.ResultSet;
import java.sql.SQLException;

@RepositoryHandler(GroupRepository.class)
@SQLTableName("groups")
public class SQLGroupRepository extends SQLRepository<Group>
                                implements GroupRepository {

    public SQLGroupRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected Group createModel() {
        return new Group();
    }

    @Override
    public Group deserialize(ResultSet resultSet) throws SQLException {
        Group group = super.deserialize(resultSet);
        group.setName(resultSet.getString("name"));
        group.setRoleName(resultSet.getString("role_name"));
        group.setPriority(resultSet.getInt("priority"));
        return group;
    }
}
