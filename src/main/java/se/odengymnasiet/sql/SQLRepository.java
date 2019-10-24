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

package se.odengymnasiet.sql;

import org.bson.types.ObjectId;
import se.odengymnasiet.Model;
import se.odengymnasiet.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class SQLRepository<E extends Model>
        implements Repository<E> {

    private final DataSource dataSource;
    private final String tableName;

    protected SQLRepository(SQLTable table) {
        this.dataSource = table.getDataSource();
        this.tableName = table.getName();
    }

    protected abstract E createModel();

    public E deserialize(ResultSet resultSet) throws SQLException {
        E model = this.createModel();
        model.setId(new ObjectId(resultSet.getString("object_id")));
        model.setUpdatedAt(resultSet.getTimestamp("updated_at").toInstant());
        return model;
    }

    @Override
    public long count() {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT COUNT(*) AS count FROM %s;");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            this.catchException(e);
        }

        return -1;
    }

    @Override
    public boolean contains(ObjectId id) {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE object_id=?;");
            statement.setString(1, id.toString());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            this.catchException(e);
        }

        return false;
    }

    @Override
    public void delete(ObjectId id) {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "DELETE FROM %s WHERE object_id=? LIMIT 1;");
            statement.setString(1, id.toString());

            statement.execute();
        } catch (SQLException e) {
            this.catchException(e);
        }
    }

    @Override
    public E find(ObjectId id) {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE object_id=?;");
            statement.setString(1, id.toString());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return this.deserialize(resultSet);
            }
        } catch (SQLException e) {
            this.catchException(e);
        }

        return null;
    }

    @Override
    public Collection<E> findAll() {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s;");

            ResultSet resultSet = statement.executeQuery();

            List<E> results = new ArrayList<>();
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
    public void update(E model) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public void save(E model) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public String getTableName() {
        return this.tableName;
    }

    protected void catchException(Exception e) {
        e.printStackTrace();
    }

    protected PreparedStatement createStatement(Connection connection,
                                                String query)
            throws SQLException {
        String formatted = String.format(query, this.getTableName());
        return connection.prepareStatement(formatted);
    }
}
