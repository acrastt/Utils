package org.example.acrastt.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class ConcurrentListTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentListTest.class);
    private static final Object obj = new Object();
    private static ConcurrentList<String> stringList;
    private static ExecutorService exec;


    @BeforeAll
    static void initialize() {
        stringList = new ConcurrentList<>();
    }

    @BeforeEach
    void setup() {
        // Initialize the string list and the executor
        stringList.clear();
        exec = Executors.newCachedThreadPool();
    }

    @RepeatedTest(10)
    void testConcurrency() {
        // Initialize both lists
        ConcurrentList<String> concurrentList = new ConcurrentList<>();
        ConcurrentLinkedDeque<String> threadSafe = new ConcurrentLinkedDeque<>();
        concurrentList.add("Foo");
        concurrentList.add("Bar");
        concurrentList.add("Baz");
        threadSafe.add("Foo");
        threadSafe.add("Bar");
        threadSafe.add("Baz");
        // Check thread safety by adding and removing
        Thread remove = new Thread(() -> {
            while (concurrentList.size() < 50 && threadSafe.size() < 50) ;
            for (int i = 0; i < 100; i++) {
                concurrentList.remove(0);
                threadSafe.removeFirst();
                Thread.yield();
            }
        });
        Thread add = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                concurrentList.add("Qux");
                threadSafe.add("Qux");
            }
        });
        add.start();
        remove.start();
        try {
            add.join();
            remove.join();
        } catch (InterruptedException e) {
            LOG.error("Thread interrupted: ", e);
        }
        // Compare the size of both lists
        assertEquals(concurrentList.size(), threadSafe.size());
    }

    // Other methods--------------------------------------------------
    @RepeatedTest(100)
    void testAddAndGet() {
        stringList.add("hello");
        assertEquals("hello", stringList.get(0));
    }

    @RepeatedTest(100)
    void testIsEmpty() {
        assertTrue(stringList.isEmpty());
    }

    @RepeatedTest(100)
    void testContains() {
        assertFalse(stringList.contains("hello"));
    }

    @RepeatedTest(100)
    void testSize() {
        assertEquals(0, stringList.size());
    }

    @RepeatedTest(100)
    void testRemove() {
        stringList.add("hello");
        stringList.add("world");
        assertFalse(stringList.remove("foo"));
    }

    @RepeatedTest(100)
    void testClear() {
        stringList.add("hello");
        stringList.add("world");
        stringList.clear();
        assertTrue(stringList.isEmpty());
    }

    @RepeatedTest(100)
    void testAddAtPosition() {
        stringList.add("hello");
        stringList.add("world");
        stringList.add(1, "there");
        assertEquals("hello", stringList.get(0));
    }

    @RepeatedTest(100)
    void testIndexOf() {
        stringList.add("hello");
        stringList.add("world");
        assertEquals(0, stringList.indexOf("hello"));
    }

    @RepeatedTest(100)
    void testLastIndexOf() {
        stringList.addAll(List.of("hello", "world", "hello"));
        assertEquals(2, stringList.lastIndexOf("hello"));
    }

    @RepeatedTest(100)
    void testSubList() {
        stringList.addAll(List.of("hello", "world", "there"));
        assertEquals(2, stringList.subList(1, 3).size());
    }

    @RepeatedTest(100)
    void testAddAll() {
        List<String> newList = new ArrayList<>();
        newList.add("hello");
        newList.add("world");
        stringList.addAll(newList);
        assertEquals("hello", stringList.get(0));
    }

    @RepeatedTest(100)
    void testIterator() {
        stringList.addAll(List.of("hello", "world", "there"));
        Iterator<String> iterator = stringList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("hello", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("world", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("there", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @RepeatedTest(100)
    void testEquals() {
        ArrayList<String> copy = new ArrayList<>(stringList);
        assertEquals(stringList, copy);
    }

    @RepeatedTest(100)
    void testHashCode() {
        assertDoesNotThrow(stringList::hashCode);
    }

    @RepeatedTest(100)
    void testTrimToSize() {
        assertDoesNotThrow(stringList::trimToSize);
    }

    @RepeatedTest(100)
    void testEnsureCapacity() {
        assertDoesNotThrow(() -> stringList.ensureCapacity(Integer.MIN_VALUE));
    }

    @RepeatedTest(100)
    void testToStringAndToArray() {
        assertEquals(Arrays.toString(stringList.toArray()), stringList.toString());
    }

    @RepeatedTest(100)
    void testToArrayWithArguments() {
        assertEquals(Arrays.toString(stringList.toArray(new String[0])), stringList.toString());
    }

    @RepeatedTest(100)
    void testContainsAll() {
        assertTrue(stringList.containsAll(new ArrayList<>(stringList)));
    }

    @RepeatedTest(100)
    void testSetWithTwoParameters() {
        stringList.add("hello");
        stringList.set(stringList.size() - 1, "hello world");
        assertEquals("hello world", stringList.get(stringList.size() - 1));
    }

    @RepeatedTest(100)
    void testCopyConstructor() {
        assertEquals(new ArrayList<>(List.of("hello", "world")), new ConcurrentList<>(List.of("hello", "world")));
    }

    @RepeatedTest(100)
    void testCapacityConstructor() {
        assertDoesNotThrow(() -> new ConcurrentList<String>(100));
    }

    @RepeatedTest(100)
    void removeAt() {
        stringList.add("hello");
        stringList.add("world");
        stringList.remove(0);
        assertEquals(1, stringList.size());
    }

    @RepeatedTest(100)
    void testRemoveRange() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.removeRange(0, 2);
        assertEquals(1, stringList.size());
    }

    @RepeatedTest(100)
    void testForEach() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.forEach(s -> assertTrue(s.equals("hello") || s.equals("world") || s.equals("there")));
    }

    @RepeatedTest(100)
    void testToArrayWithIntFunction() {
        assertEquals(Arrays.toString(stringList.toArray()), Arrays.toString(stringList.toArray(String[]::new)));
    }

    @RepeatedTest(100)
    void testRemoveAll() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.removeAll(List.of("hello", "world", "there"));
        assertEquals(0, stringList.size());
    }

    @RepeatedTest(100)
    void testRemoveIf() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.removeIf(s -> true);
        assertEquals(0, stringList.size());
    }

    @RepeatedTest(100)
    void testRetainAll() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.retainAll(List.of(""));
        assertEquals(0, stringList.size());
    }

    @RepeatedTest(100)
    void testReplaceAll() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.replaceAll(s -> s);
        assertEquals(List.of("hello", "world", "there"), stringList);
    }

    @RepeatedTest(100)
    void testSort() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.sort(String.CASE_INSENSITIVE_ORDER);
        String[] array = stringList.toArray(new String[0]);
        Arrays.sort(array);
        assertEquals(List.of(array), stringList);
    }

    @RepeatedTest(100)
    void testListIterator() {
        assertDoesNotThrow(() -> stringList.listIterator());
    }

    @RepeatedTest(100)
    void testListIteratorAt() {
        assertDoesNotThrow(() -> stringList.listIterator(0));
    }

    @RepeatedTest(100)
    void testSpliterator() {
        assertDoesNotThrow(() -> stringList.listIterator());
    }

    @RepeatedTest(100)
    void testStream() {
        assertDoesNotThrow(() -> stringList.stream());
    }

    @RepeatedTest(100)
    void testParallelStream() {
        assertDoesNotThrow(() -> stringList.parallelStream());
    }

    @RepeatedTest(100)
    void testAddAllWithTwoArguments() {
        stringList.addAll(0, List.of("hello", "world", "there"));
        assertEquals(List.of("hello", "world", "there"), stringList);
    }

    @AfterEach
    void tearDown() {
        // Close the executor
        exec.shutdownNow();
    }
}