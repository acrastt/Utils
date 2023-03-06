import org.example.acrastt.utils.JMHUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.samples.JMHSample_01_HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class JMHUtilsTest {

    Logger LOG = LoggerFactory.getLogger(JMHUtilsTest.class);

    @Test
    void test() {
        // Runs HelloWorld benchmark with JSON and CSV
        JMHUtils.runWithJson("src/test/java/JMH.json",
                JMHSample_01_HelloWorld.class.getName());
        JMHUtils.runWithCSV("src/test/java/JMH.csv",
                JMHSample_01_HelloWorld.class.getName());
        // Assert that the files exist and delete them
        try {
            Files.delete(new File("src/test/java/JMH.json").toPath());
            Files.delete(new File("src/test/java/JMH.csv").toPath());
        } catch (FileNotFoundException e) {
            LOG.error("JMHUtils not working properly", e);
        } catch (IOException e) {
            LOG.error("Error when deleting `src/test/java/JMH.json` " +
                    "or `src/test/java/JMH.csv`", e);
        }

    }
}
