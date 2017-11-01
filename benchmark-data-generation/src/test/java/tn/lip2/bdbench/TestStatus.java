package tn.lip2.bdbench;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Test class for {@link Status}.
 */
public class TestStatus {

  @Test
  public void testAcceptableStatus() {
    assertTrue(Status.OK.isOk());
    assertTrue(Status.BATCHED_OK.isOk());
    assertFalse(Status.BAD_REQUEST.isOk());
    assertFalse(Status.ERROR.isOk());
    assertFalse(Status.FORBIDDEN.isOk());
    assertFalse(Status.NOT_FOUND.isOk());
    assertFalse(Status.NOT_IMPLEMENTED.isOk());
    assertFalse(Status.SERVICE_UNAVAILABLE.isOk());
    assertFalse(Status.UNEXPECTED_STATE.isOk());
  }
}
