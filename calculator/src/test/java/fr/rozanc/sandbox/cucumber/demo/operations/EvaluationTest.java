package fr.rozanc.sandbox.cucumber.demo.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EvaluationTest {

    private Evaluation evaluation;

    @BeforeEach
    void setUp() {
        evaluation = new Evaluation();
    }

    @Test
    void should_return_the_given_number() {
        final double result = evaluation.apply(4.0);
        assertEquals(4.0, result);
    }

    static Stream<Arguments> argumentsParameters() {
        return Stream.of(
                Arguments.of((Object) new Double[]{5.1, 3.2, 2.5}),
                Arguments.of((Object) new Double[]{-3.1, 2.0}),
                Arguments.of((Object) new Double[0])
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsParameters")
    void should_accept_only_1_arguments(final Double[] args) {
        assertThrows(IllegalArgumentException.class, () -> evaluation.apply(args));
    }
}