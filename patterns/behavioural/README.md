## Behavioral Design Pattern

<p align="right">Last updated - 29.06.26</p>

## Introduction

The concept of **Behavioral Design Patterns** originates from the foundational Gang of Four (GoF) book, _Design Patterns: Elements of Reusable Object-Oriented Software_. While creational patterns manage object instantiation and structural patterns dictate component composition, behavioral patterns are entirely concerned with the mechanics of interaction.

> **Core Philosophy:** Behavioral design patterns focus on how objects communicate, how responsibilities are distributed, and how system behavior can be altered dynamically at runtime.

Implementing these patterns allows you to:

- Establish clear, flexible communication channels between objects.
- Decouple the sender of a request from its receiver.
- Eliminate complex, nested conditional logic (`if-else` or `switch` blocks).
- Adapt application behavior dynamically based on runtime context or state.

## When to Use vs. Avoid

### Use When

- You need to coordinate complex communication between a diverse set of objects.
- Component algorithms or behaviors must be selected and swapped dynamically at runtime.
- Your codebase is bogged down by massive conditional structures checking state or types.
- You need to support rollback operations (undo/redo functionality).
- You are building event-driven architectures or pipeline processing systems.

### Avoid When

- The system is straightforward and your operational flows are predictable and stable.
- Introducing an abstraction layer adds more architectural complexity than the problem warrants.
- Splitting behaviors causes "class explosion," making the codebase fragmented and harder to read.

## The 11 Behavioral Design Patterns

### 1. Strategy Pattern

- **Core Idea:** Defines a family of interchangeable algorithms, encapsulates each one, and makes them independent of the clients that use them.
- **Key Insight:** It allows you to adhere to the Open-Closed Principle (OCP) by adding new algorithms without altering the context class, effectively replacing runtime conditional blocks.

### 2. Observer Pattern

- **Core Idea:** Establishes a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
- **Real-World Analogy:** A publisher-subscriber framework, such as a content platform notifying subscribers of a new upload, or real-time UI components listening to stock price changes. It is heavily utilized in event-driven setups and framework ecosystems like Spring Events.

### 3. Command Pattern

- **Core Idea:** Encapsulates a request or action as a standalone object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.
- **Real-World Analogy:** A home automation remote control button. The button element triggers a specific execution object without needing to know the technical wiring of the target appliance.

### 4. Chain of Responsibility Pattern

- **Core Idea:** Avoids coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. It strings the receiving objects together into a pipeline and passes the request along the chain until an object handles it.
- **Real-World Analogy:** HTTP request middleware execution paths, such as Java Servlet Filters or a Spring Security filter pipeline handling Authentication $\rightarrow$ Logging $\rightarrow$ Validation.

### 5. State Pattern

- **Core Idea:** Allows an object to alter its behavior when its internal state changes, making the object appear as though it changed its class type.
- **Real-World Analogy:** An automated banking machine (ATM) shifting its behavioral context across distinct operational states: `NoCard` $\rightarrow$ `HasCard` $\rightarrow$ `PinVerified`.

### 6. Template Method Pattern

- **Core Idea:** Defines the skeleton of an algorithm in an operation, deferring some steps to subclasses. It lets subclasses redefine certain steps of an algorithm without changing the algorithm's overarching structure.
- **Key Insight:** Highly prevalent in abstract application layers, such as Spring's `JdbcTemplate`, where the structural steps (opening connections, handling transactions) are locked, but the execution details are customizable.

### 7. Iterator Pattern

- **Core Idea:** Provides a way to access the elements of an aggregate object sequentially without exposing its underlying structural representation.
- **Java Integration:** Natively integrated directly into the Java Collections Framework via the standard interface utility:

    ```java
    Iterator<String> iterator = list.iterator();
    ```

### 8. Mediator Pattern

- **Core Idea:** Defines an object that encapsulates how a set of objects interact. It promotes loose coupling by keeping objects from referring to each other explicitly, letting you vary their interaction independently.
- **Real-World Analogy:** An airport air traffic control tower coordinating plane movements rather than requiring every aircraft to communicate directly with every other aircraft.

### 9. Memento Pattern

- **Core Idea:** Without violating encapsulation, captures and externalizes an object's internal state so that the object can be restored to this state later.
- **Common Use Cases:** Designing history states, snapshots, or deep rollback actions inside rich client text editors or transactional processing units.

### 10. Visitor Pattern

- **Core Idea:** Represents an operation to be performed on the elements of an object structure. It lets you define a new operation without changing the classes of the elements on which it operates.
- **Real-World Analogy:** Applying shifting external tax assessment operations dynamically over an inventory list of stable, unchanging product types like food, clothing, and electronics.

### 11. Interpreter Pattern

- **Core Idea:** Given a language, defines a representation for its grammar along with an interpreter that uses the representation to evaluate sentences in the language.
- **Common Use Cases:** Writing internal rule evaluation engines, custom mathematical formula parsers, or SQL query evaluation utilities.

## Architectural Reference Matrix

| Pattern                     | Primary Problem Solved                                                                         |
| --------------------------- | ---------------------------------------------------------------------------------------------- |
| **Strategy**                | Needs to swap interchangeable algorithms at runtime.                                           |
| **Observer**                | Needs to broadcast state changes to unknown, dynamic listeners.                                |
| **Command**                 | Needs to turn operations into objects to support scheduling, queuing, or rollbacks.            |
| **Chain of Responsibility** | Needs to pass a request through an ordered sequence of decoupled processors.                   |
| **State**                   | Needs to change object behavior based completely on its shifting internal values.              |
| **Template Method**         | Needs to lock down an algorithmic workflow skeleton while allowing step customization.         |
| **Iterator**                | Needs to step through collection elements without exposing its data layout.                    |
| **Mediator**                | Needs to reduce chaotic, complex inter-dependencies between multiple classes.                  |
| **Memento**                 | Needs to capture, track, and restore historical snapshots of an object safely.                 |
| **Visitor**                 | Needs to add processing logic to an existing structural type without altering its source code. |
| **Interpreter**             | Needs to interpret and execute processing routines for a custom language grammar.              |

> 💡 Important - Strategy, Observer, State

## Resources

- https://www.google.com/search?q=https://refactoring.guru/design-patterns/behavioral-patterns
