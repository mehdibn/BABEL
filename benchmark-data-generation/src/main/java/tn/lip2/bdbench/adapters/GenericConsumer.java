package tn.lip2.bdbench.adapters;

import tn.lip2.bdbench.adapters.models.ConsumerMetric;
import tn.lip2.core.KafkaInjector;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

import static tn.lip2.bdbench.Client.KAFKA_BROKERS;
import static tn.lip2.bdbench.Client.KAFKA_CONSUMER_TOPIC;

public abstract class GenericConsumer {
    /**
     * Properties for configuring this GenericConsumer.
     */
    private Properties properties = new Properties();

    public String getConsumerId() {
        return consumerId;
    }

    /**
     * Properties for configuring this GenericConsumer.
     */
    private String consumerId ;


    /**
     * Kafka Injector for GenericConsumer Metrics.
     */
    private KafkaInjector kafkaInjector;

    public GenericConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

    /**
     * Set the properties for this GenericConsumer.
     */
    public void setProperties(Properties p) {
        properties = p;

    }


    /**
     * Get the set of properties for this GenericConsumer.
     */
    public Properties getProperties() {
        return properties;
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

    public void close(){
        kafkaInjector.closeOutput();
    }

}
