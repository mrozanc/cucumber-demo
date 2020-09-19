package fr.rozanc.sandbox.cucumber.demo;

import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorStepDefs implements En {

    public CalculatorStepDefs(final World world) {
        Given("a calculator I just turned on", () -> {
            world.calculator = new SimpleCalculator();
        });

        When("I input {string} on the calculator", (String input) -> {
            world.calculator.input(input);
        });

        Then("the calculator displays the value: {double}", (Double expectedValue) -> {
            assertEquals(expectedValue, world.calculator.getCurrentValue());
        });
    }
}
