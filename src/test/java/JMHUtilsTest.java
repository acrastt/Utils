import org.example.acrastt.utils.JMHUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.samples.JMHSample_01_HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JMHUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(JMHUtilsTest.class);

    @Test
    void test() {
        // Runs HelloWorld benchmark with JSON and CSV
        JMHUtils.runWithJson("src/test/java/JMH.json", JMHSample_01_HelloWorld.class.getName());
        JMHUtils.runWithCSV("src/test/java/JMH.csv", JMHSample_01_HelloWorld.class.getName());
        assertTrue(Files.exists(new File("src/test/java/JMH.json").toPath()));
        assertTrue(Files.exists(new File("src/test/java/JMH.csv").toPath()));

        // Assert that the files exist and delete them
        try {
            Files.delete(new File("src/test/java/JMH.json").toPath());
            Files.delete(new File("src/test/java/JMH.csv").toPath());
        } catch (IOException e) {
            LOG.error("Error when deleting `src/test/java/JMH.json` " + "or `src/test/java/JMH.csv`", e);
        }
    }
}
