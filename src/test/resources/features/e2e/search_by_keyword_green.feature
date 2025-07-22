Feature: Search by keyword - green

  @green @search
  Scenario: Searching for 'green'
    Given Operator is researching things on the internet
    When Operator looks up "green"
    Then Operator should see information about "green"

