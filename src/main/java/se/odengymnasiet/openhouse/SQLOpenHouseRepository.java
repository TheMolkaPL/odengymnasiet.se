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

package se.odengymnasiet.openhouse;

import org.bson.types.ObjectId;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.sql.SQLRepository;
import se.odengymnasiet.sql.SQLTable;
import se.odengymnasiet.sql.SQLTableName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RepositoryHandler(OpenHouseRepository.class)
@SQLTableName("open_houses")
public class SQLOpenHouseRepository extends SQLRepository<OpenHouse>
                                    implements OpenHouseRepository {

    public SQLOpenHouseRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected OpenHouse createModel() {
        return new OpenHouse();
    }

    @Override
    public OpenHouse deserialize(ResultSet resultSet) throws SQLException {
        Timestamp end = resultSet.getTimestamp("end");

        OpenHouse openHouse = super.deserialize(resultSet);
        openHouse.setStart(resultSet.getTimestamp("start").toInstant());
        openHouse.setEnd(end == null ? null : end.toInstant());
        openHouse.setDescription(resultSet.getString("description"));
        openHouse.setPrograms(this.findPrograms(resultSet.getInt("id")));
        openHouse.setDeployed(resultSet.getBoolean("deployed"));
        return openHouse;
    }

    @Override
    public Collection<OpenHouse> findAllComing() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE coming >= ?");
            statement.setTimestamp(1, Timestamp.from(Instant.now()));

            ResultSet resultSet = statement.executeQuery();

            List<OpenHouse> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserialize(resultSet));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    @Override
    public Collection<OpenHouse> findAllDeployed() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE start=?;");
            statement.setBoolean(1, true);

            ResultSet resultSet = statement.executeQuery();

            List<OpenHouse> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserialize(resultSet));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    @Override
    public Collection<OpenHouse> findAllDeployedComing() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE deployed=? AND start >= ?;");
            statement.setBoolean(1, true);
            statement.setTimestamp(2, Timestamp.from(Instant.now()));

            ResultSet resultSet = statement.executeQuery();

            List<OpenHouse> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserialize(resultSet));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    private List<ObjectId> findPrograms(int openHouseId) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT object_id " +
                    "FROM programs " +
                    "INNER JOIN open_house_programs " +
                    "ON programs.id = open_house_programs.program_id " +
                    "WHERE open_house_programs.open_house_id=?;");
            statement.setInt(1, openHouseId);

            ResultSet resultSet = statement.executeQuery();

            List<ObjectId> results = new ArrayList<>();
            while (resultSet.next()) {
                String objectId = resultSet.getString("programs.object_id");
                results.add(new ObjectId(objectId));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }
}
