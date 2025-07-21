Feature: Search by keyword

  @red @search
  Scenario: Searching for 'red'
    Given Sergey is researching things on the internet
    When he looks up "red"
    Then he should see information about "red"
