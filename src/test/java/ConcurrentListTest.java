import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.example.acrastt.utils.ConcurrentList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class ConcurrentListTest {

  private ConcurrentList<Integer> actual;

  private ConcurrentList<String> stringList;

  private ExecutorService exec;

  @BeforeEach
  public void setup() {
    // Initialize the actual list and the executor
    actual = new ConcurrentList<>();
    stringList = new ConcurrentList<>();
    exec = Executors.newCachedThreadPool();
  }

  @RepeatedTest(10)
  void testConcurrency() throws InterruptedException {
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

  @Test
  public void testAddAndGet() {
    stringList.add("hello");
    assertEquals("hello", stringList.get(0));
    stringList.add("world");
    assertEquals("world", stringList.get(1));
  }

  @Test
  public void testIsEmpty() {
    assertTrue(stringList.isEmpty());
    stringList.add("hello");
    assertFalse(stringList.isEmpty());
  }

  @Test
  public void testContains() {
    assertFalse(stringList.contains("hello"));
    stringList.add("hello");
    assertTrue(stringList.contains("hello"));
    assertFalse(stringList.contains("world"));
  }

  @Test
  public void testSize() {
    assertEquals(0, stringList.size());
    stringList.add("hello");
    assertEquals(1, stringList.size());
    stringList.add("world");
    assertEquals(2, stringList.size());
  }

  @Test
  public void testRemove() {
    stringList.add("hello");
    stringList.add("world");
    assertFalse(stringList.remove("foo"));
    assertTrue(stringList.remove("hello"));
    assertEquals("world", stringList.get(0));
    assertEquals(1, stringList.size());
  }

  @Test
  public void testClear() {
    stringList.add("hello");
    stringList.add("world");
    stringList.clear();
    assertTrue(stringList.isEmpty());
    assertEquals(0, stringList.size());
  }

  @Test
  public void testAddAtPosition() {
    stringList.add("hello");
    stringList.add("world");
    stringList.add(1, "there");
    assertEquals("hello", stringList.get(0));
    assertEquals("there", stringList.get(1));
    assertEquals("world", stringList.get(2));
  }

  @Test
  public void testIndexOf() {
    stringList.add("hello");
    stringList.add("world");
    assertEquals(0, stringList.indexOf("hello"));
    assertEquals(1, stringList.indexOf("world"));
    assertEquals(-1, stringList.indexOf("there"));
  }

  @Test
  public void testLastIndexOf() {
    stringList.addAll(List.of("hello", "world", "hello"));
    assertEquals(2, stringList.lastIndexOf("hello"));
    assertEquals(1, stringList.lastIndexOf("world"));
    assertEquals(-1, stringList.lastIndexOf("there"));
  }

  @Test
  public void testSubList() {
    stringList.addAll(List.of("hello", "world", "there"));
    assertEquals(2, stringList.subList(1, 3).size());
    assertEquals("world", stringList.subList(1, 3).get(0));
    assertEquals("there", stringList.subList(1, 3).get(1));
  }

  @Test
  public void testAddAll() {
    List<String> newList = new ArrayList<>();
    newList.add("hello");
    newList.add("world");
    stringList.addAll(newList);
    assertEquals("hello", stringList.get(0));
    assertEquals("world", stringList.get(1));
    assertEquals(2, stringList.size());
  }

  @Test
  public void testIterator() {
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
    assertEquals(new ArrayList<>(List.of("hello", "world")),
        new ConcurrentList<>(List.of("hello", "world")));
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
    stringList.forEach(
        s -> assertTrue(s.equals("hello") || s.equals("world") || s.equals("there")));
  }

  @Test
  void testToArrayWithIntFunction() {
    assertEquals(Arrays.toString(stringList.toArray()),
        Arrays.toString(stringList.toArray(String[]::new)));
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

  }

  @AfterEach
  void tearDown() {
    // Close the executor
    exec.shutdownNow();
  }
}
