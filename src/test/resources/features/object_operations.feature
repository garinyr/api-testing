Feature: Object CRUD Operations

  Background:
    Given I am already authenticated

  @createObject
  Scenario Outline: Add a new object with valid attributes
    When I add a new object with:
      | name        | <name>        |
      | year        | <year>        |
      | price       | <price>       |
      | cpu_model   | <cpu_model>   |
      | hdd_size    | <hdd_size>    |
      | capacity    | <capacity>    |
      | screen_size | <screen_size> |
      | color       | <color>       |
    Then the object should be added successfully

    Examples:
      | name           | year | price | cpu_model     | hdd_size | capacity | screen_size | color  |
      | MacBook Pro 16 | 2019 |  1849 | Intel Core i9 |        1 |        2 |          14 | red    |
      | MacBook Air M2 | 2022 |  1399 | M2            |        2 |        1 |          13 | silver |

  @updateObject
  Scenario: Update the previously added object
    When I update the object with new attributes
    Then the object should be updated successfully

  @deleteObject
  Scenario: Delete the previously added object
    When I delete the object
    Then the object should be deleted successfully
    And retrieving it should return an empty response
