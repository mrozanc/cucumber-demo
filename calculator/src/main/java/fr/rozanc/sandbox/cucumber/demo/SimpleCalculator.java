package fr.rozanc.sandbox.cucumber.demo;

import com.google.common.collect.ImmutableMap;
import fr.rozanc.sandbox.cucumber.demo.operations.Addition;
import fr.rozanc.sandbox.cucumber.demo.operations.CalculatorOperation;
import fr.rozanc.sandbox.cucumber.demo.operations.Division;
import fr.rozanc.sandbox.cucumber.demo.operations.Evaluation;
import fr.rozanc.sandbox.cucumber.demo.operations.Multiplication;
import fr.rozanc.sandbox.cucumber.demo.operations.Subtraction;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class SimpleCalculator implements Calculator {

    private static final Map<String, CalculatorOperation> OPERATIONS
            = ImmutableMap.of("+", new Addition(),
                              "-", new Subtraction(),
                              "*", new Multiplication(),
                              "/", new Division(),
                              "=", new Evaluation());

    private final Queue<String> inputRegistry = new LinkedList<>();

    private double currentValue = 0.;
    private Double lastComputedResult = null;

    public void input(String input) {
        final String[] inputs = input.split("\\s+");
        inputRegistry.addAll(Arrays.asList(inputs));
    }

    private void evaluateOnce() {
        final Double[] operands = new Double[]{currentValue, null};
        operands[0] = Objects.requireNonNullElseGet(lastComputedResult, () -> currentValue);
        lastComputedResult = null;
        CalculatorOperation operation = null;

        do {
            final String input = inputRegistry.poll();
            if (input == null) {
                continue;
            }
            if (OPERATIONS.containsKey(input)) {
                operation = OPERATIONS.get(input);
            } else {
                operands[operation == null ? 0 : 1] = Double.parseDouble(input);
            }
        } while (!inputRegistry.isEmpty() && (operation == null || operands[1] == null));

        currentValue = operands[operands[1] == null ? 0 : 1];
        if (operation != null) {
            lastComputedResult = operation.apply(Arrays.stream(operands).filter(Objects::nonNull).toArray(Double[]::new));
        }
    }

    @Override
    public Double getCurrentValue() {
        do {
            evaluateOnce();
        } while (!inputRegistry.isEmpty());
        return currentValue;
    }
}
