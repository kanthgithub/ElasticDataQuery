package com.elasticDataQuery.common;

import org.apache.commons.lang3.StringUtils;
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
     * string-generation-{yyyymmddhh}.log​. E.g. the file with name ​string-generation-2018093016.log
     *
     * @param fileName
     * @return LocalDateTime
     */
    public static LocalDateTime getDateTimeFromFileString(String fileName){

        log.info("extracting DateTime for file-name-String: {}",fileName);

        LocalDateTime localDateTime = null;

        String[] fileSplitString = fileName.split("-");

        log.info("split file-name-String: {}",fileSplitString);

        int length = fileSplitString.length;

        if(length >0){

            String[] timeStringSplit = fileSplitString[length-1].split("\\.");

            log.info("timeStringSplit for parsing: {}",timeStringSplit);

            String timeString = timeStringSplit!=null && timeStringSplit.length >0 ? timeStringSplit[0] : null;

            log.info("timeString for parsing: {}",timeString);


            localDateTime = StringUtils.isNotBlank(timeString) ? LocalDateTime.parse(timeString, formatter).atOffset(ZONE_OFFSET).toLocalDateTime() : null;
        }

        return localDateTime;
    }

    /**
     * validates if the fileName is compliant
     *
     * @param fileNameString
     * @return Boolean
     */
    public static Boolean isAValidFileFormat(String fileNameString){

        return getDateTimeFromFileString(fileNameString)!=null;

    }

    /**
     *
     * @param localDateTime
     * @return int
     */
    public static int getHourUnitFromTime(LocalDateTime localDateTime){

        return localDateTime.getHour();

    }

    /**
     *
     * @param dateTimeString
     * @return hour unit of time
     */
    public static int getHourUnitFromString(String dateTimeString){

        LocalDateTime localDateTime = getDateTimeFromFileString(dateTimeString);

        return localDateTime!=null ? getHourUnitFromTime(localDateTime) : 0;
    }

    /**
     *
     * @return timeStamp in epochMillis
     */
    public static Long getCurrentTimeStampInEpochMillis(){

        Instant instant = Instant.now().atOffset(ZONE_OFFSET).toInstant();
        return instant.toEpochMilli();
    }

    /**
     *
     * @return timeStamp in LocalDateTime
     */
    public static LocalDateTime getCurrentTimeStamp(){

        return LocalDateTime.now().atOffset(ZONE_OFFSET).toLocalDateTime();
    }


    /**
     *
     * @param fileString
     * @return
     */
    public static Long getTimeStampInEpochMillis(String fileString){

        LocalDateTime localDateTime = getDateTimeFromFileString(fileString);

        return localDateTime.toEpochSecond(ZONE_OFFSET);
    }

    /**
     *
     * @param fileString
     * @return
     */
    public static Long getTimeDifferenceInEpochMillis(String fileString){

        return getCurrentTimeStampInEpochMillis() - getTimeStampInEpochMillis(fileString);

    }

    /**
     *
     * computes time difference in hours
     *
     * fileString contains hours and compare it with current time and get how past is that from current time
     *
     * Default: 0 Hours
     *
     * @param fileString
     * @return Long
     */
    public Long getTimeDifferenceInHours(String fileString){

        Long timeDeltaInMilliSeconds = getTimeDifferenceInEpochMillis(fileString);

        Long hours = Long.valueOf(0);

        if (timeDeltaInMilliSeconds > HOUR) {
            hours = timeDeltaInMilliSeconds % HOUR;
        }

        return hours;
    }


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

    /**
     *
     * @param deltaSeconds
     * @return Long (time In Epoch Seconds of pastSeconds)
     */
    public static Long convertPastTimeInSecondsToEpochMillis(int deltaSeconds){

        LocalDateTime pastTime = LocalDateTime.now().plusHours(-1 * (deltaSeconds/3600));
        Instant instant2 = pastTime.toInstant(ZONE_OFFSET);
        return instant2.toEpochMilli();
    }

    /**
     *
     * @param deltaHours
     * @return Long (time In Epoch Seconds of futureHour)
     */
    public static Long getFutureTimeInEpochMillis(int deltaHours){

        LocalDateTime pastTime = LocalDateTime.now().plusHours(deltaHours);
        Instant instant2 = pastTime.toInstant(ZONE_OFFSET);
        return instant2.toEpochMilli();
    }

}
