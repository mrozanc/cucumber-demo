package fr.rozanc.sandbox.cucumber.demo.operations;

public class Multiplication implements CalculatorOperation {

    @Override
    public double apply(final Double... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Multiplication should have exactly 2 arguments, " + args.length + " given.");
        }
        return args[0] * args[1];
    }
}
