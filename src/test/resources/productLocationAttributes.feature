#Author: your.email@your.domain.com
Feature: Feature: To ensure all data feeds and changes for ProductLocation returns correct values for the corresponding attributes from respective Endpoint

  Scenario Outline: Verify the changes for the given productLocation attributes
    Given the user calls to get /productlocation
    When user performs a change in productLocation Endpoint for an "<EAN>"
    Then the user should see the changes reflected in the next load frequency update
      | {"aisle":"47","bay":"6","availableLocationType":"SellingStock","binType":"CB"} |

    Examples: 
      | EAN           |
      | 5023790032186 |
      | 5023790032186 |
      | 5023790032186 |
      | 5023790032186 |
