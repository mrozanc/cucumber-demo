package fr.rozanc.sandbox.cucumber.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleCalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimpleCalculator();
    }

    static Stream<Arguments> calculatorArguments() {
        return Stream.of(
                Arguments.of("3 + 5 =", 8.0),
                Arguments.of("3 + 7 =", 10.0),
                Arguments.of("5 / 2 =", 2.5)
        );
    }

    @ParameterizedTest
    @MethodSource("calculatorArguments")
    void should_evaluate_operations(final String input, final Double expectedResult) {
        calculator.input(input);
        final Double result = calculator.getCurrentValue();
        assertEquals(expectedResult, result);
    }

    @Test
    void should_display_0_if_no_interaction() {
        assertEquals(0.0, calculator.getCurrentValue());
    }
}