# What is new in java
Here we are focusing Java 24 new features and compare it with Java 23. The idea is to make an intellij academy tutorial for the latest version of OpenJDK.

References:
- https://openjdk.org/projects/jdk/24/
- https://adoptium.net/en-GB/temurin/release-notes/?version=jdk-24.0.1+9
- https://www.oracle.com/java/technologies/javase/24-relnote-issues.html#JDK-8341566

Java 24 Features

Features (stable, testable)
- **Class-File API** (JEP 484) – A new java.lang.classfile API for parsing, generating and transforming Java class files. This replaces ad-hoc libraries (ASM, BCEL, etc.) with a standard API.

    ```java
    byte[] bytes = Files.readAllBytes(Path.of("MyClass.class"));
    ClassFile cf = ClassFile.of();
    ClassModel model = cf.parse(bytes);
    System.out.println(model.thisClass().asString());
    ```

    (Requires Java 24; no flags needed.)

- **Stream Gatherers** (JEP 485) – Extends the Stream API with a new Stream.gather(...) intermediate operation that uses a Gatherer to implement custom streaming transforms. For example, you can slide or batch elements:

    ```java
    Stream.of(1, 2, 3, 4)
        .gather(Gatherers.windowFixed(2))
        .forEach(System.out::println);
    ```

    (Requires Java 24; no flags needed.)

- **Ahead-of-Time Class Loading & Linking** (JEP 483) – Improves startup by caching loaded+linked classes. First run the app with recording flags to build an AOT cache, then use it on subsequent runs for faster startup. Example:

    ```bash
    # First run to record class usage:
    java -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf -cp app.jar com.example.Main
    # Create the AOT cache:
    java -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf -XX:AOTCache=app.aot
    # Run with the cache enabled:
    java -XX:AOTCache=app.aot -cp app.jar com.example.Main
    ```

    (Requires Java 24; use -XX:+UnlockExperimentalVMOptions if needed.)

- **Synchronize Virtual Threads without Pinning** (JEP 491) – Changes synchronized so that virtual threads no longer permanently “pin” their carrier threads. Virtual threads blocking in synchronized methods now release the OS thread, improving scalability. For example:

    ```java
    Thread.startVirtualThread(() -> {
        synchronized(obj) {
            System.out.println("running in synchronized block");
        }
    });
    ```

    (Use Java 24; no flags needed. This feature is automatic for virtual threads.)

- **Quantum-Resistant Key-Encapsulation** (JEP 496) – Implements NIST’s ML-KEM algorithm. Developers can now generate ML-KEM key pairs and perform key encapsulation. For example:

    ```java
    KeyPairGenerator gen = KeyPairGenerator.getInstance("ML-KEM-1024");
    KeyPair kp = gen.generateKeyPair();  // ML-KEM-1024 key pair
    ```

    (Add BouncyCastle or similar provider if needed for test harness.)

- **Quantum-Resistant Signature** (JEP 497) – Implements NIST’s ML-DSA algorithm. You can sign and verify with “ML-DSA”. For example:

    ```java
    Signature sig = Signature.getInstance("ML-DSA");
    sig.initSign(privateKey);
    sig.update(data);
    byte[] signature = sig.sign();
    ```

    (Use Java 24; no special flags. Signing with an ML-DSA key.)

- **Warn upon Use of Memory-Access Methods in sun.misc.Unsafe** (JEP 498) – Issues a warning at run time on the first occasion that any memory-access method in sun.misc.Unsafe is invoked

    ```java
    var f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
    f.setAccessible(true);
    var unsafe = (sun.misc.Unsafe) f.get(null);
    unsafe.getInt(0L); // Triggers a warning in Java 24
    ```

Experiments (preview/incubator features)
- **Scoped Values** (Fourth Preview, JEP 487) – A preview API (ScopedValue) for one-way sharing of immutable data from a caller to its callees (including child threads). It’s similar to ThreadLocal but immutable and scoped. Example:

    ```java
    ScopedValue<Integer> sv = ScopedValue.newInstance();
    ScopedValue.where(sv, 42).run(() -> {
        System.out.println(sv.get());  // prints 42
    });
    ```

    (Compile/run with --enable-preview.)

- **Primitive Types in Patterns** (Second Preview, JEP 488) – Preview for using primitives in pattern matching, instanceof, and switch. For example, you can replace a default in a switch with a primitive pattern:

    ```diff
    switch (x.getStatus()) {
        case 0 -> "okay";
        case 1 -> "warn";
        case 2 -> "error";
    -   default -> "unknown: " + x.getStatus();
    +   case int i -> "unknown: " + i;
    }
    ```

    (Compile/run with --enable-preview.)

- **Flexible Constructor Bodies** (Third Preview, JEP 492) – Preview that lets you write statements in a constructor before calling super(...) or this(...). Example change:

    ```diff
    public class PositiveBigInteger extends BigInteger {
        public PositiveBigInteger(long value) {
    -        super(verifyPositive(value));
    +        if (value <= 0) throw new IllegalArgumentException("…");
    +        super(value);
        }
    }
    ```

    (Compile/run with --enable-preview.)

