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

package se.odengymnasiet.student;

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

@RepositoryHandler(StudentServiceRepository.class)
@SQLTableName("student_services")
public class SQLStudentServiceRepository extends SQLRepository<StudentService>
                                         implements StudentServiceRepository {

    public SQLStudentServiceRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected StudentService createModel() {
        return new StudentService();
    }

    @Override
    public StudentService deserialize(ResultSet resultSet) throws SQLException {
        StudentService studentService = super.deserialize(resultSet);
        studentService.setName(resultSet.getString("name"));
        studentService.setUrl(resultSet.getString("url"));
        studentService.setPriority(resultSet.getInt("priority"));
        studentService.setNavbar(resultSet.getBoolean("navbar"));
        return studentService;
    }

    @Override
    public Collection<StudentService> findAllForNavbar() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE navbar=?;");
            statement.setBoolean(1, true);

            ResultSet resultSet = statement.executeQuery();

            List<StudentService> results = new ArrayList<>();
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
