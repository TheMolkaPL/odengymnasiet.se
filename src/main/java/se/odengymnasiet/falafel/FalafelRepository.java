package se.odengymnasiet.falafel;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public interface FalafelRepository extends Repository<Falafel> {

    FalafelRepository LOCAL = new LocalFalafelRepository();

    @Deprecated
    default Collection<Falafel> findAllFor(int week) {
        return this.findAllFor(LocalDate.now().getYear(), week);
    }

    Collection<Falafel> findAllFor(int year, int week);

    default Collection<Falafel> findAllForNow() {
        LocalDate now = LocalDate.now();
        return this.findAllFor(now.getYear(), now.get(Falafel.WEEK_FIELD));
    }
}

@RepositoryHandler(FalafelRepository.class)
class LocalFalafelRepository extends LocalRepository<Falafel>
                             implements FalafelRepository {

    @Override
    public Collection<Falafel> findAllFor(int year, int week) {
        return this.container.values().stream()
                .filter(falafel -> falafel.getYear() == year &&
                                   falafel.getWeek() == week)
                .collect(Collectors.toList());
    }
}
