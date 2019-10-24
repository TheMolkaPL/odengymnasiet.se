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

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import se.odengymnasiet.Application;
import se.odengymnasiet.Database;
import se.odengymnasiet.Repository;
import se.odengymnasiet.article.SQLArticleRepository;
import se.odengymnasiet.contact.SQLGroupRepository;
import se.odengymnasiet.contact.SQLPersonRepository;
import se.odengymnasiet.falafel.SQLFalafelRepository;
import se.odengymnasiet.index.SQLMarketingRepository;
import se.odengymnasiet.openhouse.SQLOpenHouseRepository;
import se.odengymnasiet.program.SQLProgramRepository;
import se.odengymnasiet.student.SQLStudentServiceRepository;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * SQL-like database implementation.
 */
public class SQLDatabase extends Database {

    private HikariDataSource dataSource;

    public SQLDatabase(Application app,
                       Logger logger,
                       HikariDataSource dataSource) {
        super(app, logger);

        this.dataSource = dataSource;
    }

    @Override
    public void connect() throws Exception {
        try (Connection connection = this.dataSource.getConnection()) {
            connection.prepareStatement("SELECT 1;").execute();
        }
    }

    @Override
    public void disconnect() throws Exception {
        this.dataSource.close();
    }

    @Override
    public void installDefaultRepositories(Consumer<Repository> consumer) {
        Arrays.asList(
                SQLArticleRepository.class,
                SQLFalafelRepository.class,
                SQLGroupRepository.class,
                SQLMarketingRepository.class,
                SQLOpenHouseRepository.class,
                SQLPersonRepository.class,
                SQLProgramRepository.class,
                SQLStudentServiceRepository.class
        ).forEach(clazz -> {
            try {
                consumer.accept(this.loadRepository(clazz));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public String getTableName(String defaultName) {
        return defaultName;
    }

    public Repository loadRepository(Class<? extends Repository> repository)
            throws ReflectiveOperationException {
        SQLTableName tableName = repository
                .getDeclaredAnnotation(SQLTableName.class);
        if (tableName == null) {
            return null;
        }

        Constructor<?> constructor = repository
                .getConstructor(SQLTable.class);
        constructor.setAccessible(true);

        String name = this.getTableName(tableName.value());
        if (name == null) {
            return null;
        }

        SQLTable table = new SQLTable(name, this.dataSource);
        return (Repository) constructor.newInstance(table);
    }
}
