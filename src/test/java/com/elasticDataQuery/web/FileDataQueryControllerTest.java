package com.elasticDataQuery.web;

import com.elasticDataQuery.entity.FileData;
import com.elasticDataQuery.repository.FileDataRepository;
import com.elasticDataQuery.service.FileDataQueryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FileDataQueryControllerTest {

    MockMvc mockMvc;

    @Mock
    FileDataQueryServiceImpl fileDataQueryService;

    @Mock
    FileDataRepository fileDataRepository;

    @Mock
    ElasticsearchTemplate elasticsearchTemplate;

    @InjectMocks
    FileDataQueryController fileDataQueryController;


    @Before
    public void setup() throws Exception {

        ReflectionTestUtils.setField(fileDataQueryController, "queryLimitInHours", Long.valueOf(24));
        ReflectionTestUtils.setField(fileDataQueryService,"fileDataRepository",fileDataRepository);
        MockitoAnnotations.initMocks(FileDataQueryControllerTest.class);

        // Standalone context
        this.mockMvc = standaloneSetup(this.fileDataQueryController).build();
    }

    @Test
    public void test_get_FileData_From_Elastic_Successfully() throws Exception {

        String word = "testWord";

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

        given(fileDataQueryService.getAllFileDataEntities()).willReturn(fileDataList_Expected);

        mockMvc.perform(get("/all")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("[{\"id\":\"224sdf5\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf4\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf3\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf2\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf1\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"}]"));
    }

    @Test
    public void test_get_FileData_Filtered_By_Content_From_Elastic_Successfully() throws Exception {

        String word = "testWord";

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

        given(fileDataQueryService.findByContent(word)).willReturn(fileDataList_Expected);

        mockMvc.perform(get("/word")
                .param("word",word)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("[{\"id\":\"224sdf5\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf4\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf3\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf2\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf1\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"}]"));
    }

    @Test
    public void test_get_FileData_Filtered_By_Content_And_Rank_From_Elastic_Successfully() throws Exception {

        String word = "testWord";

        Long deltaTimeInHours = Long.valueOf(24);

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

        given(fileDataQueryService.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        mockMvc.perform(get("/wordAndRank")
                .param("word",word)
                .param("timeInHours",deltaTimeInHours.toString())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("[{\"id\":\"224sdf5\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf4\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf3\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf2\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"},{\"id\":\"224sdf1\",\"timestampInEpoch\":234234234,\"auditTimeInEpochMillis\":34324234,\"content\":\"testWord\"}]"));
    }

    @Test
    public void test_get_FileData_Stats_From_Elastic_Successfully() throws Exception {

        String word = "testWord";

        Long deltaTimeInHours = Long.valueOf(24);

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

        given(fileDataQueryService.getStatsAggregationDataAndTimeRank(word,deltaTimeInHours)).willReturn(Long.valueOf(fileDataList_Expected.size()));

        mockMvc.perform(get("/stats")
                .param("string",word)
                .param("timeInHours",deltaTimeInHours.toString())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string("5"));
    }

    @Test
    public void test_validate_StringRank_From_Elastic_Successfully() throws Exception {

        String word = "testWord";

        Long deltaTimeInHours = Long.valueOf(24);

        given(fileDataQueryService.validateStringRank(word,deltaTimeInHours)).willReturn(Boolean.TRUE);

        mockMvc.perform(
                get("/isStringValid").param("string", word)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"response\":\"true\"}"));
    }

    @Test
    public void test_validate_StringRank_To_False_From_Elastic_Successfully() throws Exception {

        String word = "testWord";

        Long deltaTimeInHours = Long.valueOf(24);

        given(fileDataQueryService.validateStringRank(word,deltaTimeInHours)).willReturn(Boolean.FALSE);

        mockMvc.perform(
                get("/isStringValid").param("string", word)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"response\":\"false\"}"));
    }

    @Test
    public void test_validate_StringRank_To_False_Via_ElasticCall_Mock() throws Exception {

        String word = "testWord";

        Long deltaTimeInHours = Long.valueOf(24);

        given(fileDataQueryService.validateStringRank(word,deltaTimeInHours)).willCallRealMethod();

        given(fileDataQueryService.getStatsAggregationDataAndTimeRank(word,deltaTimeInHours)).willCallRealMethod();

        given(fileDataQueryService.findByContentAndTimeLimit(word, deltaTimeInHours)).willCallRealMethod();



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

        FileData fileData_6 = getFileDataFromParams("224sdf1",Long.valueOf(234234234),word,timeStampInEpochMillis_6);

        List<FileData> fileDataList_Expected =
                Arrays.asList(fileData_1,fileData_2,fileData_3,fileData_4,fileData_5,fileData_6);

        given(fileDataRepository.findByContentAndTimeLimit(word,deltaTimeInHours)).willReturn(fileDataList_Expected);

        mockMvc.perform(
                get("/isStringValid").param("string", word)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"response\":\"false\"}"));
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
