package tn.lip2.integration.comnsumers;

import tn.lip2.bdbench.Client;
import tn.lip2.bdbench.adapters.GenericConsumer;
import tn.lip2.bdbench.adapters.models.ConsumerMetric;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

public class ConsumerTest extends GenericConsumer {

    public static void main(String[] args) throws IOException {

        ConsumerTest t = new ConsumerTest();
        Properties props = Client.parseArguments(args);
        t.setProperties(props);
        t.init();
        for (int i = 0; i < 10; i++) {
            ConsumerMetric metric = new ConsumerMetric(new Timestamp(System.currentTimeMillis()), Integer.toString(i), "1", "1", "1");
            System.out.println(metric.toJson());
            t.sendMetric(metric);
        }
        t.close();

    }
}
