Feature: Search by keyword

  @red @search
  Scenario: Searching for 'red'
    Given Operator is researching things on the internet
    When Operator looks up "red"
    Then Operator should see information about "red"
