package com.solvd.taxi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter DB_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter UI_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static String formatForDatabase(LocalDateTime dateTime) {
        return dateTime.format(DB_FORMATTER);
    }

    public static String formatForDisplay(LocalDateTime dateTime) {
        return dateTime.format(UI_FORMATTER);
    }

    public static LocalDateTime parseFromDatabase(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DB_FORMATTER);
    }

    public static boolean isWithinLast24Hours(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now().minusHours(24));
    }
}