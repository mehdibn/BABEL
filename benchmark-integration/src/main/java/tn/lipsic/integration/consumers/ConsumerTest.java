package tn.lipsic.integration.consumers;

import tn.lipsic.babel.Client;
import tn.lipsic.babel.adapters.GenericConsumer;
import tn.lipsic.babel.adapters.models.ConsumerMetric;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

public class ConsumerTest extends GenericConsumer {

    public ConsumerTest(String consumerId) {
        super(consumerId);
    }

    public static void main(String[] args) throws IOException {

        ConsumerTest t = new ConsumerTest("test");
        Properties props = Client.parseArguments(args);
        t.setProperties(props);
        t.init();
        for (int i = 0; i < 10; i++) {
            ConsumerMetric metric = new ConsumerMetric("test", new Timestamp(System.currentTimeMillis()), Integer.toString(i), "1", "1", "1");
            System.out.println(metric.toJson());
            t.sendMetric(metric);
        }
        t.close();

    }
}
