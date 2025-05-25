Feature: Object API Operations

  Scenario: Register new employee
    Given I have employee details with random email
    When I send a registration request
    Then the employee should be successfully registered and able to login

  Scenario: Add, update, and delete an object
    Given I am authenticated
    When I add a new object with specified attributes
    Then the object should be added successfully
    When I update the object with new attributes
    Then the object should be updated successfully
    When I delete the object
    Then the object should be deleted successfully
    And retrieving it should return an empty response
