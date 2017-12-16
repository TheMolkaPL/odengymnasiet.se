package se.odengymnasiet.util;

public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static String numberToTime(int number) {
        return number <= 9 ? "0" + number : Integer.toString(number);
    }
}
