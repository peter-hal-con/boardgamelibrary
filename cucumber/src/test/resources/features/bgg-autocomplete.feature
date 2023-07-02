@bgg-autocomplete
Feature: BoardGameGeek Autocomplete

  Scenario: Visiting the boardgamegeek autocomplete component test page
    When we direct the browser to "/#/test/bgg-autocomplete"
    Then we will see a component with id "bgg-autocomplete"
    And we will see a component with id "selected-item-bgg-id"
    And we will see a component with id "selected-item-primary-name"
    And we will see a component with id "selected-item-year-published"


  Scenario: Before entering a title into a boardgamegeek autocomplete component
    When we direct the browser to "/#/test/bgg-autocomplete"
    And we click on the component with id "bgg-autocomplete"
    Then we will see "No data available" in the dropdown list of the autocomplete component with id "bgg-autocomplete"


  Scenario Outline: Entering a recognized title into a boardgamegeek autocomplete component
    When we direct the browser to "/#/test/bgg-autocomplete"
    And we enter the value "<user-input>" into the autocomplete component with id "bgg-autocomplete"
    Then we will see "<display-name>" in the dropdown list of the autocomplete component with id "bgg-autocomplete"

    Examples:
      | user-input              | display-name                   |
      | Crossbows and Catapults | Crossbows and Catapults (1983) |
      | Senet e Tablan          | Senet e Tablan                 |


  Scenario Outline: Selecting a recognized title in a boardgamegeek autocomplete component combobox
    When we direct the browser to "/#/test/bgg-autocomplete"
    And we enter the value "<user-input>" into the autocomplete component with id "bgg-autocomplete"
    And we click on the "<display-name>" option in the autocomplete component with id "bgg-autocomplete"
    Then the component with id "selected-item-bgg-id" will contain "<bgg-id>"
    And the component with id "selected-item-primary-name" will contain "<primary-name>"
    And the component with id "selected-item-year-published" will contain "<year-published>"

    Examples:
      | user-input              | display-name                   | bgg-id | primary-name            | year-published |
      | Crossbows and Catapults | Crossbows and Catapults (1983) | 2129   | Crossbows and Catapults | 1983           |
      | Senet e Tablan          | Senet e Tablan                 | 328628 | Senet e Tablan          |                |


  Scenario Outline: Entering an unrecognized title into a boardgamegeek autocomplete component
    When we direct the browser to "/#/test/bgg-autocomplete"
    And we enter the value "There is no game with this title" into the autocomplete component with id "bgg-autocomplete"
    Then we will see "There is no game with this title" in the dropdown list of the autocomplete component with id "bgg-autocomplete"


  Scenario Outline: Selecting an unrecognized title in a boardgamegeek autocomplete component combobox
    When we direct the browser to "/#/test/bgg-autocomplete"
    And we enter the value "There is no game with this title" into the autocomplete component with id "bgg-autocomplete"
    And we click on the "There is no game with this title" option in the autocomplete component with id "bgg-autocomplete"
    Then the component with id "selected-item-bgg-id" will contain "none"
    And the component with id "selected-item-primary-name" will contain "There is no game with this title"
    And the component with id "selected-item-year-published" will contain ""
