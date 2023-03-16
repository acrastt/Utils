# Utils

This is a collection of different utility functions that can be used in
various applications. This repository contains the following utilities.

- `ConcurrentList`: A thread-safe list that allows concurrent operations
  without error.
  This is designed for a high number of reads,
  a low number of writes and a low number of threads.
- `JMHBuilderFactory`: A utility for JMH(Java Microbenchmark Harness), currently it has
  functions that creates json/csv file when running benchmarks and running with GC profiler.
  It also can generate `ChainedOptionsBuilder` for different needs.
- `NumberUtils`: A utility which processes numbers in Java, it currently has
  a function that converts numbers to string with ordinals.

## Getting Started

To use this library, simply clone the repository using a tool such as GitHub CLI
and either add necessary files to your application's source code or
add this to your maven repositories folder (e.g. `.m2` folder).
Each utility has a separate file and can be used independently.

### Requirements

- JDK(Java Development Kit) 14 or later^
- Apache Maven 3.2.5 or later^

  ^: This is the minimum requirement, but you should consider an LTS version.

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

JMHUtils had self-explanation methods, so just find methods that
match your needs.
Here are some examples:

```java
import org.example.acrastt.utils.JMHBuilderFactory;

public class Main {
    public static void main(String[] args) {
        JMHBuilderFactory.runWithCsv("foo.csv", "yourClassName");
        JMHBuilderFactory.runWithJson("bar.json", "yourClassName");
        JMHBuilderFactory.runWithGC("yourClassName");
        JMHBuilderFactory.runWithCSVAndGC("baz.csv", "yourClassName");
        // etc...
    }
}
```

### NumberUtils

NumberUtils is a utility class for numbers. Here are some examples:

```java
import org.example.acrastt.utils.NumberUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {

    public static void main(String[] args) {
        NumberUtils.convertToOrdinal(1); // 1st
        NumberUtils.convertToOrdinal(2); // 2nd
        NumberUtils.convertToOrdinal(3); // 3rd
        NumberUtils.convertToOrdinal(4); // 4th
        NumberUtils.convertToOrdinal(5); // 5th
        // etc...
    }
}
```

## Documentation

You can find the documentation [here](javadoc/index.html).

## Contributing

If you want to contribute, please email us at `bohandu@hotmail.com`.
If you find any bugs or suggestions, feel free to submit an issue or pull request.

## License

This project is licensed under the [MIT License](LICENSE.txt)