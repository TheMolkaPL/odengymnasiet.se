package se.odengymnasiet.util;

/**
 * Date time utilities
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * Return given number in the "00" format. If the number is smaller than 10,
     * the number is prefixed with a zero, if not - the number is returned.
     */
    public static String numberToTime(int number) {
        return number <= 9 ? "0" + number : Integer.toString(number);
    }
}
