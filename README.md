# Utils

This is a collection of different utility functions that can be used in
various applications. This repository contains the following utilities.

- `ConcurrentList`: A thread-safe list that allows concurrent operations
  without error. This is designed for high number of reads,
  low number of writes and low number of threads.
- `JMHUtils`: A utility for JMH(Java Microbenchmark Harness), currently it has
  functions that creates json/csv file when running benchmarks.
- `NumberUtils`: A utility that processes numbers in Java, it currently has
  a function that converts numbers to string with ordinals.
- `SerializableObject`: SerializableObject is a class that is serializable,
  it can be used for testing purposes.
- `Benchmark`: How dare you?

## Getting Started

To use this library, simply clone the repository using a tool such as GitHub CLI
and add necessary files to your application's source code. Each utility
is has a separate file and can be used independently.

### Requirements

- JDK(Java Development Kit) 8 or higher, but it's recommended to use JDK
  11 or higher.

## Usage

### ConcurrentList

Since ConcurrentList is an implementation of ArrayList, just use it
like a normal ArrayList. There is an example:

```java
import org.example.acrastt.utils.ConcurrentList;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ConcurrentList<>();
        list.add("foo");
        list.add("bar");
        list.add("baz");
        list.remove("baz");
        list.get(0);
        // etc...
    }
}
```

### JMHUtils

JMHUtils is had self-explanation methods, so just find methods that
match your needs.Here are some examples:

```java
import org.example.acrastt.utils.JMHUtils;

public class Main {
    public static void main(String[] args) {
        JMHUtils.runWithCsv("csv.csv", "yourClassName");
        JMHUtils.runWithJson("json.json", "yourClassName");
        // etc...
    }
}
```