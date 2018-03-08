Feature: To ensure all data feeds from SAP ECC VRZ and changes for ProductStatus returns correct values for the corresponding attributes from respective Endpoint

  Scenario Outline: Verify the updated delta product-status changes from sap vrz > step > Gapi endpoint
    Given the user calls to get /product
    When user performs a delta change in productStatus for an article "<EAN>"
    Then the user should be able to validate the technicalSpec atrributes values
      | [{"name":"Brand","value":"Jeyes Fluid"},{"name":"Litre capacity","value":"1 L"},{"name":"Pack quantity","value":"1"},{"name":"Physical state","value":"Liquid"},{"name":"Fragrance","value":"Unscented"}] |
      | [{"name":"Brand","value":"Jeyes Fluid"},{"name":"Litre capacity","value":"1 L"},{"name":"Pack quantity","value":"1"},{"name":"Physical state","value":"Liquid"},{"name":"Fragrance","value":"Unscented"}] |

    Examples: 
      | EAN           |
      | 5052931412617 |
      |      50185283 |
