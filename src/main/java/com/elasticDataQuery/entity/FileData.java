package com.elasticDataQuery.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "filedatafrequency", type = "fileData")
public class FileData {

    // Elasticsearch object internal id. Look at field "_id"
    @Id
    @Field(type= FieldType.Text)
    private String id;

    @Field(type= FieldType.Long)
    private Long timestampInEpoch;

    @Field(type= FieldType.Long)
    private Long auditTimeInEpochMillis;

    @Field(type= FieldType.Text)
    private String content;

}
