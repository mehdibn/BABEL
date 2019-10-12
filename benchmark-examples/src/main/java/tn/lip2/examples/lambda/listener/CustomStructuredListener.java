package tn.lip2.examples.lambda.listener;

import org.apache.spark.sql.execution.QueryExecution;
import org.apache.spark.sql.util.QueryExecutionListener;
import tn.lip2.bdbench.adapters.GenericConsumer;
import tn.lip2.bdbench.adapters.models.ConsumerMetric;

import java.sql.Timestamp;


public class CustomStructuredListener implements QueryExecutionListener {

    GenericConsumer consumer ;

    public CustomStructuredListener(GenericConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onSuccess(String funcName, QueryExecution qe, long durationNs) {
        ConsumerMetric metric = new ConsumerMetric(consumer.getConsumerId(), new Timestamp(System.currentTimeMillis()), Long.toString(qe.toRdd().count()), Long.toString(durationNs));
        consumer.sendMetric(metric);
    }

    @Override
    public void onFailure(String funcName, QueryExecution qe, Exception exception) {

    }
}
