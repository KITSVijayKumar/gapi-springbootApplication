@KAPS-143
Feature: Products should have technical-specifications associated with it which returns on the api.

  Scenario Outline: Verify the RS-Xml Export and API response of a Product, should have <=40 Technical Specs and should be associated with a number(a numeric value 0-40)
    Given the user calls to get /product-rs
    When user calls for TechSpecs of an "<EAN>"
    Then the user receives status code of 200
    #Then the user receives status code of 200 for LatestResponseProduct
    And the user should see the techinicalSpecifications are <=40 sequenced in ascending order
    And the user verifies the same with rs-xml

    Examples: 
      | EAN           |
      | 5055160032908 |

  #| 3663602063650 |
  #| 3121970136999 |
  #| 9003414270404 |
  Scenario Outline: Verify technicalSpecifications in the api contains |name| and |value| attributes - Where X is name and Y is value.
    Given the user calls to get /product-rs
    When user calls for TechSpecs of an "<EAN>"
    Then the user receives status code of 200
    #Then the user receives status code of 200 for LatestResponseProduct
    And the user should see name & value attributes for technicalSpecifications on the endpoint.

    Examples: 
      | EAN           |
      | 5055160032908 |

  #| 3663602063650 |
  Scenario Outline: Verify if there are no values associated with any |Tech Spec XX| - it will be an empty list in the api. Verify if there are no "Tech Spec XX" attributes associated with the product in the xml, accept the product and technicalSpecifications will be an empty list in the api
    Given the user gets Tech Spec XX attribute values from rs-xml
    When the user finds no-values for Tech_Spec
    Then the user should see empty techinicalSpecifications for the "<EAN>" on the endpoint.

    Examples: 
      | EAN           |
      | 5055160032908 |

  #| 3663602063650 |
  Scenario Outline: Verify Tech Spec XX RS-Xml attribute value is mapped to |Tech_Spec_xx| attributeID directly in the GApi db/Internal URI and should return the same “Tech_Spec_xx” value for the "technicalSpecifications" attribute on the corresponding GAPI endpoint.
    Given the user calls to get /product-rs
    When user calls for TechSpecs of an "<EAN>"
    Then the user receives status code of 200
    #Then the user receives status code of 200 for LatestResponseProduct
    When user receives the technicalSpecifications array list
    Then the user validates the same with Tech_Spec_XX in RS-Xml.

    Examples: 
      | EAN           |
      | 5055160032908 |
      #| 3663602063650 |
