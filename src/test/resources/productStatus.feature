Feature: To ensure all data feeds from SAP ECC VRZ and changes for ProductStatus returns correct values for the corresponding attributes from respective Endpoint

  Scenario Outline: Verify the updated delta product-status changes from sap vrz > step > Gapi endpoint
    Given the user calls to get /product
    When user performs a delta change in productStatus for an article "<EAN>"
    Then the user receives status code of 200
    And the user should see the changes for LO Status flag reflected on the endpoint
      | {"ean": "5052931412617","productStatus": "Available","contactCentreOrderingOnly": false,"availability": "OutOfStock"} |
      | {"ean": "5397007145823","productStatus": "Available","contactCentreOrderingOnly": false,"availability": "Available"}  |
      | {"ean": "5012061913217","productStatus": "Available","contactCentreOrderingOnly": false,"availability": "OutOfStock"} |
      | {"ean": "5397007199666","productStatus": "Available","contactCentreOrderingOnly": false,"availability": "Available"}  |
      | {"ean": "5012061014846","productStatus": "Withdrawn","contactCentreOrderingOnly": true,"availability": "Available"}   |

    Examples: 
      | EAN           |
      | 5052931412617 |
      | 5397007145823 |
      | 5012061913217 |
      | 5397007199666 |
      | 5012061014846 |
