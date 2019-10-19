package tn.lipsic.babel.generator;

import org.testng.annotations.Test;

import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class TestIncrementingPrintableStringGenerator {
    private final static int[] ATOC = new int[]{65, 66, 67};

    @Test
    public void rolloverOK() throws Exception {
        final IncrementingPrintableStringGenerator gen =
                new IncrementingPrintableStringGenerator(2, ATOC);

        assertNull(gen.lastValue());
        assertEquals(gen.nextValue(), "AA");
        assertEquals(gen.lastValue(), "AA");
        assertEquals(gen.nextValue(), "AB");
        assertEquals(gen.lastValue(), "AB");
        assertEquals(gen.nextValue(), "AC");
        assertEquals(gen.lastValue(), "AC");
        assertEquals(gen.nextValue(), "BA");
        assertEquals(gen.lastValue(), "BA");
        assertEquals(gen.nextValue(), "BB");
        assertEquals(gen.lastValue(), "BB");
        assertEquals(gen.nextValue(), "BC");
        assertEquals(gen.lastValue(), "BC");
        assertEquals(gen.nextValue(), "CA");
        assertEquals(gen.lastValue(), "CA");
        assertEquals(gen.nextValue(), "CB");
        assertEquals(gen.lastValue(), "CB");
        assertEquals(gen.nextValue(), "CC");
        assertEquals(gen.lastValue(), "CC");
        assertEquals(gen.nextValue(), "AA"); // <-- rollover
        assertEquals(gen.lastValue(), "AA");
    }

    @Test
    public void rolloverOneCharacterOK() throws Exception {
        // It would be silly to create a generator with one character.
        final IncrementingPrintableStringGenerator gen =
                new IncrementingPrintableStringGenerator(2, new int[]{65});
        for (int i = 0; i < 5; i++) {
            assertEquals(gen.nextValue(), "AA");
        }
    }

    @Test
    public void rolloverException() throws Exception {
        final IncrementingPrintableStringGenerator gen =
                new IncrementingPrintableStringGenerator(2, ATOC);
        gen.setThrowExceptionOnRollover(true);

        int i = 0;
        try {
            while (i < 11) {
                ++i;
                gen.nextValue();
            }
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertEquals(i, 10);
        }
    }

    @Test
    public void rolloverOneCharacterException() throws Exception {
        // It would be silly to create a generator with one character.
        final IncrementingPrintableStringGenerator gen =
                new IncrementingPrintableStringGenerator(2, new int[]{65});
        gen.setThrowExceptionOnRollover(true);

        int i = 0;
        try {
            while (i < 3) {
                ++i;
                gen.nextValue();
            }
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertEquals(i, 2);
        }
    }

    @Test
    public void invalidLengths() throws Exception {
        try {
            new IncrementingPrintableStringGenerator(0, ATOC);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new IncrementingPrintableStringGenerator(-42, ATOC);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void invalidCharacterSets() throws Exception {
        try {
            new IncrementingPrintableStringGenerator(2, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new IncrementingPrintableStringGenerator(2, new int[]{});
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }
}
