package com.elasticDataQuery.common;

import org.springframework.util.StringUtils;

public class StringFrequencyUtil {

    /**
     * sample input: 1541936473924,testString for extraction
     *
     * output: 1541936473924
     *
     * @param logString
     * @return timeStampInEpochMillis extracted from log-line
     */
    public static Long getTimeStampInEpochFromLogString(String logString){

        Long timeStampInEpoch = null;

        if(!StringUtils.isEmpty(logString)) {

            int timeStampEpochIndex = logString.indexOf(",");

            String timeStampLogInEpoch = logString.substring(0, timeStampEpochIndex);

            timeStampInEpoch = timeStampLogInEpoch != null ? Long.valueOf(timeStampLogInEpoch) : null;
        }

        return timeStampInEpoch;
    }

    /**
     * sample input: 1541936473924,testString for extraction
     *
     * output: 1541936473924
     *
     * @param logString
     * @return stringContent extracted from log-line
     */
    public static String getStringContentFromLogString(String logString){

        String content = "";

        if(!StringUtils.isEmpty(logString)) {

            int timeStampEpochIndex = logString.indexOf(",");

            content = logString.substring(timeStampEpochIndex + 1, logString.length());
        }

        return content;
    }

}
