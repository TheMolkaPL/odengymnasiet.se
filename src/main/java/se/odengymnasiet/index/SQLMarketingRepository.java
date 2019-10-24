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

package se.odengymnasiet.index;

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

@RepositoryHandler(MarketingRepository.class)
@SQLTableName("marketings")
public class SQLMarketingRepository extends SQLRepository<Marketing>
                                    implements MarketingRepository {

    public SQLMarketingRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected Marketing createModel() {
        return new Marketing();
    }

    @Override
    public Marketing deserialize(ResultSet resultSet) throws SQLException {
        Marketing marketing = super.deserialize(resultSet);
        marketing.setImage(resultSet.getString("image"));
        marketing.setFixed(resultSet.getBoolean("fixed"));
        marketing.setPosition(resultSet.getDouble("position"));
        marketing.setDeployed(resultSet.getBoolean("deployed"));
        return marketing;
    }

    @Override
    public Collection<Marketing> findAllDeployed() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE deployed=?;");
            statement.setBoolean(1, true);

            ResultSet resultSet = statement.executeQuery();

            List<Marketing> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserialize(resultSet));
            }

            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }
}
