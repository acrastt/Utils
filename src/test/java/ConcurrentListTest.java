import org.example.acrastt.utils.ConcurrentList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcurrentListTest {
    private ConcurrentList<Integer> actual;
    private ExecutorService exec;

    @BeforeEach
    public void setup() {
        // Initialize the actual list and the executor
        actual = new ConcurrentList<>();
        exec = Executors.newCachedThreadPool();
    }

    @RepeatedTest(5)
    void test() throws InterruptedException {
        // Write integers concurrently
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            exec.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    actual.add(finalI);
                }
            });
        }
        // Wait for all modifications to complete
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        // Compare the results
        assertEquals(10000, actual.size());
    }

    @AfterEach
    void tearDown() {
        // Close the executor
        exec.shutdownNow();
    }
}
