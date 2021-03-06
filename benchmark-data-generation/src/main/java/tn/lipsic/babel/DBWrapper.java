package tn.lipsic.babel;

import org.apache.htrace.core.TraceScope;
import org.apache.htrace.core.Tracer;
import tn.lipsic.babel.adapters.GenericProducer;
import tn.lipsic.babel.measurements.Measurements;

import java.util.*;

/**
 * Wrapper around a "real" GenericProducer that measures latencies and counts return codes.
 * Also reports latency separately between OK and failed operations.
 */
public class DBWrapper extends GenericProducer {
    private static final String REPORT_LATENCY_FOR_EACH_ERROR_PROPERTY = "reportlatencyforeacherror";
    private static final String REPORT_LATENCY_FOR_EACH_ERROR_PROPERTY_DEFAULT = "false";
    private static final String LATENCY_TRACKED_ERRORS_PROPERTY = "latencytrackederrors";
    private final GenericProducer producer;
    private final Measurements measurements;
    private final Tracer tracer;
    private final String scopeStringCleanup;
    private final String scopeStringDelete;
    private final String scopeStringInit;
    private final String scopeStringInsert;
    private final String scopeStringRead;
    private final String scopeStringScan;
    private final String scopeStringUpdate;
    private boolean reportLatencyForEachError = false;
    private Set<String> latencyTrackedErrors = new HashSet<String>();

    public DBWrapper(final GenericProducer producer, final Tracer tracer) {
        this.producer = producer;
        measurements = Measurements.getMeasurements();
        this.tracer = tracer;
        final String simple = producer.getClass().getSimpleName();
        scopeStringCleanup = simple + "#cleanup";
        scopeStringDelete = simple + "#delete";
        scopeStringInit = simple + "#init";
        scopeStringInsert = simple + "#insert";
        scopeStringRead = simple + "#read";
        scopeStringScan = simple + "#scan";
        scopeStringUpdate = simple + "#update";
    }

    /**
     * Get the set of properties for this GenericProducer.
     */
    public Properties getProperties() {
        return producer.getProperties();
    }

    /**
     * Set the properties for this GenericProducer.
     */
    public void setProperties(Properties p) {
        producer.setProperties(p);
    }

    /**
     * Initialize any state for this GenericProducer.
     * Called once per GenericProducer instance; there is one GenericProducer instance per client thread.
     */
    public void init() throws DBException {
        try (final TraceScope span = tracer.newScope(scopeStringInit)) {
            producer.init();

            this.reportLatencyForEachError = Boolean.parseBoolean(getProperties().
                    getProperty(REPORT_LATENCY_FOR_EACH_ERROR_PROPERTY,
                            REPORT_LATENCY_FOR_EACH_ERROR_PROPERTY_DEFAULT));

            if (!reportLatencyForEachError) {
                String latencyTrackedErrorsProperty = getProperties().getProperty(LATENCY_TRACKED_ERRORS_PROPERTY, null);
                if (latencyTrackedErrorsProperty != null) {
                    this.latencyTrackedErrors = new HashSet<String>(Arrays.asList(
                            latencyTrackedErrorsProperty.split(",")));
                }
            }

            System.err.println("DBWrapper: report latency for each error is " +
                    this.reportLatencyForEachError + " and specific error codes to track" +
                    " for latency are: " + this.latencyTrackedErrors.toString());
        }
    }

    /**
     * Cleanup any state for this GenericProducer.
     * Called once per GenericProducer instance; there is one GenericProducer instance per client thread.
     */
    public void cleanup() throws DBException {
        try (final TraceScope span = tracer.newScope(scopeStringCleanup)) {
            long ist = measurements.getIntendedtartTimeNs();
            long st = System.nanoTime();
            producer.cleanup();
            long en = System.nanoTime();
            measure("CLEANUP", Status.OK, ist, st, en);
        }
    }

