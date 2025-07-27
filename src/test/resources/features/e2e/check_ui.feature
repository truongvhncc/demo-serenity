Feature: Check elements in UI

  @check-ui @e2e
  Scenario: Check elements in UI
    Given Operator is researching things on the internet
    When Operator clicks on default search button
    Then Operator wait for GotIt Button is displayed
    And Modal is displayed with title as "Make DuckDuckGo your default search engine"
    When Operator clicks to close modal
    When Operator looks up "${search-variable}"
    Then Operator should see information about "red"