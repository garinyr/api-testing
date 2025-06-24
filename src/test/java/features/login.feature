Feature: Sauce Demo Login

  Scenario Outline: Login with valid and invalid credentials
    Given User is on the login page
    When User logs in with username "<username>" and password "<password>"
    Then User should see "<expected>"

    Examples:
      | username        | password     | expected                                                                  |
      | standard_user   | secret_sauce | Products                                                                  |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | standard_user   | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce | Epic sadface: Username is required                                        |
      | standard_user   |              | Epic sadface: Password is required                                        |