- **Module Import Declarations** (Second Preview, JEP 494) – Preview that allows `import module <name>;` to pull in all packages of a module at once. For example:

    ```java
    import module java.base;  // imports all packages in java.base
    ```

    (Compile/run with --enable-preview.)

- **Simple Source Files & Instance main** (Fourth Preview, JEP 495) – Preview for beginner-friendly coding: you can omit public/class/static in simple programs. For example, change a normal main:

    ```diff
    public class HelloWorld {
    -    public static void main(String[] args) {
    -        System.out.println("Hello!");
    -    }
    +    void main() {
    +        println("Hello!");
    +    }
    }
    ```

    (Compile/run with --enable-preview.)

- **Vector API** (Ninth Incubator, JEP 489) – Incubating API for SIMD vector computations. For example:

    ```java
    import jdk.incubator.vector.IntVector;
    int[] a = {1, 2, 3, 4};
    IntVector v = IntVector.fromArray(IntVector.SPECIES_PREFERRED, a, 0);
    ```

    (Compile/run with --add-modules jdk.incubator.vector --enable-preview.)

- **Structured Concurrency** (Fourth Preview, JEP 499) – Preview for easier management of groups of tasks. It provides StructuredTaskScope to fork/join threads. Example:

    ```java
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        Future<String> u = scope.fork(() -> fetchUser());
        Future<Integer> o = scope.fork(() -> fetchOrder());
        scope.join().throwIfFailed();
        // Both succeeded; use u.result() and o.result()
    }
    ```

    (Compile/run with --enable-preview.)

- **Key Derivation Function API** (Preview, JEP 478) – Preview API (`javax.crypto.KDF`) for password-based and other key-derivation functions (e.g. Argon2). Example usage:

    ```java
    KDF kdf = KDF.getInstance("PBKDF2WithHmacSHA256");
    byte[] key = kdf.deriveKey("password".toCharArray(), salt);
    ```

    (Compile/run with --enable-preview.)

Other Features (OS/hardware or non-code changes)
- **Generational Shenandoah** (Experimental, JEP 404) – An experimental “generational” mode for the Shenandoah GC that splits the heap into young/old to improve throughput and memory usage.  
  (Enable with `-XX:+UnlockExperimentalVMOptions -XX:+UseGenerationalShenandoahGC`.)

- **Compact Object Headers** (Experimental, JEP 450) – Reduces HotSpot object header size to 8 bytes on 64-bit JVMs to save memory.  
  (Enable with `-XX:+UnlockExperimentalVMOptions -XX:+UseCompactObjectHeaders` on 64-bit OS.)

- **Linking without JMODs** (JEP 493) – Improves jlink to build run-time images without using the JDK’s jmod files, shrinking the JDK ~25%.  
  (Requires building a special JDK: `--enable-linkable-runtime`.)

- **Deprecate 32-bit x86 Port** (JEP 501) – Marks the Linux 32-bit x86 port as deprecated for removal.  
  (Building on 32-bit will error unless you pass `--enable-deprecated-ports=yes`.)

- **Remove Windows 32-bit Port** (JEP 479) – Deleted support for Windows 32-bit x86.  
  (No 32-bit Windows binaries in JDK 24.)

- **Permanently Disable Security Manager** (JEP 486) – Disallows enabling the Security Manager (via `-Djava.security.manager` or `System.setSecurityManager`), finalizing its removal.  
  (All SM APIs remain but have no effect.)

- **Restrict JNI Usage** (JEP 472) – Issues runtime warnings when using JNI or the FFM (Foreign Function & Memory) API, preparing for stricter defaults in future.  
  (Current behavior unchanged except warnings.)

- **Late Barrier Expansion for G1** (JEP 475) – Internal GC optimization for G1; no user-visible change.  
  (No action needed; just a performance improvement.)

- **ZGC Non-Generational Mode Removed** (JEP 490) – Removes the old non-generational mode of ZGC.  
  (The flag `-XX:-UseZGen` is removed; nothing to enable.)

Other small API enhancements that do not form part of any JEP:
- **Reader.of(CharSequence)** – Adds a factory method to get a Reader over any CharSequence (e.g. String or StringBuilder), avoiding unnecessary copying.

    ```java
    Reader reader = Reader.of("Hello");  // reads from a String directly
    ```

    (No special setup; use Java 24 or later.)

- **Process.waitFor(Duration)** – Overloads Process.waitFor(...) to accept a java.time.Duration instead of separate timeout and unit, simplifying waiting for process termination.

    ```java
    Process p = Runtime.getRuntime().exec("sleep 5");
    p.waitFor(Duration.ofSeconds(10));  // wait up to 10 seconds
    ```

    (No special setup.)

Each item above reflects new Java 24 functionality. Code examples show minimal changes to use the feature (see notes for required flags).

Sources: Official JDK 24 release notes and JEPs
