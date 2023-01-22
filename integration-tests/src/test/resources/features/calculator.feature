Feature: Basic Arithmetic

  Background: A calculator
    Given a calculator I just turned on

  Scenario: The last number displayed is the last typed if no evaluation is done
    When I input "7 * 8" on the calculator
    Then the calculator displays the value: 8
    When I input "=" on the calculator
    Then the calculator displays the value: 56

  Scenario: Addition
    When I input "1 + 5 =" on the calculator
    Then the calculator displays the value: 6

  Scenario: Subtraction
    When I input "1 - 5 =" on the calculator
    Then the calculator displays the value: -4

  Scenario Outline: Multiplication
    When I input "<input>" on the calculator
    Then the calculator displays the value: <value>
    Examples:
      | input      | value |
      | 3 * 5 =    | 15    |
      | 6 * 7 =    | 42    |
      | -4 * 1.2 = | -4.8  |

  Scenario Outline: Division
    When I input "<input>" on the calculator
    Then the calculator displays the value: <value>
    Examples:
      | input      | value               |
      | 3 / 2 =    | 1.5                 |
      | 6 / 3 =    | 2                   |
      | -4 / 1.2 = | -3.3333333333333335 |

  Scenario: Operations can be chained
  They are executed in the input order, the operators priority is not taken into account
    When I input "3 + 7 * 5 =" on the calculator
    Then the calculator displays the value: 50
