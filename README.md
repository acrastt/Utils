[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=bugs)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=coverage)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)\
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=acrastt_Utils&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=acrastt_Utils)
# Utils

This is a collection of different utility functions that can be used in
various applications. This repository contains the following utilities.

- `ConcurrentList`: A thread-safe list that allows concurrent operations
  without error.
  This is designed for a high number of reads,
  a low number of writes and a low number of threads.
- `JMHUtils`: A utility for JMH(Java Microbenchmark Harness), currently it has
  functions that creates json/csv file when running benchmarks.
- `NumberUtils`: A utility which processes numbers in Java, it currently has
  a function that converts numbers to string with ordinals.

## Getting Started

To use this library, simply clone the repository using a tool such as GitHub CLI
and either add necessary files to your application's source code or
add this to your `.m2` directory.
Each utility has a separate file and can be used independently.

### Requirements

- JDK(Java Development Kit) 11 or later^
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
import org.example.acrastt.utils.JMHUtils;

public class Main {
    public static void main(String[] args) {
        JMHUtils.runWithCsv("foo.csv", "yourClassName");
        JMHUtils.runWithJson("bar.json", "yourClassName");
        JMHUtils.runWithGC("yourClassName");
        JMHUtils.runWithCSVAndGC("baz.csv", "yourClassName");
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
    assertEquals("1st", NumberUtils.convertToOrdinal(1)); // 1st
    assertEquals("2nd", NumberUtils.convertToOrdinal(2)); // 2nd
    assertEquals("3rd", NumberUtils.convertToOrdinal(3)); // 3rd
    assertEquals("4th", NumberUtils.convertToOrdinal(4)); // 4th
    assertEquals("5th", NumberUtils.convertToOrdinal(5)); // 5th
    // etc...
  }
}
```

## Documentation

You can find the documentation [here](javadoc/index.html).

## Contributing

If you want to contribute, please email us at `bohandu@hotmail.com`.
If you find any bugs or suggestions, feel free to submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE.txt)