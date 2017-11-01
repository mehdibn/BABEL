package tn.lip2.bdbench;

/**
 * Something bad happened while interacting with the database.
 */
public class DBException extends Exception {
  /**
   *
   */
  private static final long serialVersionUID = 6646883591588721475L;

  public DBException(String message) {
    super(message);
  }

  public DBException() {
    super();
  }

  public DBException(String message, Throwable cause) {
    super(message, cause);
  }

  public DBException(Throwable cause) {
    super(cause);
  }

}
