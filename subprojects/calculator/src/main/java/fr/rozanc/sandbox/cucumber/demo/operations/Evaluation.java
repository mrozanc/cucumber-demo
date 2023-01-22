package fr.rozanc.sandbox.cucumber.demo.operations;

public class Evaluation implements CalculatorOperation {

    @Override
    public double apply(final Double... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Evaluation should have exactly 1 argument, " + args.length + " given.");
        }
        return args[0];
    }
}
