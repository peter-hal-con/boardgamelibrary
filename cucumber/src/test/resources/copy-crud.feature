@copy-crud
Feature: Creation, retrieval, update, and deletion of items
  Background: We are logged in as an admin
    Given the following users exist:
      | username              | password  | authorities    |
      | admin@example.com     | password1 | ROLE_ADMIN     |
    And we are authenticated as "admin@example.com"
    And we have created a title with name "Some Title"


  @create
  Scenario: We can create a copy
    When we create a copy of "Some Title" belonging to "admin@example.com"
    Then the result of "$.data.copyCreate.title.name" will be 'Some Title'
    And the result of "$.data.copyCreate.owner.username" will be 'admin@example.com'


  @retrieve
  Scenario: We can retrieve a copy
    Given we have created a copy of "Some Title" belonging to "admin@example.com"
    When we perform a GraphQL query "query{copyList{title{name}, owner{username}}}"
    Then the result of "$.data.copyList[0].title.name" will be 'Some Title'
    And the result of "$.data.copyList[0].owner.username" will be 'admin@example.com'
