package tn.lip2.bdbench.measurements.exporter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Write human readable text. Tries to emulate the previous print report method.
 */
public class TextMeasurementsExporter implements MeasurementsExporter {
  private final BufferedWriter bw;

  public TextMeasurementsExporter(OutputStream os) {
    this.bw = new BufferedWriter(new OutputStreamWriter(os));
  }

  public void write(String metric, String measurement, int i) throws IOException {
    bw.write("[" + metric + "], " + measurement + ", " + i);
    bw.newLine();
  }

  public void write(String metric, String measurement, long i) throws IOException {
    bw.write("[" + metric + "], " + measurement + ", " + i);
    bw.newLine();
  }

  public void write(String metric, String measurement, double d) throws IOException {
    bw.write("[" + metric + "], " + measurement + ", " + d);
    bw.newLine();
  }

  public void close() throws IOException {
    this.bw.close();
  }
}
