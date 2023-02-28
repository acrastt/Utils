import org.example.acrastt.utils.ConcurrentList;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ConcurrentListTest {

    Logger log = LoggerFactory.getLogger(ConcurrentListTest.class);

    @RepeatedTest(5)
    void test() {
        ConcurrentList<String> list = new ConcurrentList<>();
        try (ExecutorService exec = Executors.newFixedThreadPool(10)) {
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
        }
        // We don't need to test other methods of the list because it invokes ArrayList methods.
        assert list.toString().equals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assert list.size() == 100;
    }
}