import org.example.acrastt.utils.Benchmark;
import org.example.acrastt.utils.JMHUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

class JMHUtilsTest {
    @Test
    void test() {
        JMHUtils.runWithJson("src/test/java/JMH.json", Benchmark.class.getName());
        JMHUtils.runWithCsv("src/test/java/JMH.csv", Benchmark.class.getName());
        File json = new File("src/test/java/JMH.json");
        File csv = new File("src/test/java/JMH.csv");
        assert json.exists();
        assert csv.exists();
        assert json.delete();
        assert csv.delete();
    }
}