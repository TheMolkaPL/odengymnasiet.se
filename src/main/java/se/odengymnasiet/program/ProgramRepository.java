package se.odengymnasiet.program;

import se.odengymnasiet.Repository;

public interface ProgramRepository extends Repository<Program> {
    Program findByPath(String path);
}
