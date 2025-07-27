Feature: Search by keyword - green

  @green @e2e
  Scenario: Searching for 'green'
    Given Operator is researching things on the internet
    When Operator looks up "green"
    Then Operator should see information about "green"

