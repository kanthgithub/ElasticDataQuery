package com.elasticDataQuery.service;

import com.elasticDataQuery.entity.FileData;

import java.util.List;

public interface FileDataQueryService {

    /**
     *
     * @return
     */
    public List<FileData> getAllFileDataEntities();

    List<FileData> findByContent(String word);

    /**
     *
     * @param word
     * @param deltaTimeInEpochMillis
     * @return
     */
    public List<FileData> findByContentAndTimeLimit(String word, Long deltaTimeInEpochMillis);


    /**
     *
     * @param word
     * @param deltaTimeInHours
     * @return
     */
    Long getStatsAggregationDataAndTimeRank(String word, Long deltaTimeInHours);


    /**
     *
     * @param word
     * @param deltaTimeInHours
     * @return Boolean
     */
    Boolean validateStringRank(String word, Long deltaTimeInHours);
}
