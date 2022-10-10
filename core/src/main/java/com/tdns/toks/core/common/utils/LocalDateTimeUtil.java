package com.tdns.toks.core.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
@Component
public class LocalDateTimeUtil {
    private static final ZoneId BASE_ZONE_ID = ZoneId.of("Asia/Seoul");

    public static String addTimeZone(LocalDateTime localDateTime) {
        if ( localDateTime != null ){
            return localDateTime.atZone(BASE_ZONE_ID).truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } else {
            return null;
        }
    }
}
