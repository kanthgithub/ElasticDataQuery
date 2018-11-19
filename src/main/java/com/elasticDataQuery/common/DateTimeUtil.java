package com.elasticDataQuery.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    /**
     *
     * @param deltaHours
     * @return Long (time In Epoch Seconds of pastHour)
     */
    public static Long convertPastTimeInHoursToEpochMillis(int deltaHours){

        LocalDateTime pastTime = LocalDateTime.now().plusHours(-1 * deltaHours);
        Instant instant2 = pastTime.toInstant(ZONE_OFFSET);
        return instant2.toEpochMilli();
    }


}
