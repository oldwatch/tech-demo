package com.demo.newfeature.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DatetimeUtils {

    public static LocalDateTime getLocalTime(long timestamp){
        var instant= Instant.ofEpochSecond(timestamp);

        var localZoneId = ZoneId.of("Asia/Shanghai"); // Get system's default time zone

        return LocalDateTime.ofInstant(instant,localZoneId);

    }
}
