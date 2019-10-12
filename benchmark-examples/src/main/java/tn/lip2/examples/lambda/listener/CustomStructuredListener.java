package tn.lip2.examples.lambda.listener;

import org.apache.spark.sql.execution.QueryExecution;
import org.apache.spark.sql.streaming.StreamingQueryListener;
import org.apache.spark.sql.util.QueryExecutionListener;
import tn.lip2.bdbench.adapters.GenericConsumer;
import tn.lip2.bdbench.adapters.models.ConsumerMetric;

import java.sql.Timestamp;


public class CustomStructuredListener extends StreamingQueryListener {

    GenericConsumer consumer ;

    public CustomStructuredListener(GenericConsumer consumer) {
        this.consumer = consumer;
    }


    @Override
    public void onQueryStarted(QueryStartedEvent event) {

    }

    @Override
    public void onQueryProgress(QueryProgressEvent event) {
        ConsumerMetric metric = new ConsumerMetric(consumer.getConsumerId(), new Timestamp(System.currentTimeMillis()), Long.toString(event.progress().numInputRows()), Long.toString(event.progress().durationMs().get("triggerExecution")));
        consumer.sendMetric(metric);
    }

    @Override
    public void onQueryTerminated(QueryTerminatedEvent event) {

    }
}
