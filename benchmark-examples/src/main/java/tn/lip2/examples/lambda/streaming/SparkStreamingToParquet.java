package tn.lip2.examples.lambda.streaming;


import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import tn.lip2.bdbench.Client;
import tn.lip2.bdbench.adapters.GenericConsumer;

import java.util.Properties;

/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: JavaDirectKafkaWordCount <brokers> <topics>
 * <brokers> is a list of one or more Kafka brokers
 * <topics> is a list of one or more kafka topics to consume from
 * <p>
 * Example:
 * $ bin/run-example streaming.JavaDirectKafkaWordCount broker1-host:port,broker2-host:port \
 * topic1,topic2
 */

public final class SparkToParquet extends GenericConsumer {
    private static final SparkToParquet injector = new SparkToParquet();


    public static void main(String[] args) throws Exception {

        Properties props = Client.parseArguments(args);

        String brokers = props.getProperty("SUTkafkabrokers");
        String topics = props.getProperty("SUTtopic");
        String parquetPath = props.getProperty("SUTHiveTablePath");
        System.out.println(brokers + " | " + topics);


        injector.setProperties(props);
        injector.init();


        // Create Session
        SparkSession spark = SparkSession
                .builder()
                .appName(SparkToParquet.class.getName())
                .master("local[*]")
                .getOrCreate();


        // Create direct kafka stream with brokers and topics
        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", brokers)
                .option("subscribe", topics)
                .option("key.deserializer", StringDeserializer.class.toString())
                .option("value.deserializer", StringDeserializer.class.toString())
                .option("auto.offset.reset", "latest")
                .option("enable.auto.commit", false)
                .option("group.id", "test")
                .load();

        //edf.printSchema();

        //df.writeStream().format("console").trigger(Trigger.Continuous("1 second")).start().awaitTermination();
        //df.writeStream().format("console").option("truncate", false).trigger(Trigger.Continuous("1 second")).start().awaitTermination() ;

        // write to hive with parquet format
        df.writeStream()
                .format("parquet")
                .option("path", parquetPath)
                .option("checkpointLocation", "/tmp/check")
                .start()
                .awaitTermination();


    }
}