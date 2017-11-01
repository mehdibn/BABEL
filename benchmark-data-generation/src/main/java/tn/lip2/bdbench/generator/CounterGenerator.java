package tn.lip2.bdbench.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates a sequence of integers.
 * (0, 1, ...)
 */
public class CounterGenerator extends NumberGenerator {
  private final AtomicLong counter;

  /**
   * Create a counter that starts at countstart.
   */
  public CounterGenerator(long countstart) {
    counter=new AtomicLong(countstart);
  }

  @Override
  public Long nextValue() {
    return counter.getAndIncrement();
  }

  @Override
  public Long lastValue() {
    return counter.get() - 1;
  }

  @Override
  public double mean() {
    throw new UnsupportedOperationException("Can't compute mean of non-stationary distribution!");
  }
}
