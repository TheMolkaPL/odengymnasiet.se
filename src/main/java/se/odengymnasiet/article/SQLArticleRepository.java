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

package se.odengymnasiet.article;

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

@RepositoryHandler(ArticleRepository.class)
@SQLTableName("articles")
public class SQLArticleRepository extends SQLRepository<Article>
                                  implements ArticleRepository {

    public SQLArticleRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected Article createModel() {
        return new Article();
    }

    @Override
    public Article deserialize(ResultSet resultSet) throws SQLException {
        Article article = super.deserialize(resultSet);
        article.setTitle(resultSet.getString("title"));
        article.setPath(resultSet.getString("path"));
        article.setPriority(resultSet.getInt("priority"));
        article.setText(resultSet.getString("text"));
        return article;
    }

    @Override
    public Collection<Article> findAllByStartingPath(String startingPath) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE path LIKE ?;");
            statement.setString(1, startingPath + "%");

            ResultSet resultSet = statement.executeQuery();

            List<Article> results = new ArrayList<>();
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
    public Article findByPath(String path) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE path=? LIMIT 1;");
            statement.setString(1, path);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return this.deserialize(resultSet);
            }
        } catch (SQLException e) {
            this.catchException(e);
        }

        return null;
    }
}
