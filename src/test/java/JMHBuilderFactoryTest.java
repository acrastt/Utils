import org.example.acrastt.utils.JMHBuilderFactory;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.samples.JMHSample_01_HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JMHBuilderFactoryTest {

    private static final String JSON = "src/test/java/JMH.json";
    private static final String CSV = "src/test/java/JMH.csv";

    private static final Logger LOG = LoggerFactory.getLogger(JMHBuilderFactory.class);

    @Test
    void testJSON() {
        // Runs HelloWorld benchmark with JSON and CSV
        JMHBuilderFactory.runWithJson(JSON, JMHSample_01_HelloWorld.class.getName());
        assertTrue(Files.exists(new File(JSON).toPath()));

        // Assert that the files exist and delete them
        try {
            Files.delete(new File(JSON).toPath());
        } catch (IOException e) {
            LOG.error("Error when deleting '" + JSON + "'", e);
        }
    }

    @Test
    void testCSV() {
        JMHBuilderFactory.runWithCSV(CSV, JMHSample_01_HelloWorld.class.getName());
        assertTrue(Files.exists(new File(CSV).toPath()));
        try {
            Files.delete(new File(CSV).toPath());
        } catch (IOException e) {
            LOG.error("Error when deleting '" + CSV + "'", e);
        }
    }

    @Test
    void testGC() {
        assertDoesNotThrow(() -> JMHBuilderFactory.runWithGC(JMHSample_01_HelloWorld.class.getName()));
    }
}