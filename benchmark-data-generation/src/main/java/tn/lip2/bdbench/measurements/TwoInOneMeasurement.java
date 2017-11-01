package tn.lip2.bdbench.measurements;

import tn.lip2.bdbench.Status;
import tn.lip2.bdbench.measurements.exporter.MeasurementsExporter;

import java.io.IOException;

/**
 * delegates to 2 measurement instances.
 */
public class TwoInOneMeasurement extends OneMeasurement {

  private final OneMeasurement thing1, thing2;

  public TwoInOneMeasurement(String name, OneMeasurement thing1, OneMeasurement thing2) {
    super(name);
    this.thing1 = thing1;
    this.thing2 = thing2;
  }

  /**
   * No need for synchronization, using CHM to deal with that.
   */
  @Override
  public void reportStatus(final Status status) {
    thing1.reportStatus(status);
  }

  /**
   * It appears latency is reported in micros.
   * Using {@link org.HdrHistogram.Recorder} to support concurrent updates to histogram.
   */
  @Override
  public void measure(int latencyInMicros) {
    thing1.measure(latencyInMicros);
    thing2.measure(latencyInMicros);
  }

  /**
   * This is called from a main thread, on orderly termination.
   */
  @Override
  public void exportMeasurements(MeasurementsExporter exporter) throws IOException {
    thing1.exportMeasurements(exporter);
    thing2.exportMeasurements(exporter);
  }

  /**
   * This is called periodically from the StatusThread. There's a single StatusThread per Client process.
   * We optionally serialize the interval to log on this opportunity.
   *
   * @see OneMeasurement#getSummary()
   */
  @Override
  public String getSummary() {
    return thing1.getSummary() + "\n" + thing2.getSummary();
  }

}
