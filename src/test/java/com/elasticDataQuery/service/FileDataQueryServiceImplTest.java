package com.elasticDataQuery.service;

import com.elasticDataQuery.entity.FileData;
import com.elasticDataQuery.repository.FileDataRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FileDataQueryServiceImplTest {

    Logger log = LoggerFactory.getLogger(FileDataQueryServiceImplTest.class);

    @Mock
    ElasticsearchTemplate elasticsearchTemplate;

    @Mock
    FileDataRepository fileDataRepository;

    @InjectMocks
    FileDataQueryServiceImpl fileDataQueryService;


    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_assert_getAllFileDataEntities(){

        //given

        String search_String = "testString";
        Long timeStampInEpochMillis = Long.valueOf(34324234);

        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),search_String,timeStampInEpochMillis));

        given(fileDataRepository.findAll()).willReturn(fileDataList_Expected);

        //when
        List<FileData> fileDataList_Actual =
                fileDataQueryService.getAllFileDataEntities();


        //then
        assertNotNull(fileDataList_Actual);
        assertEquals(fileDataList_Expected,fileDataList_Actual);
    }

    @Test
    public void test_assert_Find_By_Content(){

        //given
        String search_String = "testString";
        Long timeStampInEpochMillis = Long.valueOf(34324234);


        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),search_String,timeStampInEpochMillis));


        given(fileDataRepository.findByContent(search_String)).willReturn(fileDataList_Expected);

        //when
        List<FileData> fileDataList_Actual =
                fileDataQueryService.findByContent(search_String);

        //then
        assertNotNull(fileDataList_Actual);
        assertEquals(fileDataList_Expected,fileDataList_Actual);
    }


    @Test
    public void test_Assert_findBy_Content_And_TimeLimit(){

        //given
        String search_String = "testString";
        Long timeInHours = Long.valueOf(12);
        Long timeStampInEpochMillis = Long.valueOf(34324234);

        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),search_String,timeStampInEpochMillis));

        given(fileDataRepository.findByContentAndTimeLimit(search_String,timeInHours)).willReturn(fileDataList_Expected);

        //when
        List<FileData> fileDataList_Actual =
                fileDataQueryService.findByContentAndTimeLimit(search_String,timeInHours);

        //then
        assertNotNull(fileDataList_Actual);
        assertEquals(fileDataList_Expected,fileDataList_Actual);
    }


    @Test
    public void test_assert_get_StatsAggregationData_And_TimeRank() {

        //given
        String word = "testString";
        Long deltaTimeInHours = Long.valueOf(4);
        Long timeStampInEpochMillis = Long.valueOf(34324234);

        List<FileData> fileDataList_Expected =
                Arrays.asList(getFileDataFromParams("224sdf",Long.valueOf(234234234),word,timeStampInEpochMillis));

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        //when
        Long stats_Actual = fileDataQueryService.getStatsAggregationDataAndTimeRank(word,deltaTimeInHours );

        //then
        assertNotNull(stats_Actual);
        assertEquals(Long.valueOf(1),stats_Actual );
    }

    @Test
    public void test_validate_To_True_For_StringRank_As_MaxCount() {

        //given
        String word = "testString";
        Long deltaTimeInHours = Long.valueOf(4);

        Long timeStampInEpochMillis_1 = Long.valueOf(34324234);

        FileData fileData_1 = getFileDataFromParams("224sdf5",Long.valueOf(234234234),word,timeStampInEpochMillis_1);

        Long timeStampInEpochMillis_2 = Long.valueOf(34324234);

        FileData fileData_2 = getFileDataFromParams("224sdf4",Long.valueOf(234234234),word,timeStampInEpochMillis_2);

        Long timeStampInEpochMillis_3 = Long.valueOf(34324234);

        FileData fileData_3 = getFileDataFromParams("224sdf3",Long.valueOf(234234234),word,timeStampInEpochMillis_3);

        Long timeStampInEpochMillis_4 = Long.valueOf(34324234);

        FileData fileData_4 = getFileDataFromParams("224sdf2",Long.valueOf(234234234),word,timeStampInEpochMillis_4);

        Long timeStampInEpochMillis_5 = Long.valueOf(34324234);

        FileData fileData_5 = getFileDataFromParams("224sdf1",Long.valueOf(234234234),word,timeStampInEpochMillis_5);

        List<FileData> fileDataList_Expected =
                Arrays.asList(fileData_1,fileData_2,fileData_3,fileData_4,fileData_5);

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        //when
        Boolean validIndicator_Actual = fileDataQueryService.validateStringRank(word,deltaTimeInHours );

        //then
        assertNotNull(validIndicator_Actual);
        assertTrue(validIndicator_Actual );
    }


    @Test
    public void test_validate_To_True_For_StringRank_As_MinCount() {

        //given
        String word = "testString";
        Long deltaTimeInHours = Long.valueOf(4);

        Long timeStampInEpochMillis_1 = Long.valueOf(34324234);

        FileData fileData_1 = getFileDataFromParams("224sdf5",Long.valueOf(234234234),word,timeStampInEpochMillis_1);

        Long timeStampInEpochMillis_2 = Long.valueOf(34324234);

        FileData fileData_2 = getFileDataFromParams("224sdf4",Long.valueOf(234234234),word,timeStampInEpochMillis_2);

        List<FileData> fileDataList_Expected =
                Arrays.asList(fileData_1,fileData_2);

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        //when
        Boolean validIndicator_Actual = fileDataQueryService.validateStringRank(word,deltaTimeInHours );

        //then
        assertNotNull(validIndicator_Actual);
        assertTrue(validIndicator_Actual );
    }


    @Test
    public void test_validate_To_True_For_StringRank_As_ZeroCount() {

        //given
        String word = "testString";
        Long deltaTimeInHours = Long.valueOf(4);

        List<FileData> fileDataList_Expected =  Arrays.asList();

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        //when
        Boolean validIndicator_Actual = fileDataQueryService.validateStringRank(word,deltaTimeInHours );

        //then
        assertNotNull(validIndicator_Actual);
        assertTrue(validIndicator_Actual );
    }

    @Test
    public void test_validate_To_True_For_StringRank_As_ZeroCount_WIth_NullReturn() {

        //given
        String word = "testString";
        Long deltaTimeInHours = Long.valueOf(4);

        List<FileData> fileDataList_Expected =  null;

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        //when
        Boolean validIndicator_Actual = fileDataQueryService.validateStringRank(word,deltaTimeInHours );

        //then
        assertNotNull(validIndicator_Actual);
        assertTrue(validIndicator_Actual );
    }


    @Test
    public void test_validate_To_False_For_StringRank() {

        //given
        String word = "testString";
        Long deltaTimeInHours = Long.valueOf(4);


        Long timeStampInEpochMillis_1 = Long.valueOf(34324234);

        FileData fileData_1 = getFileDataFromParams("224sdf5",Long.valueOf(234234234),word,timeStampInEpochMillis_1);

        Long timeStampInEpochMillis_2 = Long.valueOf(34324234);

        FileData fileData_2 = getFileDataFromParams("224sdf4",Long.valueOf(234234234),word,timeStampInEpochMillis_2);

        Long timeStampInEpochMillis_3 = Long.valueOf(34324234);

        FileData fileData_3 = getFileDataFromParams("224sdf3",Long.valueOf(234234234),word,timeStampInEpochMillis_3);

        Long timeStampInEpochMillis_4 = Long.valueOf(34324234);

        FileData fileData_4 = getFileDataFromParams("224sdf2",Long.valueOf(234234234),word,timeStampInEpochMillis_4);

        Long timeStampInEpochMillis_5 = Long.valueOf(34324234);

        FileData fileData_5 = getFileDataFromParams("224sdf1",Long.valueOf(234234234),word,timeStampInEpochMillis_5);

        Long timeStampInEpochMillis_6 = Long.valueOf(34324234);

        FileData fileData_6 = getFileDataFromParams("224sdf12",Long.valueOf(234234234),word,timeStampInEpochMillis_6);

        List<FileData> fileDataList_Expected =
                Arrays.asList(fileData_1,fileData_2,fileData_3,fileData_4,fileData_5,fileData_6);

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        //when
        Boolean validIndicator_Actual = fileDataQueryService.validateStringRank(word,deltaTimeInHours );

        //then
        assertNotNull(validIndicator_Actual);
        assertFalse(validIndicator_Actual );
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
