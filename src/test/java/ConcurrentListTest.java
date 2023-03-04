import org.example.acrastt.utils.ConcurrentList;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcurrentListTest {

    Logger log = LoggerFactory.getLogger(ConcurrentListTest.class);

    @Test
    void test() {
        ConcurrentList<String> list = new ConcurrentList<>();
        ExecutorService exec = Executors.newFixedThreadPool(10);
        try {
            for (int i = 0; i < 10; i++) {
                exec.execute(() -> {
                    for (int j = 0; j < 10; j++) {
                        list.add(String.valueOf(j));
                    }
                });
            }
            exec.shutdown();
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            exec.shutdownNow();
        }
        List<String> expected = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                expected.add(String.valueOf(j));
            }
        }
        // We don't need to test other methods of the list because it invokes ArrayList methods.
        assertEquals(expected.toString(), list.toString());
        assertEquals(100, list.size());
    }
}