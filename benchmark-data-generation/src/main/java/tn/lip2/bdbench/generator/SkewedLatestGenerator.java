package tn.lip2.bdbench.generator;

/**
 * Generate a popularity distribution of items, skewed to favor recent items significantly more than older items.
 */
public class SkewedLatestGenerator extends NumberGenerator {
  private CounterGenerator basis;
  private final ZipfianGenerator zipfian;

  public SkewedLatestGenerator(CounterGenerator basis) {
    this.basis = basis;
    zipfian = new ZipfianGenerator(this.basis.lastValue());
    nextValue();
  }

  /**
   * Generate the next string in the distribution, skewed Zipfian favoring the items most recently returned by
   * the basis generator.
   */
  @Override
  public Long nextValue() {
    long max = basis.lastValue();
    long next = max - zipfian.nextLong(max);
    setLastValue(next);
    return next;
  }

  public static void main(String[] args) {
    SkewedLatestGenerator gen = new SkewedLatestGenerator(new CounterGenerator(1000));
    for (int i = 0; i < Integer.parseInt(args[0]); i++) {
      System.out.println(gen.nextString());
    }
  }

  @Override
  public double mean() {
    throw new UnsupportedOperationException("Can't compute mean of non-stationary distribution!");
  }
}
