package tn.lipsic.babel;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class TestNumericByteIterator {

    @Test
    public void testLong() throws Exception {
        NumericByteIterator it = new NumericByteIterator(42L);
        assertFalse(it.isFloatingPoint());
        assertEquals(42L, it.getLong());

        try {
            it.getDouble();
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
        }
        try {
            it.next();
            fail("Expected UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
        }

        assertEquals(8, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(7, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(6, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(5, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(4, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(3, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(2, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(1, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 42, (byte) it.nextByte());
        assertEquals(0, it.bytesLeft());
        assertFalse(it.hasNext());

        it.reset();
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
    }

    @Test
    public void testDouble() throws Exception {
        NumericByteIterator it = new NumericByteIterator(42.75);
        assertTrue(it.isFloatingPoint());
        assertEquals(42.75, it.getDouble(), 0.001);

        try {
            it.getLong();
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
        }
        try {
            it.next();
            fail("Expected UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
        }

        assertEquals(8, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 64, (byte) it.nextByte());
        assertEquals(7, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 69, (byte) it.nextByte());
        assertEquals(6, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 96, (byte) it.nextByte());
        assertEquals(5, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(4, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(3, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(2, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(1, it.bytesLeft());
        assertTrue(it.hasNext());
        assertEquals((byte) 0, (byte) it.nextByte());
        assertEquals(0, it.bytesLeft());
        assertFalse(it.hasNext());

        it.reset();
        assertTrue(it.hasNext());
        assertEquals((byte) 64, (byte) it.nextByte());
    }
}
