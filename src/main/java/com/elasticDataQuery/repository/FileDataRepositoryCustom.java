package com.elasticDataQuery.repository;

import com.elasticDataQuery.entity.FileData;

import java.util.List;

public interface FileDataRepositoryCustom {

    /**
     * match by word Content & the timeStamp >= deltaTimeInEpochMillis
     *
     * @param word
     * @return Collection of FileData Entities
     */
    List<FileData> findByContent(String word);

    /**
     *
     * match by word Content & the timeStamp >= deltaTimeInEpochMillis
     *
     * @param word
     * @param deltaTimeInHours
     * @return Collection of FileData Entities
     */
    public List<FileData> findByContentAndTimeLimit(String word, Long deltaTimeInHours);

}
