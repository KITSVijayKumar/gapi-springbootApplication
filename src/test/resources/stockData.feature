Feature: To Enable the provision of Sub-Hourly Store Stock Levels | Verify SAP BODS job to get the delta stock data with details of EAN, StoreCode, OPCO, RangedFlag and CurrentStockQuantity.

  @KAPS-1076 @stockTest @stockEndpointVsCsvFile
  Scenario Outline: user verifies the stockData Output is in-order from CSVfile for STORECODE, EAN, STOCKLEVEL, RANGED, OPCO and validates the same on the Endpoint.
    Given the user receives storeCode of 4 characters, "<ProductEAN>" exactly 13 characters, StockLevel maximum 11 characters Whole-Number, Ranged: 1 character and OPCO in maximum 4 characters in length
    When user calls to get /stock response for a given "<StoreID>" and "<ProductEAN>"
    Then the user should receive status code of 200
    And the user verifies stockData delta changes on Endpoint for "<StoreID>" and "<ProductEAN>" are matching with the latest CSVfile

    Examples: 
      | StoreID | ProductEAN    |
      #|    1138 | 5013601065557 |
      #|    1314 | 5052931073269 |
      #|    1390 | 5052931087754 |
      #|    1271 | 0051131866935 |
      #|    1390 | 0076171101891 |
      #|    1239 | 0051141346991 |
      #|    1152 |      03207406 |
      #|    1390 | 0076171101891 |
      #|    1284 | 5030659059673 |
      |    1353 | 3663602010463 |
      |    1033 |      04205722 |
      |    1275 | 5024160080912 |
      |    1266 | 5021703223058 |
      |    1260 |      00838632 |
      |    1260 | 3663602934899 |

  @KAPS-1076 @stockTest
  Scenario Outline: user verifies the EANs where the data was changed as stock Delta since the last successful job run.
    When user calls to get /stock response for a given "<StoreID>" and "<ProductEAN>"
    Then the user should receive status code of 200
    And the user verifies stockData delta changes and compares with the previous job run on Endpoint

    Examples: 
      | StoreID | ProductEAN    |
      |    1353 | 3663602010463 |
      |    1033 |      04205722 |
      |    1275 | 5024160080912 |
      |    1266 | 5021703223058 |
      |    1260 |      00838632 |
      |    1260 | 3663602934899 |

  @KAPS-1073 @csvTest
  Scenario Outline: WHEN a new deltaRefresh stockData is available to GAPI, THEN every stockData present in BulkStock database/stock-api db and has a matching EAN in the BODS filesystem is updated with the delta stockData.
    When the user verifies stockData of "<ProductEAN>" and validate with BODS CSVfile
    Then user checks the stockLevel in BODS fileSytem for "<StoreID>" and "<ProductEAN>" should match with the latest stockData delta on stock-api db

    Examples: 
      | StoreID | ProductEAN    |
      #|    1138 | 5013601065557 |
      #|    1314 | 5052931073269 |
      #|    1390 | 5052931087754 |
      #|    1271 | 0051131866935 |
      #|    1239 | 0051141346991 |
      #|    1152 | 0000003207406 |
      #|    1390 | 0076171101891 |
      #|    1284 | 5030659059673 |
      |    1336 | 4027655547496 |
      |    1062 | 5052931672134 |
      |    1390 | 5052931076376 |
      |    1051 | 0000000823936 |
