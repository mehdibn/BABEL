package tn.lip2.examples.lambda.streaming;


import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Time;
import scala.Tuple2;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.apache.spark.streaming.Durations;
import tn.lip2.bdbench.Client;
import tn.lip2.bdbench.adapters.GenericConsumer;
import tn.lip2.bdbench.adapters.models.ConsumerMetric;


public final class SparkStreamingAnalyzer extends GenericConsumer {
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final AtomicLong runningCount = new AtomicLong(0);
    private static final AtomicLong duration = new AtomicLong(0);
    private static final SparkStreamingAnalyzer analyzer = new SparkStreamingAnalyzer();


    public static void main(String[] args) throws Exception {

        Properties props = Client.parseArguments(args);

        String brokers = props.getProperty("SUTkafkabrokers");
        String topics = props.getProperty("SUTtopic");
        String sparkMode = props.getProperty("SUTSparkMode");
        System.out.println("Spark Mode : " + sparkMode);
        System.out.println(brokers + " | " + topics);


        analyzer.setProperties(props);
        analyzer.init();


        // Create context with a 2 seconds batch interval
        // .set("spark.metrics.conf", "/Users/mehdi/IdeaProjects/BDBench/examples/src/main/resources/metrics.properties")
        SparkConf sparkConf = new SparkConf();
        if ("local".equals(sparkMode)) {
            sparkConf.setMaster("local[*]");
        }

        sparkConf.setAppName("SparkStreamingAnalyzer");


        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", brokers);
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        kafkaParams.put("group.id", "test");


        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams));

        // Get the lines, split them into words, count the words and print
        jssc.sparkContext().accumulator(0, "count");
        JavaDStream<String> lines = messages.map(ConsumerRecord::value);
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(SPACE.split(x)).iterator());
        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(s -> new Tuple2<>(s, 1))
                .reduceByKey((i1, i2) -> i1 + i2);
        wordCounts.print();

        lines.foreachRDD((JavaRDD<String> stringJavaRDD, Time time) -> {

            //System.out.println("Time : =" + time + "| Count =" + stringJavaRDD.count() + " | Global Count=" + runningCount.addAndGet(stringJavaRDD.count()));

            //Timestamp timestamp, String duration, String operations, String count
            analyzer.sendMetric(new ConsumerMetric(new Timestamp(System.currentTimeMillis()), Long.toString(duration.addAndGet(1)), Long.toString(runningCount.addAndGet(stringJavaRDD.count())), Long.toString(stringJavaRDD.count())));
        });
        // Start the computation
        jssc.start();
        jssc.awaitTermination();
    }
}