package tn.lip2.bdbench;

import org.apache.htrace.core.Tracer;

import java.util.Properties;

/**
 * Creates a DB layer by dynamically classloading the specified DB class.
 */
public final class DBFactory {
  private DBFactory() {
    // not used
  }

  public static DB newDB(String dbname, Properties properties, final Tracer tracer) throws UnknownDBException {
    ClassLoader classLoader = DBFactory.class.getClassLoader();

    DB ret;

    try {
      Class dbclass = classLoader.loadClass(dbname);

      ret = (DB) dbclass.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    ret.setProperties(properties);

    return new DBWrapper(ret, tracer);
  }

}
