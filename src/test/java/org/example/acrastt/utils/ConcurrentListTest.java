package org.example.acrastt.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
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

class ConcurrentListTest {

    private final Logger LOG = LoggerFactory.getLogger(ConcurrentListTest.class);
    private final Object obj = new Object();
    private ConcurrentList<String> stringList;
    private ExecutorService exec;

    @BeforeEach
    void setup() {
        // Initialize the string list and the executor
        stringList = new ConcurrentList<>();
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
            while (concurrentList.size() < 5000 && threadSafe.size() < 5000) ;
            for (int i = 0; i < 10000; i++) {
                concurrentList.remove(0);
                threadSafe.removeFirst();
                Thread.yield();
            }
        });
        Thread add = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
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
    @Test
    void testAddAndGet() {
        stringList.add("hello");
        assertEquals("hello", stringList.get(0));
        stringList.add("world");
        assertEquals("world", stringList.get(1));
    }

    @Test
    void testIsEmpty() {
        assertTrue(stringList.isEmpty());
        stringList.add("hello");
        assertFalse(stringList.isEmpty());
    }

    @Test
    void testContains() {
        assertFalse(stringList.contains("hello"));
        stringList.add("hello");
        assertTrue(stringList.contains("hello"));
        assertFalse(stringList.contains("world"));
    }

    @Test
    void testSize() {
        assertEquals(0, stringList.size());
        stringList.add("hello");
        assertEquals(1, stringList.size());
        stringList.add("world");
        assertEquals(2, stringList.size());
    }

    @Test
    void testRemove() {
        stringList.add("hello");
        stringList.add("world");
        assertFalse(stringList.remove("foo"));
        assertTrue(stringList.remove("hello"));
        assertEquals(1, stringList.size());
    }

    @Test
    void testClear() {
        stringList.add("hello");
        stringList.add("world");
        stringList.clear();
        assertTrue(stringList.isEmpty());
    }

    @Test
    void testAddAtPosition() {
        stringList.add("hello");
        stringList.add("world");
        stringList.add(1, "there");
        assertEquals("hello", stringList.get(0));
    }

    @Test
    void testIndexOf() {
        stringList.add("hello");
        stringList.add("world");
        assertEquals(0, stringList.indexOf("hello"));
    }

    @Test
    void testLastIndexOf() {
        stringList.addAll(List.of("hello", "world", "hello"));
        assertEquals(2, stringList.lastIndexOf("hello"));
    }

    @Test
    void testSubList() {
        stringList.addAll(List.of("hello", "world", "there"));
        assertEquals(2, stringList.subList(1, 3).size());
    }

    @Test
    void testAddAll() {
        List<String> newList = new ArrayList<>();
        newList.add("hello");
        newList.add("world");
        stringList.addAll(newList);
        assertEquals("hello", stringList.get(0));
        assertEquals("world", stringList.get(1));
        assertEquals(2, stringList.size());
    }

    @Test
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

    @Test
    void testEquals() {
        ArrayList<String> copy = new ArrayList<>(stringList);
        assertEquals(stringList, copy);
    }

    @Test
    void testHashCode() {
        assertDoesNotThrow(stringList::hashCode);
    }

    @Test
    void testTrimToSize() {
        assertDoesNotThrow(stringList::trimToSize);
    }

    @Test
    void testEnsureCapacity() {
        assertDoesNotThrow(() -> stringList.ensureCapacity(Integer.MIN_VALUE));
    }

    @Test
    void testToStringAndToArray() {
        assertEquals(Arrays.toString(stringList.toArray()), stringList.toString());
    }

    @Test
    void testToArrayWithArguments() {
        assertEquals(Arrays.toString(stringList.toArray(new String[0])), stringList.toString());
    }

    @Test
    void testContainsAll() {
        assertTrue(stringList.containsAll(new ArrayList<>(stringList)));
    }

    @Test
    void testSetWithTwoParameters() {
        stringList.add("hello");
        stringList.set(stringList.size() - 1, "hello world");
        assertEquals("hello world", stringList.get(stringList.size() - 1));
    }

    @Test
    void testCopyConstructor() {
        assertEquals(new ArrayList<>(List.of("hello", "world")), new ConcurrentList<>(List.of("hello", "world")));
    }

    @Test
    void testCapacityConstructor() {
        assertDoesNotThrow(() -> new ConcurrentList<String>(10));
    }

    @Test
    void removeAt() {
        stringList.add("hello");
        stringList.add("world");
        stringList.remove(0);
        assertEquals(1, stringList.size());
    }

    @Test
    void testRemoveRange() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.removeRange(0, 2);
        assertEquals(1, stringList.size());
    }

    @Test
    void testForEach() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.forEach(s -> assertTrue(s.equals("hello") || s.equals("world") || s.equals("there")));
    }

    @Test
    void testToArrayWithIntFunction() {
        assertEquals(Arrays.toString(stringList.toArray()), Arrays.toString(stringList.toArray(String[]::new)));
    }

    @Test
    void testRemoveAll() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.removeAll(List.of("hello", "world", "there"));
        assertEquals(0, stringList.size());
    }

    @Test
    void testRemoveIf() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.removeIf(s -> true);
        assertEquals(0, stringList.size());
    }

    @Test
    void testRetainAll() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.retainAll(List.of(""));
        assertEquals(0, stringList.size());
    }

    @Test
    void testReplaceAll() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.replaceAll(s -> s);
        assertEquals(List.of("hello", "world", "there"), stringList);
    }

    @Test
    void testSort() {
        stringList.addAll(List.of("hello", "world", "there"));
        stringList.sort(String.CASE_INSENSITIVE_ORDER);
        String[] array = stringList.toArray(new String[0]);
        Arrays.sort(array);
        assertEquals(List.of(array), stringList);
    }

    @Test
    void testListIterator() {
        assertDoesNotThrow(() -> stringList.listIterator());
    }

    @Test
    void testListIteratorAt() {
        assertDoesNotThrow(() -> stringList.listIterator(0));
    }

    @Test
    void testSpliterator() {
        assertDoesNotThrow(() -> stringList.listIterator());
    }

    @Test
    void testStream() {
        assertDoesNotThrow(() -> stringList.stream());
    }

    @Test
    void testParallelStream() {
        assertDoesNotThrow(() -> stringList.parallelStream());
    }

    @Test
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
