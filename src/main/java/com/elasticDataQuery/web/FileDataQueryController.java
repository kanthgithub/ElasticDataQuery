package com.elasticDataQuery.web;

import com.elasticDataQuery.entity.FileData;
import com.elasticDataQuery.model.ResponseModel;
import com.elasticDataQuery.service.FileDataQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/fileData")
@Validated
public class FileDataQueryController {


    @Autowired
    private FileDataQueryService fileDataQueryService;

    @Value("${search.queryLimitInHours}")
    private Long queryLimitInHours;

    /**
     *  Extracts all  FileData entities recorded in elasticSearch
     *
     *  HTTPCode: 200 (Success) / 500 (Internal Server Error)
     *
     * @return all FileData entities recorded in elasticSearch
     */
    @RequestMapping(path="/all", method = RequestMethod.GET)
    public ResponseEntity<List<FileData>> getAllFileDataEntities()  {

        List<FileData> fileDataList = fileDataQueryService.getAllFileDataEntities();

        return fileDataList!=null ?  ResponseEntity.ok(fileDataList) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     *  Extracts all  FileData entities with matching content (input argument)
     *
     *  HTTPCode: 200 (Success) / 500 (Internal Server Error)
     *
     * @return all FileData entities recorded in elasticSearch - with matching content (input argument)
     */
    @RequestMapping(path="/{word}", method = RequestMethod.GET)
    public ResponseEntity<List<FileData>> getAllFileDataEntitiesFilteredByWord(@PathVariable String word)  {

        List<FileData> fileDataList = fileDataQueryService.findByContent(word);

        return fileDataList!=null ?  ResponseEntity.ok(fileDataList) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     *  Extracts all  FileData entities with matching content (input argument)
     *
     *  HTTPCode: 200 (Success) / 500 (Internal Server Error)
     *
     * @return all FileData entities recorded in elasticSearch - with matching content (input argument)
     */
    @RequestMapping(path="/{word}/{timeInHours}", method = RequestMethod.GET)
    public ResponseEntity<List<FileData>> getAllRankedFileDataEntitiesFilteredByWord(@PathVariable String word,@PathVariable String timeInHours)  {

        List<FileData> fileDataList = fileDataQueryService.findByContentAndTimeLimit(word,Long.valueOf(timeInHours));

        return fileDataList!=null ?  ResponseEntity.ok(fileDataList) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @param word
     * @param timeInHours
     * @return aggregated stats for matching words ranked by timeDelta
     */
    @RequestMapping(path="/stats/{word}/{timeInHours}", method = RequestMethod.GET)
    public ResponseEntity<Long> getStatsByWordAndTimeRank(@PathVariable String word,@PathVariable String timeInHours)  {

        Long stats = fileDataQueryService.getStatsAggregationDataAndTimeRank(word,Long.valueOf(timeInHours));

        return stats!=null ?  ResponseEntity.ok(stats) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @param word
     * @return aggregated stats for matching words ranked by timeDelta
     */
    @RequestMapping(path="/isStringValid", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> validateStringRank(@RequestParam("string") @Size(min= 1, max = 40 , message = "string length must be between 1 and 40") String word)  {

        Boolean isAValidString = fileDataQueryService.validateStringRank(word,queryLimitInHours);

        ResponseModel responseModel = new ResponseModel();
        responseModel.setResponse(isAValidString.toString());

        return isAValidString!=null ?  ResponseEntity.ok(responseModel) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
