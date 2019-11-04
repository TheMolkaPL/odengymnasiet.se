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

package se.odengymnasiet.falafel;

import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.sql.SQLRepository;
import se.odengymnasiet.sql.SQLTable;
import se.odengymnasiet.sql.SQLTableName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RepositoryHandler(FalafelRepository.class)
@SQLTableName("menus")
public class SQLFalafelRepository extends SQLRepository<Falafel>
                                  implements FalafelRepository {
    public SQLFalafelRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected Falafel createModel() {
        return new Falafel();
    }

    @Override
    public Falafel deserialize(ResultSet resultSet) throws SQLException {
        Falafel falafel = super.deserialize(resultSet);
        falafel.setYear(resultSet.getShort("year"));
        falafel.setWeek(resultSet.getShort("week"));
        falafel.setDay(DayOfWeek.of(resultSet.getByte("day")));
        falafel.setDishes(this.findDishes(resultSet.getInt("id")));
        return falafel;
    }

    @Override
    public Collection<Falafel> findAllFor(int year, int week) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE year=? AND week=?;");
            statement.setShort(1, (short) year);
            statement.setShort(2, (short) week);

            ResultSet resultSet = statement.executeQuery();

            List<Falafel> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserialize(resultSet));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    private List<String> findDishes(int falafelId) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM menu_dishes WHERE menu_id=?;");
            statement.setInt(1, falafelId);

            ResultSet resultSet = statement.executeQuery();

            List<String> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(resultSet.getString("text"));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }
}
