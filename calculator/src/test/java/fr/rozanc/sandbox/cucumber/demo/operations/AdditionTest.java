package fr.rozanc.sandbox.cucumber.demo.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdditionTest {

    private Addition addition;

    @BeforeEach
    void setUp() {
        addition = new Addition();
    }

    @Test
    void should_add_two_numbers() {
        final double result = addition.apply(4.0, 7.5);
        assertEquals(11.5, result);
    }

    static Stream<Arguments> argumentsParameters() {
        return Stream.of(
                Arguments.of((Object) new Double[]{5.1, 3.2, 2.5}),
                Arguments.of((Object) new Double[]{-3.1})
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsParameters")
    void should_accept_only_2_arguments(final Double[] args) {
        assertThrows(IllegalArgumentException.class, () -> addition.apply(args));
    }
}