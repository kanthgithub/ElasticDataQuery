package com.elasticDataQuery.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.elasticDataQuery.common.DateTimeUtil.convertPastTimeInHoursToEpochMillis;
import static org.junit.Assert.assertNotNull;

public class DateTimeUtilTest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void assert_Get_PastTime_In_Epoch(){

        //given
        int hours = 4;

        //when
        Long pastTimeInEpoch = convertPastTimeInHoursToEpochMillis(hours);

        //then
        assertNotNull(pastTimeInEpoch);
    }

}
