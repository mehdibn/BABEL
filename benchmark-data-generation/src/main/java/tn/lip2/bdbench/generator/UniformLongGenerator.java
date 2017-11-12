package tn.lip2.bdbench.generator;

import tn.lip2.bdbench.Utils;

/**
 * Generates longs randomly uniform from an interval.
 */
public class UniformLongGenerator extends NumberGenerator {
  private final long lb, ub, interval;

  /**
   * Creates a generator that will return longs uniformly randomly from the 
   * interval [lb,ub] inclusive (that is, lb and ub are possible values)
   * (lb and ub are possible values).
   *
   * @param lb the lower bound (inclusive) of generated values
   * @param ub the upper bound (inclusive) of generated values
   */
  public UniformLongGenerator(long lb, long ub) {
    this.lb = lb;
    this.ub = ub;
    interval = this.ub - this.lb + 1;
  }

  @Override
  public Long nextValue() {
    long ret = Math.abs(Utils.random().nextLong()) % interval  + lb;
    setLastValue(ret);

    return ret;
  }

  @Override
  public double mean() {
    return ((lb + (long) ub)) / 2.0;
  }
}
