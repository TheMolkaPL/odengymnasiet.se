package se.odengymnasiet.falafel;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Collections;
import java.util.List;

/**
 * Representation of a single school lunch day. Every day is provided by its
 * year and week number. School lunches can hold as many dishes as you want. The
 * dishes are not sorted (because we want to have main dish first, and the
 * vegetarian after it).
 *
 * You maybe wonder why this class is called after falafels. I'm not a big fan
 * of falafels, but there is a kebab restaurant in Stockholm which is called
 * Falafelkungen. I ate there ALL my school lunches on the food card...
 * That's why this is called Falafel :D
 */
public class Falafel extends Model implements Comparable<Falafel> {

    public static final Falafel NULL = new NullFalafel();

    public static final WeekFields WEEK_FIELDS = WeekFields.ISO;
    public static final TemporalField WEEK_FIELD =
            WEEK_FIELDS.weekOfWeekBasedYear();
    public static final TemporalField DAY_FIELD = WEEK_FIELDS.dayOfWeek();

    public static final String FIELD_YEAR = "year";
    public static final String FIELD_WEEK = "week";
    public static final String FIELD_DAY = "day";
    public static final String FIELD_DISHES = "dishes";

    private int year;
    private int week;
    private DayOfWeek day;
    private List<String> dishes;

    public Falafel() {
        super();
    }

    public Falafel(ObjectId id) {
        super(id);
    }

    public Falafel(Document data) {
        super(data);
    }

    @Override
    public int compareTo(Falafel o) {
        int compare = Integer.compare(this.getYear(), o.getYear());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(this.getWeek(), o.getWeek());
        if (compare != 0) {
            return compare;
        }

        return Integer.compare(this.getDay().getValue(), o.getDay().getValue());
    }

    public int getYear() {
        return this.year;
    }

    public int getWeek() {
        return this.week;
    }

    public DayOfWeek getDay() {
        return this.day;
    }

    /**
     * Should maybe be moved to a utility class?
     */
    public String getDayName() {
        switch (this.getDay()) {
            case MONDAY: return "måndag";
            case TUESDAY: return "tisdag";
            case WEDNESDAY: return "onsdag";
            case THURSDAY: return "torsdag";
            case FRIDAY: return "fredag";
            case SATURDAY: return "lördag";
            case SUNDAY: return "söndag";
            default: throw new IllegalArgumentException();
        }
    }

    public List<String> getDishes() {
        return this.dishes;
    }

    public LocalDate getLocalDate() {
        return LocalDate.now().withYear(this.getYear())
                              .with(WEEK_FIELD, this.getWeek())
                              .with(DAY_FIELD, this.getDay().getValue());
    }

    public boolean isToday() {
        LocalDate now = LocalDate.now();

        return this.getYear() == now.getYear() &&
                this.getWeek() == now.get(WEEK_FIELD) &&
                this.getDay().equals(now.getDayOfWeek());
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setDishes(List<String> dishes) {
        Collections.sort(dishes);
        this.dishes = dishes;
    }

    public static Falafel of(int year) {
        Falafel falafel = new Falafel();
        falafel.setYear(year);
        return falafel;
    }

    public static Falafel of(int year, int week) {
        Falafel falafel = new Falafel();
        falafel.setYear(year);
        falafel.setWeek(week);
        return falafel;
    }

    public static Falafel of(int year, int week, int dayOfWeek) {
        return of(year, week, DayOfWeek.of(dayOfWeek));
    }

    public static Falafel of(int year, int week, DayOfWeek day) {
        Falafel falafel = new Falafel();
        falafel.setYear(year);
        falafel.setWeek(week);
        falafel.setDay(day);
        return falafel;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_YEAR, this.getYear());
        data.put(FIELD_WEEK, this.getWeek());
        data.put(FIELD_DAY, this.getDay().getValue());
        data.put(FIELD_DISHES, this.getDishes());
        return super.serialize(data);
    }

    public static Falafel deserialize(Document data) {
        Falafel falafel = new Falafel(data);
        falafel.setYear(data.getInteger(FIELD_YEAR));
        falafel.setWeek(data.getInteger(FIELD_WEEK));
        falafel.setDay(DayOfWeek.of(data.getInteger(FIELD_DAY)));
        falafel.setDishes(data.get(FIELD_DISHES, List.class));
        return falafel;
    }
}

class NullFalafel extends Falafel {

    @Override
    public int getYear() {
        return this.now().getYear();
    }

    @Override
    public int getWeek() {
        return this.now().get(WEEK_FIELD);
    }

    @Override
    public DayOfWeek getDay() {
        return this.now().getDayOfWeek();
    }

    @Override
    public List<String> getDishes() {
        return Collections.emptyList();
    }

    @Override
    public LocalDate getLocalDate() {
        return this.now();
    }

    private LocalDate now() {
        return LocalDate.now();
    }
}
