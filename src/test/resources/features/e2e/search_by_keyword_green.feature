Feature: Search by keyword - green

  @green @search
  Scenario: Searching for 'green'
    Given Sergey is researching things on the internet
    When he looks up "green"
    Then he should see information about "green"

