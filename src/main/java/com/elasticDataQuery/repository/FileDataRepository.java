package com.elasticDataQuery.repository;

import com.elasticDataQuery.entity.FileData;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDataRepository extends ElasticsearchCrudRepository<FileData, String>, com.elasticDataQuery.repository.FileDataRepositoryCustom {

    /**
     *
     * @param word
     * @return Collection of FileData Entities
     */
    @Query("{\"bool\": {\"must\": [{\"match\": {\"content\": \"?0\"}}]}}")
    public List<FileData> finfByContent(String word);

    /**
     *
     * @param auditTime
     * @return Collection of FileData Entities
     */
    @Query("{\"bool\": {\"must\": [{\"match\": {\"timestampInEpoch\": \"?0\"}}]}}")
    public List<FileData> findByAuditTime(Long auditTime);


}
