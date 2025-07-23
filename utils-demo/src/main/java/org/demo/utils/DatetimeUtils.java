package org.demo.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DatetimeUtils {

    public static final String TIME_ZONE = "Asia/Shanghai";

    public static LocalDateTime getLocalTime(long timestamp) {
        var instant = Instant.ofEpochSecond(timestamp);

        var localZoneId = ZoneId.of(TIME_ZONE); // Get system's default time zone

        return LocalDateTime.ofInstant(instant, localZoneId);

    }

    public static Long getTimestamp(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        var zonedDateTime = date.atZone(ZoneId.of(TIME_ZONE));
        return zonedDateTime.toEpochSecond();
    }
}