    /**
     * Read a record from the database. Each field/value pair from the result
     * will be stored in a HashMap.
     *
     * @param table  The name of the table
     * @param key    The record key of the record to read.
     * @param fields The list of fields to read, or null for all of them
     * @param result A HashMap of field/value pairs for the result
     * @return The result of the operation.
     */
    public Status read(String table, String key, Set<String> fields,
                       Map<String, ByteIterator> result) {
        try (final TraceScope span = tracer.newScope(scopeStringRead)) {
            long ist = measurements.getIntendedtartTimeNs();
            long st = System.nanoTime();
            Status res = producer.read(table, key, fields, result);
            long en = System.nanoTime();
            measure("READ", res, ist, st, en);
            measurements.reportStatus("READ", res);
            return res;
        }
    }

    /**
     * Perform a range scan for a set of records in the database.
     * Each field/value pair from the result will be stored in a HashMap.
     *
     * @param table       The name of the table
     * @param startkey    The record key of the first record to read.
     * @param recordcount The number of records to read
     * @param fields      The list of fields to read, or null for all of them
     * @param result      A Vector of HashMaps, where each HashMap is a set field/value pairs for one record
     * @return The result of the operation.
     */
    public Status scan(String table, String startkey, int recordcount,
                       Set<String> fields, Vector<HashMap<String, ByteIterator>> result) {
        try (final TraceScope span = tracer.newScope(scopeStringScan)) {
            long ist = measurements.getIntendedtartTimeNs();
            long st = System.nanoTime();
            Status res = producer.scan(table, startkey, recordcount, fields, result);
            long en = System.nanoTime();
            measure("SCAN", res, ist, st, en);
            measurements.reportStatus("SCAN", res);
            return res;
        }
    }

    private void measure(String op, Status result, long intendedStartTimeNanos,
                         long startTimeNanos, long endTimeNanos) {
        String measurementName = op;
        if (result == null || !result.isOk()) {
            if (this.reportLatencyForEachError ||
                    this.latencyTrackedErrors.contains(result.getName())) {
                measurementName = op + "-" + result.getName();
            } else {
                measurementName = op + "-FAILED";
            }
        }
        measurements.measure(measurementName,
                (int) ((endTimeNanos - startTimeNanos) / 1000));
        measurements.measureIntended(measurementName,
                (int) ((endTimeNanos - intendedStartTimeNanos) / 1000));
    }

    /**
     * Update a record in the database. Any field/value pairs in the specified values HashMap will be written into the
     * record with the specified record key, overwriting any existing values with the same field name.
     *
     * @param table  The name of the table
     * @param key    The record key of the record to write.
     * @param values A HashMap of field/value pairs to update in the record
     * @return The result of the operation.
     */
    public Status update(String table, String key,
                         Map<String, ByteIterator> values) {
        try (final TraceScope span = tracer.newScope(scopeStringUpdate)) {
            long ist = measurements.getIntendedtartTimeNs();
            long st = System.nanoTime();
            Status res = producer.update(table, key, values);
            long en = System.nanoTime();
            measure("UPDATE", res, ist, st, en);
            measurements.reportStatus("UPDATE", res);
            return res;
        }
    }

    /**
     * Insert a record in the database. Any field/value pairs in the specified
     * values HashMap will be written into the record with the specified
     * record key.
     *
     * @param table  The name of the table
     * @param key    The record key of the record to insert.
     * @param values A HashMap of field/value pairs to insert in the record
     * @return The result of the operation.
     */
    public Status insert(String table, String key,
                         Map<String, ByteIterator> values) {
        try (final TraceScope span = tracer.newScope(scopeStringInsert)) {
            long ist = measurements.getIntendedtartTimeNs();
            long st = System.nanoTime();
            Status res = producer.insert(table, key, values);
            long en = System.nanoTime();
            measure("INSERT", res, ist, st, en);
            measurements.reportStatus("INSERT", res);
            return res;
        }
    }

    /**
     * Delete a record from the database.
     *
     * @param table The name of the table
     * @param key   The record key of the record to delete.
     * @return The result of the operation.
     */
    public Status delete(String table, String key) {
        try (final TraceScope span = tracer.newScope(scopeStringDelete)) {
            long ist = measurements.getIntendedtartTimeNs();
            long st = System.nanoTime();
            Status res = producer.delete(table, key);
            long en = System.nanoTime();
            measure("DELETE", res, ist, st, en);
            measurements.reportStatus("DELETE", res);
            return res;
        }
    }
}
