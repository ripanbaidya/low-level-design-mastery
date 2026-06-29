## Structural Design Pattern

<p align="right">Last updated - 29.06.2026</p>

## Introduction

The term **Structural Design Patterns** stems from the classic Gang of Four (GoF) reference manual, _Design Patterns: Elements of Reusable Object-Oriented Software_. While creational patterns manage the nuances of object instantiation and behavioral patterns dictate how objects interact, structural patterns target how components are physically assembled.

> **Core Philosophy:** Structural design patterns focus on how classes and objects are composed to form larger, more complex structures while ensuring the system remains flexible, efficient, and scalable.

Implementing these patterns allows you to:

- Assemble large object hierarchies using composition rather than rigid, deep inheritance layers.
- Bridge structural gaps between incompatible legacy systems and modern APIs.
- Extend component features dynamically at runtime without modifying source code.
- Optimize system memory footprint by sharing common object details.

## The 7 Structural Design Patterns

### 1. Adapter Pattern

- **Strategic Purpose:** Converts the interface of a class into another interface that the client expects. It enables classes with incompatible interfaces to collaborate seamlessly.
- **Real-World Analogy:** A standard travel wall adapter that acts as a middleman, enabling a foreign 110V plug to draw power safely from a domestic 220V power outlet.
- **Backend Application:** Wrapping an outdated, legacy library inside a clean, modern interface without modifying the vendor's source code.

### 2. Bridge Pattern

- **Strategic Purpose:** Decouples an abstraction from its implementation details so that the two can vary completely independently.
- **The Problem It Solves:** Prevents "inheritance explosion". Instead of generating a distinct subclass for every combination (e.g., `RedCircle`, `BlueCircle`, `RedSquare`, `BlueSquare`), you decouple `Shape` from `Color` via composition.

### 3. Composite Pattern

- **Strategic Purpose:** Composes objects into tree structures to represent part-whole hierarchies, allowing clients to treat individual objects and nested groups uniformly.
- **Real-World Analogy:** An operating system file directory. A directory can hold individual files (leaf nodes) as well as subdirectories (composite nodes), and both respond to the same commands like `getSize()` or `delete()`.

### 4. Decorator Pattern

- **Strategic Purpose:** Attaches additional responsibilities and behaviors to an object dynamically at runtime. It offers a flexible alternative to subclassing for extending functionality.
- **Real-World Analogy:** Ordering a basic black coffee and layered modifiers dynamically (e.g., adding milk, adding sugar, adding foam) without creating unique classes for every conceivable coffee combination.

### 5. Facade Pattern

- **Strategic Purpose:** Provides a simplified, high-level interface to a complex and larger subsystem of classes.
- **Real-World Analogy:** A single "Watch Movie" button on a smart remote that automatically switches on the television, configures the audio amplifier, boots the media player, and dims the smart lights.
- **Backend Application:** Designing an enterprise service layer class that coordinates underlying communication between multiple repositories, mail systems, and external payment APIs.

### 6. Flyweight Pattern

- **Strategic Purpose:** Minimizes application memory usage by sharing common, unchanging data across a massive quantity of similar objects.
- **Real-World Analogy:** A digital text engine rendering a book. Instead of instantiating unique font style records for thousands of individual characters, each character holds a coordinate pointer back to a single shared style object.

### 7. Proxy Pattern

- **Strategic Purpose:** Provides a surrogate, placeholder, or wrapper object to control access to another target object.
- **Core Variations:**
- **Virtual Proxy:** Delays expensive initialization until the object is explicitly used (lazy loading).
- **Protection Proxy:** Intercepts invocations to enforce security policies and authorization permissions.
- **Remote Proxy:** Manages structural messaging details between an application and remote network servers.

- **Backend Application:** Core framework interceptors, such as Spring AOP aspect proxies wrapping database transactions around services.

## Architectural Reference Matrix

| Pattern       | Architectural Problem Solved                                                                    |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **Adapter**   | Unifies two incompatible, disparate interfaces without altering vendor code.                    |
| **Bridge**    | Isolates structural variations of an interface from its independent implementation strategy.    |
| **Composite** | Handles nested, tree-like hierarchies uniformly using a shared component type.                  |
| **Decorator** | Layer additional features onto concrete objects at runtime without using subclassing.           |
| **Facade**    | Conceals a messy, complex collection of backend classes behind a single simple entry point.     |
| **Flyweight** | Drastically cuts RAM usage when generating thousands of identical structural entities.          |
| **Proxy**     | Intercepts operations to add access controls, lazy evaluation logic, or transaction boundaries. |

> Important - Decorator, Facade

## Resources

- https://www.google.com/search?q=https://refactoring.guru/design-patterns/structural-patterns