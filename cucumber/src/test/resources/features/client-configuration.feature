@client-configuration
Feature: Client Configuration

  Scenario: Requesting the client configuration
    When we perform a GET request on "/clientConfiguration"
    Then we get a 200 response