@happyHolidayMaker

Feature: Assert the Weather forecast for Sydney on Thursday is greater than 10 Degree Celsius

  Scenario Outline: A happy holiday maker
    Given I like to holiday in <city> of <country>
    And I only like to holiday on <dayOfTheWeek>
    When I look up the weather forecast
    Then I receive the weather forecast
    And the temperature is warmer than <maxTemperature> degrees
    Examples:
      | city   | country | dayOfTheWeek | maxTemperature |
      | Sydney | AU      | 5            | 10             |