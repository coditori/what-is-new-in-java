Java 24: Future‑proof features (read‑only)

These are important platform changes that are hard to demonstrate in a tiny task. Read and remember:

- Ahead‑of‑Time Class Loading & Linking (JEP 483): improves startup via AOT caches. Use special flags when recording/creating caches.
- Quantum‑resistant Key‑Encapsulation (JEP 496): ML‑KEM algorithms. Needs crypto providers and setup to try.
- Quantum‑resistant Signatures (JEP 497): ML‑DSA algorithms. Needs proper keys/providers.
- Warn on sun.misc.Unsafe memory access (JEP 498): runtime warning on first use.
- GC and JDK build changes: Generational Shenandoah (JEP 404), Compact Object Headers (JEP 450), Linking without JMODs (JEP 493), Deprecations/Removals (JEP 501, 479), Security Manager permanently disabled (JEP 486), Restrict JNI usage warnings (JEP 472), ZGC non‑generational removed (JEP 490).

If you want to explore, see your README links to official JEPs and release notes. 