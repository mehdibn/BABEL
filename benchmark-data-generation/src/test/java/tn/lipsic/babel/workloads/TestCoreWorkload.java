package tn.lipsic.babel.workloads;

import org.testng.annotations.Test;
import tn.lipsic.babel.generator.DiscreteGenerator;

import java.util.Properties;

import static org.testng.Assert.assertTrue;

public class TestCoreWorkload {

    @Test
    public void createOperationChooser() {
        final Properties p = new Properties();
        p.setProperty(CoreWorkload.READ_PROPORTION_PROPERTY, "0.20");
        p.setProperty(CoreWorkload.UPDATE_PROPORTION_PROPERTY, "0.20");
        p.setProperty(CoreWorkload.INSERT_PROPORTION_PROPERTY, "0.20");
        p.setProperty(CoreWorkload.SCAN_PROPORTION_PROPERTY, "0.20");
        p.setProperty(CoreWorkload.READMODIFYWRITE_PROPORTION_PROPERTY, "0.20");
        final DiscreteGenerator generator = CoreWorkload.createOperationGenerator(p);
        final int[] counts = new int[5];

        for (int i = 0; i < 100; ++i) {
            switch (generator.nextString()) {
                case "READ":
                    ++counts[0];
                    break;
                case "UPDATE":
                    ++counts[1];
                    break;
                case "INSERT":
                    ++counts[2];
                    break;
                case "SCAN":
                    ++counts[3];
                    break;
                default:
                    ++counts[4];
            }
        }

        for (int i : counts) {
            // Doesn't do a wonderful job of equal distribution, but in a hundred, if we
            // don't see at least one of each operation then the generator is really broke.
            assertTrue(i > 1);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createOperationChooserNullProperties() {
        CoreWorkload.createOperationGenerator(null);
    }
}