package se.odengymnasiet.mongo;

import java.time.Instant;
import java.util.Date;

public class DBFields {
    public static Date toDate(Instant instant) {
        if (instant != null) {
            return Date.from(instant);
        }

        return null;
    }

    public static Instant toInstant(Date date) {
        if (date != null) {
            return date.toInstant();
        }

        return null;
    }
}
