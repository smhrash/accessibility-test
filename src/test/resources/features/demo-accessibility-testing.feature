@accessibilityReport
Feature: Demo Testing

  Background: As a tester I should see the accessibility report

  Scenario Outline: Accessibility report

    Given I navigate to my health toolkit page
    When I enter my username: "<Username>" and password: "<Password>"
    And I click on login button
    Then I see accessibility report
    Examples:
      | Username | Password |
      | sales    | abc123   |


