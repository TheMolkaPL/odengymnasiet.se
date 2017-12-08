package se.odengymnasiet.student;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface StudentServiceRepository extends Repository<StudentService> {

    StudentServiceRepository LOCAL = new LocalStudentServiceRepository();

    Collection<StudentService> findNavbar();
}

@RepositoryHandler(StudentServiceRepository.class)
class LocalStudentServiceRepository extends LocalRepository<StudentService>
                                    implements StudentServiceRepository {

    @Override
    public Collection<StudentService> findNavbar() {
        return this.container.values().stream()
                .filter(StudentService::isNavbar)
                .collect(Collectors.toList());
    }
}
