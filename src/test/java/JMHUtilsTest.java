import org.example.acrastt.utils.JMHUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.samples.JMHSample_01_HelloWorld;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JMHUtilsTest {
    @Test
    void test() {
        JMHUtils.runWithJson("src/test/java/JMH.json", JMHSample_01_HelloWorld.class.getName());
        JMHUtils.runWithCsv("src/test/java/JMH.csv", JMHSample_01_HelloWorld.class.getName());
        File json = new File("src/test/java/JMH.json");
        File csv = new File("src/test/java/JMH.csv");
        assertTrue(json.exists());
        assertTrue(csv.exists());
        assertTrue(json.delete());
        assertTrue(csv.delete());
    }
}