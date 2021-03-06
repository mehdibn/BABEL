package tn.lipsic.babel.adapters.models;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;

public class ConsumerMetric {

    /*

    timestamp : Time of Metric

    duration  : Benchmark Duration

    operations: nb of operations per second

    count     : total operations between (duration) and (duration-1)

    latency   : average Latency

     */

    private static ObjectMapper MAPPER = new ObjectMapper();
    private Timestamp timestamp;
    private String duration;
    private String operations;
    private String count;
    private String latency;
    private String consumer;


    public ConsumerMetric(String consumer, Timestamp timestamp, String duration, String operations, String count, String latency) {
        this.timestamp = timestamp;
        this.duration = duration;
        this.operations = operations;
        this.count = count;
        this.latency = latency;
        this.consumer = consumer;
    }

    public ConsumerMetric(String consumer, Timestamp timestamp, String duration, String operations, String count) {
        this.timestamp = timestamp;
        this.duration = duration;
        this.operations = operations;
        this.count = count;
        this.consumer = consumer;
    }


    public ConsumerMetric(String consumer, Timestamp timestamp, String count, String latency) {
        this.timestamp = timestamp;
        this.count = count;
        this.latency = latency;
        this.consumer = consumer;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public String toJson() throws IOException {
        return MAPPER.writeValueAsString(this);
    }

    public String getConsumer() {
        return consumer;
    }
}
