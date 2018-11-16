package com.elasticDataQuery.service;

import com.elasticDataQuery.entity.FileData;
import com.elasticDataQuery.repository.FileDataRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class FileDataQueryServiceImpl implements  FileDataQueryService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Override
    public List<FileData> getAllFileDataEntities() {
        return Lists.newArrayList(fileDataRepository.findAll());
    }


    @Override
    public List<FileData> findByContent(String word) {
        return fileDataRepository.findByContent(word);
    }

    @Override
    public List<FileData> findByContentAndTimeLimit(String word, Long deltaTimeInHours) {
        return fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours);
    }


    @Override
    public Long getStatsAggregationDataAndTimeRank(String word, Long deltaTimeInHours) {
        List<FileData> fileDataList = findByContentAndTimeLimit(word,deltaTimeInHours);

        return !CollectionUtils.isEmpty(fileDataList) ? Long.valueOf(fileDataList.size()) : 0;
    }

    /**
     * @param word
     * @param deltaTimeInHours
     * @return Boolean
     */
    @Override
    public Boolean validateStringRank(String word, Long deltaTimeInHours) {
        Long stats = getStatsAggregationDataAndTimeRank(word,deltaTimeInHours);

        return stats == null || stats<=5;
    }
}
