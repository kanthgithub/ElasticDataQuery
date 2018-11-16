package com.elasticDataQuery.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.elasticDataQuery.common.DateTimeUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DateTimeUtilTest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void assert_Get_CurrentTime_In_EpochMillis(){

        //given & when

        Long currentTimeInEpochMillis =
                DateTimeUtil.getCurrentTimeStampInEpochMillis();


        //then
        assertNotNull(currentTimeInEpochMillis);

        System.currentTimeMillis();

        log.info("currentTime In EpochMillis : {}",currentTimeInEpochMillis);

    }

    @Test
    public void assert_Get_Delta_Of_TimeString_In_Epoch(){

        //given
        String fileString = "string-generation-2018093016.log";

        Long expectedTimeInEpoch = Long.valueOf(1538294400);

        //when
        Long timeStringAsEpoch = DateTimeUtil.getTimeStampInEpochMillis(fileString);

        //then
        assertNotNull(timeStringAsEpoch);
        assertEquals(expectedTimeInEpoch,timeStringAsEpoch);

    }


    @Test
    public void assert_Get_Hour_Unit_From_Time(){

        //given
        String fileString = "string-generation-2018093016.log";

        LocalDateTime localDateTime = getDateTimeFromFileString(fileString);

        //when
        int hourUnit = getHourUnitFromTime(localDateTime);

        //then
        assertEquals(16,hourUnit);
    }


    @Test
    public void assert_Get_TimeDifference_In_EpochMillis(){

        //given
        String fileString = "string-generation-2018093016.log";

        //when
        Long timeDifference = getTimeDifferenceInEpochMillis(fileString);

        //then
        assertNotNull(timeDifference);

    }


    @Test
    public void assert_Get_PastTime_In_Epoch(){

        //given
        int hours = 4;

        //when
        Long pastTimeInEpoch = convertPastTimeInHoursToEpochMillis(hours);

        //then
        assertNotNull(pastTimeInEpoch);
    }


    @Test
    public void assert_Get_FutureTime_In_Epoch(){

        //given
        int hours = 4;

        //when
        Long futureTimeInEpoch = getFutureTimeInEpochMillis(hours);

        //then
        assertNotNull(futureTimeInEpoch);
    }

}
