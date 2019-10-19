package tn.lipsic.babel.measurements;

import tn.lipsic.babel.Status;
import tn.lipsic.babel.measurements.exporter.MeasurementsExporter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A single measured metric (such as READ LATENCY).
 */
public abstract class OneMeasurement {

    private final String name;
    private final ConcurrentHashMap<Status, AtomicInteger> returncodes;

    /**
     * @param name measurement name
     */
    public OneMeasurement(String name) {
        this.name = name;
        this.returncodes = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public abstract void measure(int latency);

    public abstract String getSummary();

    /**
     * No need for synchronization, using CHM to deal with that.
     */
    public void reportStatus(Status status) {
        AtomicInteger counter = returncodes.get(status);

        if (counter == null) {
            counter = new AtomicInteger();
            AtomicInteger other = returncodes.putIfAbsent(status, counter);
            if (other != null) {
                counter = other;
            }
        }

        counter.incrementAndGet();
    }

    /**
     * Export the current measurements to a suitable format.
     *
     * @param exporter Exporter representing the type of format to write to.
     * @throws IOException Thrown if the export failed.
     */
    public abstract void exportMeasurements(MeasurementsExporter exporter) throws IOException;

    protected final void exportStatusCounts(MeasurementsExporter exporter) throws IOException {
        for (Map.Entry<Status, AtomicInteger> entry : returncodes.entrySet()) {
            exporter.write(getName(), "Return=" + entry.getKey().getName(), entry.getValue().get());
        }
    }
}
