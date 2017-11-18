package se.odengymnasiet.program;

import org.bson.types.ObjectId;
import se.odengymnasiet.RepositoryHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RepositoryHandler(ProgramRepository.class)
public class LocalProgramRepository implements ProgramRepository {
    private Collection<Program> container = new ArrayList<>();

    @Override
    public void delete(ObjectId id) {
        this.container.removeIf(program -> program.getId().equals(id));
    }

    @Override
    public Program find(ObjectId id) {
        return this.container.stream()
                .filter(program -> program.getId().equals(id))
                .findAny()
                .get();
    }

    @Override
    public Collection<Program> findAll() {
        return new ArrayList<>(this.container);
    }

    @Override
    public Program findByPath(String path) {
        return this.container.stream()
                .filter(program -> program.getPath().equals(path))
                .findAny()
                .get();
    }

    @Override
    public void update(Program model) {
        this.container = this.container.stream().map(program -> {
            if (program.getId().equals(model.getId())) {
                return model;
            } else {
                return program;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void save(Program model) {
        this.container.add(model);
    }
}
