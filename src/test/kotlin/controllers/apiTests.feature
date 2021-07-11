Feature: Tests

  Background:
    Given url 'http://localhost:8080'

  Scenario Outline: Search for available products
    Given path '/findAvailableProducts?type=' + <productType>
    When method get
    Then status 200
    And assert response[0]["id"] == <productId>
    And assert response[0]["name"] == <productName>

    Examples:
      | productType | productId | productName  |
      | "gadget"    | 10        | "XYZ Laptop" |

  Scenario Outline: Create order
    Given path '/orders'
    And request {"productid": <productId>, "count": <count>}
    When method post
    Then status 200
    And assert response["status"] == <status>
    And assert response["id"] == <productId>

    Examples:
      | productId | count | status    |
      | 10        | 1     | "success" |