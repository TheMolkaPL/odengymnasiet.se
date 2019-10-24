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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdom2.Element;
import org.slf4j.Logger;
import se.odengymnasiet.Application;
import se.odengymnasiet.Database;
import se.odengymnasiet.DatabaseFactory;

import java.util.Properties;

/**
 * Producing a SQL-like database.
 */
public class SQLDatabaseFactory implements DatabaseFactory {

    @Override
    public Database newDatabase(Application app,
                                Logger logger,
                                Element configuration) {
        Properties properties = new Properties();
        configuration.getChildren().forEach(element -> {
            properties.setProperty(element.getName(), element.getTextTrim());
        });

        HikariConfig config = new HikariConfig(properties);
        return new SQLDatabase(app, logger, new HikariDataSource(config));
    }
}
