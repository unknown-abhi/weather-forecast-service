package org.implantbase.weatherforecastservice.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static String date(long ts) {
        return Instant.ofEpochSecond(ts)
                .atZone(ZoneId.systemDefault())
                .format(FORMAT);
    }

    public static String day(long ts) {
        return Instant.ofEpochSecond(ts)
                .atZone(ZoneId.systemDefault())
                .getDayOfWeek()
                .toString();
    }


}
