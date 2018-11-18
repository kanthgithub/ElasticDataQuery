# ElasticDataQuery

API to query the Log-File Data from Elastic-Search-Engine


API end-point & URL:

http://localhost:<port_Configured_In_application.yaml>/isStringValid/string=

API Response:

If the string appears more than 5 times in the last 24 hours:

{"response" : false}

Else:

{"response" : true}


API Constraints:

Query string length <=40


Use Case:

Load Log File content to Elastic-Search Engine

Purpose:

Full-Text query to be performed to analyse text Data and generate analytics based on text match and timestamps

# Tech-Stack:

- JDK-8 for core programming

- Spring-Boot for Service/Repository management and interaction with Elastic-Engine

- Spring-elastic-data manages all dependencies with Elastic-Search Component

- Mockito and Junit for Unit-Testing

- Embedded-Elastic-Search for Integration Testing

- Swagger to generate documentation for Service-Endpoint and Request/Response Details

# Functional Flow:

- Provides an API endpoint: http://localhost:<port>/isStringValid?string={string}
- The caller can only send a string of up to 40 characters
- The API shall return a json in this format: ​{“response”: “true|false”}
    - If the string appears more than 5 times in the last 24 hours, return​ {“response”: “false”}
    - Else return ​{“response”: “true”}


Technical Flow:



UML:






Test Details:

Unit Testing:

Repository Tests:


Utility Tests:


Data Processing Tests:




File Watcher tests:




# Integration Testing:

** Pending

 - Reason: Embedded Elastic-Engine has issues in compatibility with Spring Dependencies

Followup: Develop a maven plugin which starts/stops the Elastic-Engine for each integration test







