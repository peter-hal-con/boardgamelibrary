@login
Feature: Login

  Scenario: There is a login button
    When we direct the browser to "/#/"
    Then we will see a component with id "open_login"


  Scenario: Clicking the login button opens the login dialog
    Given we have directed the browser to "/#/"
    When we click on the component with id "open_login"
    Then we will see a component with id "username"
    And we will see a component with id "password"
    And we will see a component with id "submit_login"


  Scenario: We can login
    Given the following users exist:
      | username         | password  |
      | test@example.com | password3 |
    And we have directed the browser to "/#/login/"
    When we enter the value "test@example.com" into the component with id "username"
    And we enter the value "password3" into the component with id "password"
    And we click on the component with id "submit_login"
    Then we will be logged in as "test@example.com"
    And the current URL will be "/#/"
    And we will see a component with id "user_dropdown"
    And the button with id "user_dropdown" will be labelled "test@example.com"
    And we will not see a component with id "open_login"


  Scenario: Failed login should display alert
    Given the following users exist:
      | username         | password  |
      | test@example.com | password3 |
    And we have directed the browser to "/#/login/"
    When we enter the value "test@example.com" into the component with id "username"
    And we enter the value "wrong_password" into the component with id "password"
    And we click on the component with id "submit_login"
    Then we will not be logged in
    And we will see a component with id "login_failed"


  Scenario: There is a logout option in the user dropdown
    Given the following users exist:
      | username         | password  |
      | test@example.com | password3 |
    And we are logged in as "test@example.com"
    When we click on the component with id "user_dropdown"
    Then we will see a component with id "logout"


  Scenario: We can logout
    Given the following users exist:
      | username         | password  |
      | test@example.com | password3 |
    And we are logged in as "test@example.com"
    And we have directed the browser to "/#/someplace/"
    When we click on the component with id "user_dropdown"
    And we click on the component with id "logout"
    Then we will not be logged in
    And the current URL will be "/#/"
    And we will not see a component with id "user_dropdown"
    And we will see a component with id "open_login"
