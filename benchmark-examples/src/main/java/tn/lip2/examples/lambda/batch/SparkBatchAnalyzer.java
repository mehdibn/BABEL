package tn.lip2.examples.lambda.batch;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.QueryExecution;
import org.apache.spark.sql.util.QueryExecutionListener;
import tn.lip2.bdbench.Client;
import tn.lip2.bdbench.adapters.GenericConsumer;
import tn.lip2.bdbench.adapters.models.ConsumerMetric;
import tn.lip2.examples.lambda.listener.CustomStructuredListener;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;


public final class SparkBatchAnalyzer extends GenericConsumer {
    public static Long count;
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final AtomicLong runningCount = new AtomicLong(0);
    private static final AtomicLong duration = new AtomicLong(0);
    private static final SparkBatchAnalyzer analyzer = new SparkBatchAnalyzer("SparkBatchAnalyzer");

    public SparkBatchAnalyzer(String consumerId) {
        super(consumerId);
    }


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

        spark.listenerManager().register(new QueryExecutionListener() {
            @Override
            public void onFailure(String funcName, QueryExecution qe, Exception exception) {

            }

            @Override
            public void onSuccess(String funcName, QueryExecution qe, long durationNs) {
                ConsumerMetric metric = new ConsumerMetric(analyzer.getConsumerId(), new Timestamp(System.currentTimeMillis()), Long.toString(count), Long.toString(durationNs));
                analyzer.sendMetric(metric);
            }
        });
        // Analytics
        Dataset<Row> lines = spark.read().parquet(parquetPath);
        count = lines.count();
        System.out.println("count : " + count);
        System.out.println("count de-duplicated : " + lines.select("offset").distinct().count());
    }
}