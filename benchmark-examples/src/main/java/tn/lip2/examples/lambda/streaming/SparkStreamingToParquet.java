package tn.lip2.examples.lambda.streaming;


import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import tn.lip2.bdbench.Client;
import tn.lip2.bdbench.adapters.GenericConsumer;

import java.util.Properties;

public final class SparkStreamingToParquet extends GenericConsumer {
    private static final SparkStreamingToParquet injector = new SparkStreamingToParquet();


    public static void main(String[] args) throws Exception {

        Properties props = Client.parseArguments(args);

        String brokers = props.getProperty("SUTkafkabrokers");
        String topics = props.getProperty("SUTtopic");
        String parquetPath = props.getProperty("SUTHiveTablePath");
        String sparkMode = props.getProperty("SUTSparkMode");
        System.out.println("Spark Mode : " + sparkMode);
        System.out.println(brokers + " | " + topics);


        injector.setProperties(props);
        injector.init();


        // Create Session
        // Create Session
        SparkSession spark;
        if ("local".equals(sparkMode)) {
            spark = SparkSession
                    .builder()
                    .appName(SparkStreamingToParquet.class.getName())
                    .master("local[*]")
                    .getOrCreate();
        } else {
            spark = SparkSession
                    .builder()
                    .appName(SparkStreamingToParquet.class.getName())
                    .master("local[*]")
                    .getOrCreate();
        }

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

        // write to hive with parquet format
        df.writeStream()
                .format("parquet")
                .option("path", parquetPath)
                .option("checkpointLocation", "/tmp/check")
                .start()
                .awaitTermination();


    }
}