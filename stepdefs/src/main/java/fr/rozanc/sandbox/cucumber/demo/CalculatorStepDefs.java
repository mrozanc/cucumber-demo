package fr.rozanc.sandbox.cucumber.demo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;

public class CalculatorStepDefs {

    private final CalculatorContext calculatorContext;

    public CalculatorStepDefs(final CalculatorContext calculatorContext) {
        this.calculatorContext = calculatorContext;
    }

    @Given("a calculator I just turned on")
    public void givenACalculatorIJustTurnedOn() {
        calculatorContext.calculator = new SimpleCalculator();
    }

    @When("I input {string} on the calculator")
    public void whenIInputOnTheCalculator(final String input) {
        calculatorContext.calculator.input(input);
    }

    @Then("the calculator displays the value: {double}")
    public void thenTheCalculatorDisplaysTheValue(final double expectedValue) {
        assertThat(calculatorContext.calculator.getCurrentValue()).isEqualTo(expectedValue, byLessThan(1e-9));
    }
}
