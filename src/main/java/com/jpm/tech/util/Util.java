package com.jpm.tech.util;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public final class Util {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu");

    private Util() {
    }

    public static String format(TemporalAccessor temporalAccessor) {
        return DEFAULT_FORMATTER.format(temporalAccessor);
    }

    public static boolean isSundayCurrency(String currency) {
        return "AED".equals(currency) || "SAR".equals(currency);
    }

    public static long dateOffsetToWeekday(LocalDate date, DayOfWeek endDay) {
        long result = 0L;
        if (date != null && endDay != null) {
            int dateValue = date.getDayOfWeek().getValue();
            int endValue = endDay.getValue();

            // this control may be misleading when sunday is a weekday.
            // V = Value, W = Weekday, O = Offset
            // DAY  V W O W O
            // ==============
            // MOND 1 x 0 y 0
            // TUSD 2 x 0 y 0
            // WEDD 3 x 0 y 0
            // THUR 4 x 0 y 0
            // FRID 5 x 0   2
            // SATD 6   2   1
            // SUND 7   1 y 0
            if (dateValue > endValue) {
                result = endValue + 3 - dateValue;
            }
        }
        return result;
    }
}
