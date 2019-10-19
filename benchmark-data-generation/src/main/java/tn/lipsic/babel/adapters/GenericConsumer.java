package tn.lipsic.babel.adapters;

import tn.lipsic.babel.adapters.models.ConsumerMetric;
import tn.lipsic.core.KafkaInjector;

import java.io.IOException;
import java.util.Properties;

import static tn.lipsic.babel.Client.KAFKA_BROKERS;
import static tn.lipsic.babel.Client.KAFKA_CONSUMER_TOPIC;

public abstract class GenericConsumer {
    /**
     * Properties for configuring this GenericConsumer.
     */
    private Properties properties = new Properties();
    /**
     * Properties for configuring this GenericConsumer.
     */
    private String consumerId;
    /**
     * Kafka Injector for GenericConsumer Metrics.
     */
    private KafkaInjector kafkaInjector;


    public GenericConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    /**
     * Get the set of properties for this GenericConsumer.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Set the properties for this GenericConsumer.
     */
    public void setProperties(Properties p) {
        properties = p;

    }

    /**
     * Initialize any state for this GenericConsumer.
     * Called once per GenericConsumer instance; there is one GenericConsumer instance per client thread.
     */
    public void init() {
        if ((getProperties().getProperty(KAFKA_BROKERS) != null) && (getProperties().getProperty(KAFKA_CONSUMER_TOPIC) != null)) {
            kafkaInjector = new KafkaInjector(getProperties().getProperty(KAFKA_BROKERS), getProperties().getProperty(KAFKA_CONSUMER_TOPIC));
            kafkaInjector.createOutput();
        }
    }

    /**
     * Cleanup any state for this GenericConsumer.
     * Called once per GenericConsumer instance; there is one GenericConsumer instance per client thread.
     */
    public void cleanup() {
        kafkaInjector.closeOutput();
    }

    /*

    timestamp : Time of Metric

    duration  : Benchmark Duration

    operations: nb of operations per second

    count     : total operations between (duration) and (duration-1)

    latency   : average Latency
     */

    public void sendMetric(ConsumerMetric metric) {
        try {
            kafkaInjector.sendMessage(metric.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        kafkaInjector.closeOutput();
    }

}
