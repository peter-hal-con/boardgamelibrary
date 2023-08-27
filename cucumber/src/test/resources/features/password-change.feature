@password-change
Feature: User self service password change

  Background:
    Given the following users exist:
      | username         | password  |
      | test@example.com | password3 |
    And we are logged in as "test@example.com"


  Scenario: User dropdown includes password change item
    Given we have directed the browser to "/#/"
    When we click on the component with id "user_dropdown"
    Then we will see a component with id "password_change"


  Scenario: Opening the password change form
    Given we have directed the browser to "/#/"
    When we click on the component with id "user_dropdown"
    And we click on the component with id "password_change"
    Then we will see a component with id "new_password"
    And we will see a component with id "confirm_new_password"
    And we will see a component with id "submit_change_password"


  Scenario: When confirm_new_password is blank, clicking the submit button will be ineffective
    Given we have directed the browser to "/#/change-password"
    When we enter the value "updatedPassword" into the component with id "new_password"
    And we click on the component with id "submit_change_password"
    Then the component with id "confirm_new_password" will be in an error state
    And the component with id "submit_change_password" will be disabled


  Scenario: When new_password and confirm_new_password are different an alert will appear and the submit button will be disabled
    Given we have directed the browser to "/#/change-password"
    When we enter the value "updatedPassword" into the component with id "new_password"
    And we enter the value "differentPassword" into the component with id "confirm_new_password"
    Then the component with id "confirm_new_password" will be in an error state
    And the component with id "submit_change_password" will be disabled


  Scenario: When new_password and confirm_new_password are too short an alert will appear and the submit button will be disabled
    Given we have directed the browser to "/#/change-password"
    When we enter the value "shrt" into the component with id "new_password"
    And we enter the value "shrt" into the component with id "confirm_new_password"
    Then the component with id "new_password" will be in an error state
    And the component with id "submit_change_password" will be disabled


  Scenario: When new_password is sufficiently long, the password_too_short alert will disappear
    Given we have directed the browser to "/#/change-password"
    When we enter the value "long enough" into the component with id "new_password"
    Then the component with id "new_password" will not be in an error state
    And the component with id "submit_change_password" will be enabled
