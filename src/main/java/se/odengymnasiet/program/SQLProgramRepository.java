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

package se.odengymnasiet.program;

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

@RepositoryHandler(ProgramRepository.class)
@SQLTableName("programs")
public class SQLProgramRepository extends SQLRepository<Program>
                                  implements ProgramRepository {

    public SQLProgramRepository(SQLTable table) {
        super(table);
    }

    @Override
    protected Program createModel() {
        return new Program();
    }

    @Override
    public Program deserialize(ResultSet resultSet) throws SQLException {
        Program program = super.deserialize(resultSet);
        program.setTitle(resultSet.getString("title"));
        program.setSubtitle(resultSet.getString("subtitle"));
        program.setSubtitle(resultSet.getString("path"));
        program.setIcon(resultSet.getString("icon"));
        program.setPriority(resultSet.getInt("priority"));
        program.setMarketing(resultSet.getString("marketing"));
        program.setRecommended(resultSet.getBoolean("recommended"));
        program.setOpen(resultSet.getBoolean("open"));
        program.setFiles(this.findFiles(resultSet.getInt("id")));
        return program;
    }

    @Override
    public Collection<Program> findAllOpen() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE open=?;");
            statement.setBoolean(1, true);

            ResultSet resultSet = statement.executeQuery();

            List<Program> results = new ArrayList<>();
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
    public Collection<Program> findAllRecommended() {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = this.createStatement(connection,
                    "SELECT * FROM %s WHERE recommended=?;");
            statement.setBoolean(1, true);

            ResultSet resultSet = statement.executeQuery();

            List<Program> results = new ArrayList<>();
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
    public Program findByPath(String path) {
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

    private List<ProgramFile> findFiles(int programId) {
        try (Connection connection = this.getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM program_files WHERE program_id=?;");
            statement.setInt(1, programId);

            ResultSet resultSet = statement.executeQuery();

            List<ProgramFile> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(this.deserializeFile(resultSet));
            }
            return results;
        } catch (SQLException e) {
            this.catchException(e);
        }

        return Collections.emptyList();
    }

    private ProgramFile deserializeFile(ResultSet resultSet) throws SQLException {
        ProgramFile programFile = new ProgramFile();
        programFile.setName(resultSet.getString("name"));
        programFile.setUrl(resultSet.getString("url"));
        programFile.setFormat(resultSet.getString("format"));
        programFile.setSize(resultSet.getDouble("size"));
        return programFile;
    }
}
