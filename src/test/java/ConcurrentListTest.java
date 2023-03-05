import org.example.acrastt.utils.ConcurrentList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcurrentListTest {
    private static final ArrayList<Integer> expected = new ArrayList<>();
    private static ConcurrentList<Integer> actual;
    private static ExecutorService exec;

    @BeforeEach
    public void setup() {
        // Initialize the expected list
        expected.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                expected.add(j);
            }
        }
        // Initialize the actual list and the executor
        actual = new ConcurrentList<>();
        exec = Executors.newCachedThreadPool();
    }

    @Test
    void test() throws InterruptedException {
        // Write integers concurrently
        for (int i = 0; i < 10; i++) {
            exec.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    actual.add(j);
                }
            });
        }
        // Wait for all modifications to complete
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        // Compare the results
        assertEquals(expected.toString(), actual.toString());
    }

    @AfterEach
    void tearDown() {
        // Close the executor
        exec.shutdownNow();
    }
}
