package tn.lip2.core;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

public class KafkaInjector {

    private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(KafkaInjector.class);

    private Producer<String, String> producer;
    private String topic;
    private int count = 0;
    private int partitions;

    public KafkaInjector(String brokersList, String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokersList);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put("request.timeout.ms", 100);
        this.topic = topic;
        producer = new KafkaProducer<String, String>(props);
        partitions = producer.partitionsFor(topic).size();
    }


    public void closeOutput() {
        producer.flush();
        producer.close();
        LOGGER.info(count + " data inserted");
    }


    public void createOutput() {
        //TODO
    }


    public void sendMessage(String value) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        //producer.send(new ProducerRecord<String, String>(topic, Integer.toString(count), value));
        //producer.send(new ProducerRecord<String, String>(topic, count % partitions, t.getDateTime(), Integer.toString(count) + " partitions : " + partitions + " time :" + Long.toString(t.getDateTime()), value));
        producer.send(new ProducerRecord<String, String>(topic, count % partitions, t.getTime(), Integer.toString(count), value));
        producer.flush();
        count++;
    }


}