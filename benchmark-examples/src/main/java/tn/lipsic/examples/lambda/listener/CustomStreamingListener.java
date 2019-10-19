package tn.lipsic.examples.lambda.listener;

import org.apache.spark.streaming.scheduler.*;
import tn.lipsic.babel.adapters.GenericConsumer;
import tn.lipsic.babel.adapters.models.ConsumerMetric;

import java.sql.Timestamp;

public class CustomStreamingListener implements StreamingListener {

    GenericConsumer consumer;

    public CustomStreamingListener(GenericConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onStreamingStarted(StreamingListenerStreamingStarted streamingStarted) {
    }

    @Override
    public void onReceiverStarted(StreamingListenerReceiverStarted receiverStarted) {
    }

    @Override
    public void onReceiverError(StreamingListenerReceiverError receiverError) {
    }

    @Override
    public void onReceiverStopped(StreamingListenerReceiverStopped receiverStopped) {
    }

    @Override
    public void onBatchSubmitted(StreamingListenerBatchSubmitted batchSubmitted) {
    }

    @Override
    public void onBatchStarted(StreamingListenerBatchStarted batchStarted) {
    }

    @Override
    public void onBatchCompleted(StreamingListenerBatchCompleted batchCompleted) {
        ConsumerMetric metric = new ConsumerMetric(consumer.getConsumerId(), new Timestamp(System.currentTimeMillis()), Long.toString(batchCompleted.batchInfo().numRecords()), batchCompleted.batchInfo().totalDelay().get().toString());
        consumer.sendMetric(metric);
    }

    @Override
    public void onOutputOperationStarted(StreamingListenerOutputOperationStarted outputOperationStarted) {
    }

    @Override
    public void onOutputOperationCompleted(StreamingListenerOutputOperationCompleted outputOperationCompleted) {
    }
}
