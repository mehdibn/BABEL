package tn.lipsic.babel.generator;

import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Tests for the AcknowledgedCounterGenerator class.
 */
public class AcknowledgedCounterGeneratorTest {

    /**
     * Test that advancing past {@link Integer#MAX_VALUE} works.
     */
    @Test
    public void testIncrementPastIntegerMaxValue() {
        final long toTry = AcknowledgedCounterGenerator.WINDOW_SIZE * 3;

        AcknowledgedCounterGenerator generator =
                new AcknowledgedCounterGenerator(Integer.MAX_VALUE - 1000);

        Random rand = new Random(System.currentTimeMillis());
        BlockingQueue<Long> pending = new ArrayBlockingQueue<Long>(1000);
        for (long i = 0; i < toTry; ++i) {
            long value = generator.nextValue();

            while (!pending.offer(value)) {

                Long first = pending.poll();

                // Don't always advance by one.
                if (rand.nextBoolean()) {
                    generator.acknowledge(first);
                } else {
                    Long second = pending.poll();
                    pending.add(first);
                    generator.acknowledge(second);
                }
            }
        }

    }
}
