package tn.lipsic.babel.measurements.exporter;

import java.io.Closeable;
import java.io.IOException;

/**
 * Used to export the collected measurements into a useful format, for example
 * human readable text or machine readable JSON.
 */
public interface MeasurementsExporter extends Closeable {
    /**
     * Write a measurement to the exported format.
     *
     * @param metric      Metric name, for example "READ LATENCY".
     * @param measurement Measurement name, for example "Average latency".
     * @param i           Measurement to write.
     * @throws IOException if writing failed
     */
    void write(String metric, String measurement, int i) throws IOException;

    /**
     * Write a measurement to the exported format.
     *
     * @param metric      Metric name, for example "READ LATENCY".
     * @param measurement Measurement name, for example "Average latency".
     * @param i           Measurement to write.
     * @throws IOException if writing failed
     */
    void write(String metric, String measurement, long i) throws IOException;

    /**
     * Write a measurement to the exported format.
     *
     * @param metric      Metric name, for example "READ LATENCY".
     * @param measurement Measurement name, for example "Average latency".
     * @param d           Measurement to write.
     * @throws IOException if writing failed
     */
    void write(String metric, String measurement, double d) throws IOException;
}
