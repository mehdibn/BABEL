package tn.lip2.bdbench.generator;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertFalse;


public class TestZipfianGenerator {
    @Test
    public void testMinAndMaxParameter() {
        long min = 5;
        long max = 10;
        ZipfianGenerator zipfian = new ZipfianGenerator(min, max);

        for (int i = 0; i < 10000; i++) {
            long rnd = zipfian.nextValue();
            assertFalse(rnd < min);
            assertFalse(rnd > max);
        }

    }
}
