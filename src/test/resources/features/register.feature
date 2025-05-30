Feature: Employee Registration

  Scenario Outline: Register employee with dynamic email
    Given I have employee details with name "<name>" and email "<email>"
    When I send a registration request
    Then the employee should be successfully registered

    Examples:
      | name       | email                   |
      | Garin YR   | garinyr_test1@gmail.com |
      | Garin Test | garinyr_test2@gmail.com |
