package tn.lip2.bdbench.measurements.exporter;

import tn.lip2.bdbench.generator.ZipfianGenerator;
import tn.lip2.bdbench.measurements.Measurements;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestMeasurementsExporter {
    @Test
    public void testJSONArrayMeasurementsExporter() throws IOException {
        Properties props = new Properties();
        props.put(Measurements.MEASUREMENT_TYPE_PROPERTY, "histogram");
        Measurements mm = new Measurements(props);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONArrayMeasurementsExporter export = new JSONArrayMeasurementsExporter(out);

        long min = 5000;
        long max = 100000;
        ZipfianGenerator zipfian = new ZipfianGenerator(min, max);
        for (int i = 0; i < 1000; i++) {
            int rnd = zipfian.nextValue().intValue();
            mm.measure("UPDATE", rnd);
        }
        mm.exportMeasurements(export);
        export.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode  json = mapper.readTree(out.toString("UTF-8"));
        assertTrue(json.isArray());
        assertEquals(json.get(0).get("measurement").asText(), "Operations");
        assertEquals(json.get(4).get("measurement").asText(), "MaxLatency(us)");
        assertEquals(json.get(11).get("measurement").asText(), "4");
    }
}
