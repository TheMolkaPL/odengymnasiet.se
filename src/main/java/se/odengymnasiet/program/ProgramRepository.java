package se.odengymnasiet.program;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

public interface ProgramRepository extends Repository<Program> {

    ProgramRepository LOCAL = new LocalProgramRepository();

    Program findByPath(String path);
}

@RepositoryHandler(ProgramRepository.class)
class LocalProgramRepository extends LocalRepository<Program>
                             implements ProgramRepository {

    @Override
    public Program findByPath(String path) {
        return this.container.values().stream()
                .filter(program -> program.getPath().equals(path))
                .findFirst().get();
    }
}
