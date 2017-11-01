package tn.lip2.bdbench;

/**
 * Could not create the specified DB.
 */
public class UnknownDBException extends Exception {
  /**
   *
   */
  private static final long serialVersionUID = 459099842269616836L;

  public UnknownDBException(String message) {
    super(message);
  }

  public UnknownDBException() {
    super();
  }

  public UnknownDBException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnknownDBException(Throwable cause) {
    super(cause);
  }

}
