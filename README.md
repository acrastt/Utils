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
- `SerializableObject`: SerializableObject is a class which is serializable,
  it can be used for testing purposes.
- `Benchmark`: How dare you?

## Getting Started

To use this library, simply clone the repository using a tool such as GitHub CLI
and add necessary files to your application's source code. Each utility
has a separate file and can be used independently.

### Requirements

- JDK(Java Development Kit) 14 or higher.
- Apache Maven 3.2.5 or higher

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
match your needs.Here are some examples:

```java
import org.example.acrastt.utils.JMHUtils;

public class Main {
    public static void main(String[] args) {
        JMHUtils.runWithCsv("foo.csv", "yourClassName");
        JMHUtils.runWithJson("bar.json", "yourClassName");
        // etc...
    }
}
```

### NumberUtils

NumberUtils is a utility class for numbers. Here are some examples:

```java
import org.example.acrastt.utils.NumberUtils;

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

### SerializableObject

SerializableObject is a utility class for testing,
it is a serializable object that can be used in ObjectOutputStreams or others.
Here are some examples:

```java
import org.example.acrastt.utils.SerializableObject;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class Main {
  public static void main(String[] args) {
    SerializableObject obj = new SerializableObject();
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("yourFileName.txt"))) {
        out.writeObject(obj);
    }
  }
}
```

## Documentation

You can find the documentation at [here](javadoc/index.html)

## Contributing

If you want to contribute, please email us at `bohandu@hotmail.com`.
If you find any bugs or suggestions, feel free to submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE.txt)