package tn.lip2.bdbench.generator;

/**
 * A generator that is capable of generating numeric values.
 *
 */
public abstract class NumberGenerator extends Generator<Number> {
  private Number lastVal;

  /**
   * Set the last value generated. NumberGenerator subclasses must use this call
   * to properly set the last value, or the {@link #lastValue()} calls won't work.
   */
  protected void setLastValue(Number last) {
    lastVal = last;
  }


  @Override
  public Number lastValue() {
    return lastVal;
  }

  /**
   * Return the expected value (mean) of the values this generator will return.
   */
  public abstract double mean();
}
