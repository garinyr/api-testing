Feature: Checkout functionality

  Scenario: Complete checkout flow with standard_user
    Given User is on the login page
    When User logs in with username "standard_user" and password "secret_sauce"
    And User adds "Sauce Labs Backpack" to the cart
    And User proceeds to checkout with first name "Garin", last name "Test", postal code "12345"
    Then Checkout should be success

  Scenario: Complete checkout flow with problem_user
    Given User is on the login page
    When User logs in with username "problem_user" and password "secret_sauce"
    And User adds "Sauce Labs Backpack" to the cart
    And User proceeds to checkout with first name "Garin", last name "Test", postal code "12345"
    Then Checkout should be failed
