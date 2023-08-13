# knabApiAssignment

**API Testing RestAssured Framework**
****
This API Testing Framework is built using Java and RestAssured, which is a popular library for testing RESTful APIs.The framework is designed for trello board endpoint validation test. Framework reads the data from the property file during test execution. This allows for easy maintenance and updates to test data without requiring changes to the actual test code. The framework includes an implementation of the Logger, which is used to log details during the execution of tests. This feature helps in debugging and identifying issues during the testing phase. The Logger can be customized based on the user's requirements and can be used to output logs to different sources such as the console, log files, or database. For reporting purposes, the framework uses Extent Reports, which is a powerful reporting library that generates HTML reports. These reports include information about the tests that were run, their status, and other details such as the time taken to execute the tests. The reports can be easily shared with other team members and stakeholders to keep them informed about the testing progress and results. Overall, this RestAssured API Testing Framework provides a comprehensive solution for testing RESTful APIs. Its data-driven approach allows for greater flexibility in testing different scenarios and its use of Extent Reports and Logger ensures that test results are well-documented and easily accessible and customizable.

**Folder Structure**
****

The folder structure of your API Testing Framework consists of the following:

* src/main/java: This is the main source code directory that contains all the Java packages for the framework.

* trelloEndpoints: This package contains the methods to perform CRUD operations for trello endpoints. The response is sent back so that it can be called in test scripts.

* Payload: This package contains POJO (Plain Old Java Object) classes that can be utilized in test scripts to create objects and set values in variables. These object is used to validate schema

* Utils: This package contains utility files like FileUtil, ExcelUtil, ExtentSetup, and ListenerTest.

1.ExtentSetup: This class sets up the Extent report.

2.ListenerTest: This class logs data based on test result such as test pass, test fail, or test skipped.

* Routes: This package contains the endpoints for the API.

* src/main/resource:
1. log4j2.xml file which is configured for two loggers and each logger logs the data in Logs folder with separate file name(trello).

2. trello_schema: This is the json-schema which is used to validate the json response with matching with this schema

* src/test/java - Contains package testEndPoints inside it we have written testcases for each endpoint(trello).Used BDD approach using trelloSteps and runner package
* AdequateData- Testdata for trello and endpoint is mentioned in trello.txt file and can utilised in trelloendpoint
*feature - feature file containing scenario's
Overall, the structure follows a modular approach with separate packages for endpoints, payload, utilities, and routes. This makes it easy to maintain and extend the framework. The use of POJO classes helps to achieve objective, while Extent reporting provides detailed reports for better analysis.

**Language**
***
* Java (11)

**Supporting tools **
***
* Maven (Build tool)
* TestNG 
* ExtentReport
* Logger
* hamcrest matchers

**Installation**
***
If you want to run the code locally you can clone the repo  or open the project in code editor (eclipse or intellij )and run either using testng.xml or  mvn clean test command.

All the Logs are being generated in Logs folder and Extent-Report is generated in Reports folder. 

**Code Repository**
***
* Code can be pushed in any code repository github or gitlub or bitbucket (preferably github as i created maven.yml file under .github/workflow folder to use GITHUB CI)
* GitHub instructions-> 
1. Create repository in github
2. Clone the repo
3. Put the code in clone repo
4. stage the code, commit with message
5. Push the code 
6. Verify the pushed code in github 
7. verify the CI running under actions tab in github


**CI Integration **
***
Maven.yml file is created to run the test in github CI using java 11 and ubuntu (any OS can be used by modifying ex macOS-latest)

** Scenario Automated **
***
#Author: Ishuvir singh
Feature: Trello Board API Automation

  
Scenario: Retrieve board details using a valid request
When GET request is made to the Trello API
Then verify response status should be 200
And verify status line code should be "HTTP/1.1 200 OK"
And verify response should have "Content-Type" header as "application/json; charset=utf-8"
And verify response body should show correct ID
And verify response time is less than 900 ms
And verify the response schema
  
 
Scenario: Create a Trello Board
Given I want to Board with "Assignment" name
When I send a POST request to Trello API
Then verify response status should be 200
And  verify response body contains the board id
And verify board is created with "Assignment" name
  

Scenario: Delete the created trello board
Given I want to Board with "Assignment" name
When I send a DELETE request to the Trello API
Then verify response status should be 200
And Verify the delete response body
