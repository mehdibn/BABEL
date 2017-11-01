package tn.lip2.bdbench.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates a sequence of integers 0, 1, ...
 */
public class SequentialGenerator extends NumberGenerator {
  private final AtomicLong counter;
  private long interval;
  private long countstart;

  /**
   * Create a counter that starts at countstart.
   */
  public SequentialGenerator(long countstart, long countend) {
    counter = new AtomicLong();
    setLastValue(counter.get());
    this.countstart = countstart;
    interval = countend - countstart + 1;
  }

  /**
   * If the generator returns numeric (long) values, return the next value as an long.
   * Default is to return -1, which is appropriate for generators that do not return numeric values.
   */
  public long nextLong() {
    long ret = countstart + counter.getAndIncrement() % interval;
    setLastValue(ret);
    return ret;
  }

  @Override
  public Number nextValue() {
    long ret = countstart + counter.getAndIncrement() % interval;
    setLastValue(ret);
    return ret;
  }

  @Override
  public Number lastValue() {
    return counter.get() + 1;
  }

  @Override
  public double mean() {
    throw new UnsupportedOperationException("Can't compute mean of non-stationary distribution!");
  }
}
