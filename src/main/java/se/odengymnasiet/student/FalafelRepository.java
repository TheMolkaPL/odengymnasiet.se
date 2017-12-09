package se.odengymnasiet.student;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Collection;
import java.util.stream.Collectors;

public interface FalafelRepository extends Repository<Falafel> {

    FalafelRepository LOCAL = new LocalFalafelRepository();

    @Deprecated
    default Collection<Falafel> findFor(int week) {
        return this.findFor(LocalDate.now().getYear(), week);
    }

    Collection<Falafel> findFor(int year, int week);

    default Collection<Falafel> findForNow() {
        LocalDate now = LocalDate.now();
        TemporalField field = WeekFields.ISO.weekOfWeekBasedYear();
        return this.findFor(now.getYear(), now.get(field));
    }
}

@RepositoryHandler(FalafelRepository.class)
class LocalFalafelRepository extends LocalRepository<Falafel>
                             implements FalafelRepository {

    @Override
    public Collection<Falafel> findFor(int year, int week) {
        return this.container.values().stream()
                .filter(falafel -> falafel.getYear() == year &&
                                   falafel.getWeek() == week)
                .collect(Collectors.toList());
    }
}
