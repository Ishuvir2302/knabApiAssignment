#Author: Ishuvir singh
Feature: Trello Board API Automation

  @order1
  Scenario: Retrieve board details using a valid request
  When GET request is made to the Trello API
  Then verify response status should be 200
  And verify status line code should be "HTTP/1.1 200 OK"
  And verify response should have "Content-Type" header as "application/json; charset=utf-8"
  And verify response body should show correct ID
  And verify response time is less than 900 ms
  And verify the response schema
  
  @order2
  Scenario: Create a Trello Board
  Given I want to Board with "Assignment" name
  When I send a POST request to Trello API
  Then verify response status should be 200
  And  verify response body contains the board id
  And verify board is created with "Assignment" name
  
  @order3
  Scenario: Delete the created trello board
    Given I want to Board with "Assignment" name
    When I send a DELETE request to the Trello API
    Then verify response status should be 200
    And Verify the delete response body
