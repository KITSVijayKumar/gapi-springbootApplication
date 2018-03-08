Feature: To ensure the bazaarvoice basic product review data to be exposed by the API

  Scenario Outline: WHEN a new daily BazaarVoice refresh is available to GAPI, THEN every product present in both the product database and has a matching EAN in the BazaarVoice feed is updated with the review information in the BazaarVoice feed:
    Given user calls to get /product-rs response for a given "<ProductEAN>"
    Then the user should receive status code of 200
    When there is a new bazaarvoice refresh to GAPI
    Then the user should verify the review information of "<ProductEAN>" and validate with bv_diy_ratings xml file
    And the user checks that reviews: content, ratingValue: and ratingCount: in productAverageRating: matches the <<ProductReviewsUrl, <<AverageOverallRating and <<TotalReviewCount in <<ReviewStatistics for every "<ProductEAN>" in the latest Bazaarvoice xml file feed

    Examples: 
      | ProductEAN    |
      | 0026457941628 |
      | 0026457940300 |
      | 0026457940287 |
      | 0026457941611 |
      |      04052524 |
      #| 3153894986640 |
      #| 3165140606585 |
      #| 3165140882446 |
      #|      03425961 |
      #| 3454974794903 |
      #| 3520190929686 |
      #| 5000253004300 |
      #| 5000253033997 |
      #| 5060009330817 |
      #| 7290106936188 |
      #| 7290103658441 |
      #| 7393080388568 |
      #| 7612980609598 |
      #| 7640112390021 |
      #| 8007842838435 |
      #| 8013183032883 |
      #| 8422248029346 |
      #||
      | 8691200443623 |
      |      03819906 |
      | 0663896077916 |
      |         19064 |
      |      03163955 |
      |      03164266 |
      |      03164297 |
      |      03164617 |
      | 3232630200407 |
      #| 5052931525362 |
      #| 5397007058697 |
      #| 5411012339937 |
      |      03856017 |
      #|      03856000 |
      #| 3566550018843 |
      |      03847947 |
      #| 3232630223956 |
      #| 3232630908556 |
      #|      03163955 |
      |      03164457 |
      #||
      | 4897022173623 |
      | 4897022173692 |
      | 5011944250289 |
      | 5011944260936 |
      | 5011944981589 |
      | 5021711045208 |
      | 5021711045918 |
      | 5052193004568 |
      | 5052193021169 |
      | 5055160032908 |
      | 5055160032915 |
      #||
      | 0026457940201 |
      | 0026457940171 |
