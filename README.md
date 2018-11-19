# ElasticDataQuery

API to query the Log-File Data from Elastic-Search-Engine

# API Specifications:

  ```
   You can access swagger page here: http://localhost:8081/swagger-ui.html
  ```

# API end-point & URL:

    http://localhost:<port_Configured_In_application.yaml>/isStringValid/string=

   ## API Response:

     If the string appears more than 5 times in the last 24 hours:

      {"response" : false}

     Else:

       {"response" : true}


    ##API Constraints:

     Query string length <=40


# Use Case:

```
   Load Log File content to Elastic-Search Engine
```

# Purpose:
```
    Full-Text query to be performed to analyse text Data and generate analytics based on text match and timestamps
```

# Tech-Stack:

- JDK-8 for core programming

- Spring-Boot for Service/Repository management and interaction with Elastic-Engine

- Spring-elastic-data manages all dependencies with Elastic-Search Component

- Mockito and Junit for Unit-Testing

- Embedded-Elastic-Search for Integration Testing

- Swagger to generate documentation for Service-Endpoint and Request/Response Details

# Functional Flow:

- Provides an API endpoint:
    ```
    http://localhost:<port>/isStringValid?string={string}
    ```
- The caller can only send a string of up to 40 characters
- The API shall return a json in this format: ​{“response”: “true|false”}
    - If the string appears more than 5 times in the last 24 hours, return​ {“response”: “false”}
    - Else return ​{“response”: “true”}


Technical Flow:

 - REST endpoint defined in :

   ```
   FileDataQueryController (com/elasticDataQuery/web)
   ```

 - Query Service to query Elastic-Search engine and get aggregated, ranked and validated data:

   ```
   FileDataQueryServiceImpl (com/elasticDataQuery/service)
   ```

   ```java

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

   ```

- Elastic-Search DSL in repository:

  ```
  FileDataRepositoryImpl (com/elasticDataQuery/repository)
  ```

  1. QueryBuilder builds the query to search based on:

      - exact match of string
      - timeStampInEpoch (milliseconds) which is a pasttime (as we need to do a search for 24 Hours)
      - Beauty in elastic-search is that when this is running on cluster, query will become a distributed query
      - No programmatic handling of designing or running distributed query

  ```java

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

  ```


## UML:





## Test Details:

### Unit Testing:

1. Repository Tests: src/test/java/com/elasticDataQuery/repository/
2. Utility Tests:  src/test/java/com/elasticDataQuery/common/
3. Data Query Tests: src/test/java/com/elasticDataQuery/service/
4. REST Controller tests: src/test/java/com/elasticDataQuery/web/

### Integration Testing:

** Pending

- Reason: Embedded Elastic-Engine has issues in compatibility with Spring Dependencies

- Followup: Develop a maven plugin which starts/stops the Elastic-Engine for each integration test

- How To: Integration testing using java test-container as mentioned in:

   - https://gitlab.com/kanthgitlab/elasticsearch-integration-testing

   - Source: https://www.testcontainers.org/usage/elasticsearch_container.html







