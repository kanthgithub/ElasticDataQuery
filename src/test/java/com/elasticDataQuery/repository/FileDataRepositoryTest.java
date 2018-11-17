package com.elasticDataQuery.repository;


import com.elasticDataQuery.entity.FileData;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;

@RunWith(MockitoJUnitRunner.class)
public class FileDataRepositoryTest {

    Logger log = LoggerFactory.getLogger(FileDataRepositoryTest.class);

    @Mock
    ElasticsearchTemplate elasticsearchTemplate;

    @InjectMocks
    FileDataRepositoryImpl fileDataRepository;


    @Before
    public void setup() throws Exception{

        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void test_Assert_Get_All_FileData_Entities(){

        //given
        String searchString = "testWord";


        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),"testWord",Long.valueOf(322342342)));


        given(elasticsearchTemplate.queryForList(isA(SearchQuery.class), any(Class.class))).willReturn(fileDataList_Expected);

        //when

        List<FileData> fileDataList_Actual =fileDataRepository.findByContent(searchString);


        //then
        assertNotNull(fileDataList_Actual);
        assertEquals(fileDataList_Expected,fileDataList_Actual);
    }


    @Test
    public void test_Assert_QueryBuilder_For_Content_And_Time(){

        //given
        String search_String = "testString";
        Long time = Long.valueOf(324234324);

        //when
        QueryBuilder queryBuilder_Acutal =
                fileDataRepository.getQueryBuilderForContentAndTimeRank(search_String,time);


        //then
        assertNotNull(queryBuilder_Acutal);
        assertNotNull(queryBuilder_Acutal.getName());

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder_Acutal)
                .build();

        assertNotNull(build);
        assertNotNull(build.getFields());
    }


    @Test
    public void test_Assert_findBy_Content_And_TimeLimit(){

        //given
        String search_String = "testString";
        Long timeInHours = Long.valueOf(12);
        Long timeStampInEpochMillis = Long.valueOf(34324234);


        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),search_String,timeStampInEpochMillis));


        given(elasticsearchTemplate.queryForList(isA(SearchQuery.class), any(Class.class))).willReturn(fileDataList_Expected);

        //when
        List<FileData> fileDataList_Actual =
                fileDataRepository.findByContentAndTimeLimit(search_String,timeInHours);



        //then
        assertNotNull(fileDataList_Actual);
        assertEquals(fileDataList_Expected,fileDataList_Actual);
    }

    @Test
    public void test_Assert_findBy_Content(){

        //given
        String search_String = "testString";
        Long timeStampInEpochMillis = Long.valueOf(34324234);


        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),search_String,timeStampInEpochMillis));


        given(elasticsearchTemplate.queryForList(isA(SearchQuery.class), any(Class.class))).willReturn(fileDataList_Expected);

        //when
        List<FileData> fileDataList_Actual =
                fileDataRepository.findByContent(search_String);

        //then
        assertNotNull(fileDataList_Actual);
        assertEquals(fileDataList_Expected,fileDataList_Actual);
    }




    public FileData getFileDataFromParams(String id,Long timeStampInEpoch,String content,Long auditTimeStamp){

        FileData fileData = new FileData();

        fileData.setId(id);
        fileData.setContent(content);
        fileData.setTimestampInEpoch(timeStampInEpoch);
        fileData.setAuditTimeInEpochMillis(auditTimeStamp);

        return fileData;
    }





}
