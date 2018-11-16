package com.elasticDataQuery.common;

import org.junit.Test;

import static com.elasticDataQuery.common.StringFrequencyUtil.getStringContentFromLogString;
import static com.elasticDataQuery.common.StringFrequencyUtil.getTimeStampInEpochFromLogString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringFrequencyUtilTest {

    @Test
    public void assert_That_Content_Is_Extracted_From_LogString(){

        //given
        String logString = "1541936473924,testString for extraction";

        //when
        String messageString = getStringContentFromLogString(logString);


        //then
        assertNotNull(messageString);
        assertEquals("testString for extraction",messageString);
    }


    @Test
    public void assert_That_TimeStamp_Is_Extracted_From_LogString(){

        //given
        String logString = "1541936473924,testString for extraction";

        //when
        Long timeStampInEpoch = getTimeStampInEpochFromLogString(logString);


        //then
        assertNotNull(timeStampInEpoch);
        assertEquals(Long.valueOf("1541936473924"),timeStampInEpoch);
    }
}