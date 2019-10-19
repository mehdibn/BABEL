package tn.lipsic.babel;

import org.apache.htrace.core.Tracer;
import tn.lipsic.babel.adapters.GenericProducer;

import java.util.Properties;

/**
 * Creates a GenericProducer layer by dynamically classloading the specified GenericProducer class.
 */
public final class DBFactory {
    private DBFactory() {
        // not used
    }

    public static GenericProducer newDB(String dbname, Properties properties, final Tracer tracer) throws UnknownDBException {
        ClassLoader classLoader = DBFactory.class.getClassLoader();

        GenericProducer ret;

        try {
            Class dbclass = classLoader.loadClass(dbname);

            ret = (GenericProducer) dbclass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ret.setProperties(properties);

        return new DBWrapper(ret, tracer);
    }

}
