package com.elasticDataQuery.repository;

import com.elasticDataQuery.common.DateTimeUtil;
import com.elasticDataQuery.entity.FileData;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDataRepositoryImpl implements  FileDataRepositoryCustom {

    Logger log = LoggerFactory.getLogger(FileDataRepositoryImpl.class);

    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public FileDataRepositoryImpl(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /**
     * match by word Content
     *
     * @param word
     * @return Collection of FileData Entities
     */
    @Override
    public List<FileData> findByContent(String word) {

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(getQueryBuilderForContent(word))
                .build();

        return elasticsearchTemplate.queryForList(build, FileData.class);
    }

    /**
     * match by word Content & the timeStamp >= deltaTimeInEpochMillis
     *
     * @param word
     * @param deltaTimeInHours
     * @return Collection of FileData Entities
     */
    @Override
    public List<FileData> findByContentAndTimeLimit(String word, Long deltaTimeInHours) {

        QueryBuilder qb = getQueryBuilderForContentAndTimeRank(word, deltaTimeInHours);

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(qb)
                .build();

        return elasticsearchTemplate.queryForList(build, FileData.class);
    }


    /**
     *
     * @param word
     * @param deltaTimeInHours
     * @return QueryBuilder
     */
    public QueryBuilder getQueryBuilderForContentAndTimeRank(String word, Long deltaTimeInHours) {

        Long pastTimeInEpochMillis = DateTimeUtil.convertPastTimeInHoursToEpochMillis(deltaTimeInHours.intValue());

        log.info("pastTimeInEpochMillis : {} for Hours: {}",pastTimeInEpochMillis,deltaTimeInHours);

        return QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("content", word))
                .must(QueryBuilders.rangeQuery("timestampInEpoch").gte(pastTimeInEpochMillis));
    }

    /**
     *
     * @param word
     * @return QueryBuilder
     */
    public QueryBuilder getQueryBuilderForContent(String word) {

        return QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("content", word));
    }

}