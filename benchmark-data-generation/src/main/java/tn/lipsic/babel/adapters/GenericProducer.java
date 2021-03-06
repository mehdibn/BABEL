package tn.lipsic.babel.adapters;

import tn.lipsic.babel.ByteIterator;
import tn.lipsic.babel.DBException;
import tn.lipsic.babel.Status;

import java.util.*;

/**
 * A layer for accessing a database to be benchmarked. Each thread in the client
 * will be given its own instance of whatever GenericProducer class is to be used in the test.
 * This class should be constructed using a no-argument constructor, so we can
 * load it dynamically. Any argument-based initialization should be
 * done by init().
 * <p>
 * Note that babel does not make any use of the return codes returned by this class.
 * Instead, it keeps a count of the return values and presents them to the user.
 * <p>
 * The semantics of methods such as insert, update and delete vary from database
 * to database.  In particular, operations may or may not be durable once these
 * methods commit, and some systems may return 'success' regardless of whether
 * or not a tuple with a matching key existed before the call.  Rather than dictate
 * the exact semantics of these methods, we recommend you either implement them
 * to match the database's default semantics, or the semantics of your
 * target application.  For the sake of comparison between experiments we also
 * recommend you explain the semantics you chose when presenting performance results.
 */
public abstract class GenericProducer {
    /**
     * Properties for configuring this GenericProducer.
     */
    private Properties properties = new Properties();

    /**
     * Get the set of properties for this GenericProducer.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Set the properties for this GenericProducer.
     */
    public void setProperties(Properties p) {
        properties = p;

    }

    /**
     * Initialize any state for this GenericProducer.
     * Called once per GenericProducer instance; there is one GenericProducer instance per client thread.
     */
    public void init() throws DBException {
    }

    /**
     * Cleanup any state for this GenericProducer.
     * Called once per GenericProducer instance; there is one GenericProducer instance per client thread.
     */
    public void cleanup() throws DBException {
    }

    /**
     * Read a record from the database. Each field/value pair from the result will be stored in a HashMap.
     *
     * @param table  The name of the table
     * @param key    The record key of the record to read.
     * @param fields The list of fields to read, or null for all of them
     * @param result A HashMap of field/value pairs for the result
     * @return The result of the operation.
     */
    public abstract Status read(String table, String key, Set<String> fields, Map<String, ByteIterator> result);

    /**
     * Perform a range scan for a set of records in the database. Each field/value pair from the result will be stored
     * in a HashMap.
     *
     * @param table       The name of the table
     * @param startkey    The record key of the first record to read.
     * @param recordcount The number of records to read
     * @param fields      The list of fields to read, or null for all of them
     * @param result      A Vector of HashMaps, where each HashMap is a set field/value pairs for one record
     * @return The result of the operation.
     */
    public abstract Status scan(String table, String startkey, int recordcount, Set<String> fields,
                                Vector<HashMap<String, ByteIterator>> result);

    /**
     * Update a record in the database. Any field/value pairs in the specified values HashMap will be written into the
     * record with the specified record key, overwriting any existing values with the same field name.
     *
     * @param table  The name of the table
     * @param key    The record key of the record to write.
     * @param values A HashMap of field/value pairs to update in the record
     * @return The result of the operation.
     */
    public abstract Status update(String table, String key, Map<String, ByteIterator> values);

    /**
     * Insert a record in the database. Any field/value pairs in the specified values HashMap will be written into the
     * record with the specified record key.
     *
     * @param table  The name of the table
     * @param key    The record key of the record to insert.
     * @param values A HashMap of field/value pairs to insert in the record
     * @return The result of the operation.
     */
    public abstract Status insert(String table, String key, Map<String, ByteIterator> values);

    /**
     * Delete a record from the database.
     *
     * @param table The name of the table
     * @param key   The record key of the record to delete.
     * @return The result of the operation.
     */
    public abstract Status delete(String table, String key);
}
