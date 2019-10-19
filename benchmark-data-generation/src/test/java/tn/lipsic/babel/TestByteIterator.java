package tn.lipsic.babel;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class TestByteIterator {
    @Test
    public void testRandomByteIterator() {
        int size = 100;
        ByteIterator itor = new RandomByteIterator(size);
        assertTrue(itor.hasNext());
        assertEquals(size, itor.bytesLeft());
        assertEquals(size, itor.toString().getBytes().length);
        assertFalse(itor.hasNext());
        assertEquals(0, itor.bytesLeft());

        itor = new RandomByteIterator(size);
        assertEquals(size, itor.toArray().length);
        assertFalse(itor.hasNext());
        assertEquals(0, itor.bytesLeft());
    }
}
