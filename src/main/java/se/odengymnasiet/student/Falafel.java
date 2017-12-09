package se.odengymnasiet.student;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Collections;
import java.util.List;

public class Falafel extends Model implements Comparable<Falafel> {

    public static final Falafel NULL = new NullFalafel();

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

    public boolean isToday() {
        LocalDate now = LocalDate.now();
        int week = now.get(WeekFields.ISO.weekOfWeekBasedYear());

        return this.getYear() == now.getYear() &&
                this.getWeek() == week &&
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
        return this.now().get(WeekFields.ISO.weekOfWeekBasedYear());
    }

    @Override
    public DayOfWeek getDay() {
        return this.now().getDayOfWeek();
    }

    @Override
    public List<String> getDishes() {
        return Collections.emptyList();
    }

    private LocalDate now() {
        return LocalDate.now();
    }
}
