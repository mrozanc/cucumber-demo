package fr.rozanc.sandbox.cucumber.demo.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubtractionTest {

    private Subtraction subtraction;

    @BeforeEach
    void setUp() {
        subtraction = new Subtraction();
    }

    @Test
    void should_subtract_two_numbers() {
        final double result = subtraction.apply(4.0, 7.5);
        assertThat(result).isEqualTo(-3.5);
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
        assertThatThrownBy(() -> subtraction.apply(args)).isInstanceOf(IllegalArgumentException.class);
    }
}