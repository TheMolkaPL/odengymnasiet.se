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
