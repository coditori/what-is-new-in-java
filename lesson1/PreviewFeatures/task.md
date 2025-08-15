Java 24: Preview and Incubator features (one place)

These are features you can try locally, but they require `--enable-preview` (and sometimes `--add-modules`). Keep it simple: read the snippets below and try them in your own scratch file if you want.

- Scoped Values (JEP 487):
```java
// Requires --enable-preview
// ScopedValue<Integer> sv = ScopedValue.newInstance();
```

- Primitive Types in Patterns (JEP 488):
```java
// Requires --enable-preview
// switch (x) { case int i -> System.out.println(i); }
```

- Flexible Constructor Bodies (JEP 492):
```java
// Requires --enable-preview
```

- Module Import Declarations (JEP 494):
```java
// Requires --enable-preview
// import module java.base;
```

- Simple Source Files & instance main (JEP 495):
```java
// Requires --enable-preview
```

- Vector API (JEP 489):
```java
// Requires --enable-preview --add-modules jdk.incubator.vector
```

- Structured Concurrency (JEP 499):
```java
// Requires --enable-preview
```

- Key Derivation Function API (JEP 478):
```java
// Requires --enable-preview
```

Tip: Use the latest JDK 24 and run with preview flags in your IDE run configuration. 