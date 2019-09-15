package tn.lip2.examples.lambda.batch;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import tn.lip2.bdbench.Client;
import tn.lip2.bdbench.adapters.GenericConsumer;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;


public final class SparkBatchAnalyzer extends GenericConsumer {
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final AtomicLong runningCount = new AtomicLong(0);
    private static final AtomicLong duration = new AtomicLong(0);
    private static final SparkBatchAnalyzer analyzer = new SparkBatchAnalyzer();


    public static void main(String[] args) throws Exception {

        Properties props = Client.parseArguments(args);

        String parquetPath = props.getProperty("SUTHiveTablePath");
        String sparkMode = props.getProperty("SUTSparkMode");
        System.out.println("Spark Mode : " + sparkMode);
        System.out.println("Parquet Path : " + parquetPath);


        analyzer.setProperties(props);
        analyzer.init();

        // Create Session
        SparkSession spark;
        if ("local".equals(sparkMode)) {
            spark = SparkSession
                    .builder()
                    .appName(SparkBatchAnalyzer.class.getName())
                    .master("local[*]")
                    .getOrCreate();
        } else {
            spark = SparkSession
                    .builder()
                    .appName(SparkBatchAnalyzer.class.getName())
                    .getOrCreate();
        }


        // Analytics
        Dataset<Row> lines = spark.read().parquet(parquetPath);
        lines.sort("offset").show();
        System.out.println("count : " + lines.count());
        System.out.println("count de-duplicated : " + lines.select("offset").distinct().count());
    }
}