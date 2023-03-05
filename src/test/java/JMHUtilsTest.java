import org.example.acrastt.utils.JMHUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.samples.JMHSample_01_HelloWorld;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

public class JMHUtilsTest {
    @Test
    void test() {
        // Runs HelloWorld benchmark with JSON and CSV
        JMHUtils.runWithJson("src/test/java/JMH.json",
                JMHSample_01_HelloWorld.class.getName());
        JMHUtils.runWithCSV("src/test/java/JMH.csv",
                JMHSample_01_HelloWorld.class.getName());
        // Assert that the files exist and delete them
        assertTrue(new File("src/test/java/JMH.json").delete());
        assertTrue(new File("src/test/java/JMH.csv").delete());
    }
}
