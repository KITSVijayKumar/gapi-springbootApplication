Feature: To ensure STEPdata & Stepersand data behaves as expected on their respective endpoints.

  Scenario Outline: Verify productsResponse vs products-rsReponse
    When user calls to get /product response for a given "<EAN>"
    Then the user should receive status code of 200
    And the user should be able to see all the expected product attributes
    When user calls to get /product-rs response for a given "<EAN>"
    Then the user should receive status code of 200
    And the user should be able to see all the expected product-rs attributes
    Then compare the endpoints symmetricDifference for TECH_SPECS
    And the endpoints symmetricDifference for HLTH_and_SAFETY
    And the endpoints symmetricDifference for HM_Del_SHPPNG_MethodsBQ
      | {"availability":"Available","shippingMethodId":"SHPM_166479"} |
    And the endpoints symmetricDifference for OTHER_SHPPNG_MethodsBQ
      | availability     | Available   |
      | shippingMethodId | SHPM_191331 |
    Then the user checks child objects of 'data:' are ordered
      | attributes    |
      | relationships |
      | type          |
      | id            |
      | links         |
    And the user checks child objects of 'attributes:' are ordered
      | ean                               |
      | name                              |
      | deliveryChargeIdentifier          |
      | productStatus                     |
      | contactCentreOrderingOnly         |
      | productInformation                |
      | featureCopy                       |
      | features                          |
      | productInfoBullets                |
      | technicalSpecifications           |
      | reviews                           |
      | productHealthAndSafety            |
      | productAverageRating              |
      | recommendedProductShippingMethods |
      | storeLowStockThreshold            |
      | generalItemCategoryCode           |
      | deliveryMethods                   |
      | productMediaObjects               |
      | productDisplayType                |
      | productAgeRestrictions            |
      | relatedContentArticles            |
      | merchandisingTags                 |
      | unitOfMeasure                     |
      | unitOfMeasureQuantity             |
      | primaryCategory                   |
      | shortName                         |
      | parentCategories                  |
      | seoId                             |
      | complianceCodes                   |
    And the user checks child objects of 'relationships:' are ordered
      | crossSell                 |
      | upSell                    |
      | fulfilmentBillOfMaterials |
      | bundleBillOfMaterials     |
      | price                     |
      | multiSku                  |
    Then the user checks the child objects of 'links:' are ordered
      | self |

    Examples: 
      | EAN           |
      | 5397007223156 |
      | 5010212533031 |
      | 5010212562611 |
      #| 5010212572993 |
      | 5010212576021 |
      | 5010212576755 |
      | 5010212577035 |
      | 5397007062724 |
