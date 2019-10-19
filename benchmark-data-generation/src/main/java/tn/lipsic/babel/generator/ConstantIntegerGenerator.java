package tn.lipsic.babel.generator;

/**
 * A trivial integer generator that always returns the same value.
 */
public class ConstantIntegerGenerator extends NumberGenerator {
    private final int i;

    /**
     * @param i The integer that this generator will always return.
     */
    public ConstantIntegerGenerator(int i) {
        this.i = i;
    }

    @Override
    public Integer nextValue() {
        return i;
    }

    @Override
    public double mean() {
        return i;
    }

}
