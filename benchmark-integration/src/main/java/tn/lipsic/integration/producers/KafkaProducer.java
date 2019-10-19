package tn.lipsic.integration.producers;

import tn.lipsic.babel.ByteIterator;
import tn.lipsic.babel.Client;
import tn.lipsic.babel.DBException;
import tn.lipsic.babel.Status;
import tn.lipsic.babel.adapters.GenericProducer;
import tn.lipsic.core.KafkaInjector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class KafkaProducer extends GenericProducer {

    private static KafkaInjector injector;

    public void init() throws DBException {
        injector = new KafkaInjector(getProperties().getProperty("SUTkafkabrokers"), getProperties().getProperty("SUTtopic"));
        injector.createOutput();
    }

    public void cleanup() throws DBException {
        injector.closeOutput();
    }

    @Override
    public Status read(String s, String s1, Set<String> set, Map<String, ByteIterator> map) {
        return null;
    }

    @Override
    public Status scan(String s, String s1, int i, Set<String> set, Vector<HashMap<String, ByteIterator>> vector) {
        return null;
    }

    @Override
    public Status update(String s, String s1, Map<String, ByteIterator> map) {
        return null;
    }

    @Override
    public Status insert(String s, String s1, Map<String, ByteIterator> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("InjectorID : " + getProperties().getProperty(Client.INJECTOR_ID) + " | ").append(" [ ");
        if (map != null) {
            for (Map.Entry<String, ByteIterator> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
            }
        }

        sb.append("]");
        injector.sendMessage(sb.toString());

        return Status.OK;
    }

    @Override
    public Status delete(String s, String s1) {
        return null;
    }
}
