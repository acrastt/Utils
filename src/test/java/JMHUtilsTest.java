import org.example.acrastt.utils.JMHUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.samples.JMHSample_01_HelloWorld;

import java.io.File;

public class JMHUtilsTest {
    @Test
    void test() {
        JMHUtils.runWithJson("src/test/java/JMH.json",
                JMHSample_01_HelloWorld.class.getName());
        JMHUtils.runWithCSV("src/test/java/JMH.csv",
                JMHSample_01_HelloWorld.class.getName());
        assert new File("src/test/java/JMH.json").delete();
        assert new File("src/test/java/JMH.csv").delete();
    }
}
