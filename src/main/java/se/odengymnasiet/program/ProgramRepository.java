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

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface ProgramRepository extends Repository<Program> {

    ProgramRepository LOCAL = new LocalProgramRepository();

    Collection<Program> findAllOpen();

    Collection<Program> findAllRecommended();

    Program findByPath(String path);
}

@RepositoryHandler(ProgramRepository.class)
class LocalProgramRepository extends LocalRepository<Program>
                             implements ProgramRepository {

    @Override
    public Collection<Program> findAllOpen() {
        return this.container.values().stream()
                .filter(Program::isOpen)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Program> findAllRecommended() {
        return this.container.values().stream()
                .filter(Program::isRecommended)
                .collect(Collectors.toList());
    }

    @Override
    public Program findByPath(String path) {
        return this.container.values().stream()
                .filter(program -> program.getPath().equals(path))
                .findFirst().get();
    }
}
