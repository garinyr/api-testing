Feature: Employee Authentication

  Scenario Outline: Login with valid credentials
    Given I am a registered employee with email "<email>" and password "<password>"
    When I login
    Then I should receive a valid auth token

    Examples:
      | email                   | password |
      | garinyr_test1@gmail.com | @dmin123 |
      | garinyr_test2@gmail.com | @dmin123 |

  Scenario Outline: Login with invalid credentials
    Given I use email "<email>" and password "<password>"
    When I login
    Then the login response should contain message "This email= <email> has not been registered yet."

    Examples:
      | email                   | password  |
      | wrong@gmail.com         | wrongpass |
      | garinyr_test1@gmail.com | wrongpass |
