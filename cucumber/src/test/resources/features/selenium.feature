@selenium
Feature: Selenium

  Scenario: Connecting a browser to the client
    When we direct the browser to "/#/"
    Then we will see a component with id "app"
