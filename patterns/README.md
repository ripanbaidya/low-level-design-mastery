## Design Patterns

<p align="right">Last updated - 29.06.2026</p>

## Overview

Design patterns represent the industry-standard best practices used by experienced object-oriented software developers. They are elegant, battle-tested solutions to recurring problems encountered during software design. Rather than being invented out of thin air, these solutions were discovered and refined through years of trial, error, and practical experience by countless developers.

### Key Characteristics

- **Smart:** They provide elegant, optimal solutions that a novice developer might not immediately conceptualize or discover.

- **Generic:** They are decoupled from specific system types, application domains, or programming languages. They represent conceptual blueprints.

- **Well-Proven:** They are discovered from real, operating object-oriented systems—not merely theoretical paperwork.

- **Simple:** They generally involve a small, highly cohesive cluster of classes. Complex software structures are often built by combining multiple simple patterns.

- **Reusable:** They are flexible, adaptive solutions that can be applied effectively across completely different systems and industries.

- **Object-Oriented:** They leverage fundamental `OO` mechanisms—such as encapsulation, inheritance, interfaces, and polymorphism—rooted deeply in foundational software design principles.

## What is the "Gang of Four" (GoF)?

In **1994**, authors **Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides** published the seminal book _"Design Patterns: Elements of Reusable Object-Oriented Software"_. This milestone publication formally introduced the software engineering community to architectural design patterns.

These four authors are universally referred to as the **Gang of Four (GoF)**. According to them, design patterns are built primarily on two core pillars of object-oriented design:

> 1. **Program to an interface, not an implementation.**
> 2. **Favor object composition over class inheritance.**

## Core Usage of Design Patterns

Design patterns serve two vital roles in modern software development:

### 1. Establishing a Common Platform

Design patterns provide a standardized, universal vocabulary for developers. Instead of explaining a complex architectural flow from scratch, developers can use a pattern name to immediately convey intent.

- _Example:_ Stating that a module uses a **Singleton Pattern** instantly informs the team that only one instance of that object will exist throughout the application lifecycle.

### 2. Standardizing Best Practices

Because these patterns have evolved over decades, they bypass the pitfalls of poor architecture. Learning these patterns acts as a catalyst for junior developers, allowing them to absorb masterful design principles rapidly without making costly rookie mistakes.

---

## Classification of Design Patterns

The original GoF reference book identifies **23 distinct design patterns**, organized cleanly into three primary categories based on their functional purpose. A fourth category (**J2EE Patterns**) was later added by the Sun Java Center to address enterprise web applications.

```
                ┌───────────────────────────┐
                │   Design Pattern Types    │
                └─────────────┬─────────────┘
                              │
     ┌───────────────┬───────────────┬───────────────┐
     ▼               ▼               ▼               ▼
┌───────────┐   ┌───────────┐   ┌───────────┐   ┌───────────┐
│Creational │   │Structural │   │Behavioral │   │   J2EE    │
└───────────┘   └───────────┘   └───────────┘   └───────────┘

```

---

### 1. Creational Design Patterns

These patterns abstract and handle the object instantiation process. Instead of hardcoding direct object creation using the `new` operator, creational patterns decouple the system from how its objects are created, composed, and represented.

- **Abstract Factory:** Creates families of related or dependent objects without specifying their concrete classes.
- **Builder:** Separates the construction of a complex object from its representation, allowing the same construction process to create different representations.
- **Factory Method:** Defines an interface for creating an object, but lets subclasses decide which class to instantiate.
- **Prototype:** Creates new objects by copying/cloning an existing archetypal instance.
- **Singleton:** Ensures a class has only one global instance and provides a single point of access to it.

---

### 2. Structural Design Patterns

These patterns deal with class and object composition. They use inheritance and interfaces to seamlessly stitch distinct objects together, forming larger, more efficient structures without sacrificing flexibility.

- **Adapter:** Allows incompatible interfaces to work together by wrapping a class around an existing one.
- **Bridge:** Decouples an abstraction from its implementation so that the two can vary independently.
- **Composite:** Composes objects into tree structures to represent part-whole hierarchies, letting clients treat individual objects and compositions uniformly.
- **Decorator:** Dynamically attaches additional responsibilities to an object at runtime without altering its structure.
- **Facade:** Provides a simplified, unified interface to a complex subsystem of classes.
- **Flyweight:** Minimizes memory usage by sharing as much data as possible with similar objects.
- **Proxy:** Provides a placeholder or surrogate object to control access to the original object (e.g., for lazy loading, security, or caching).

---

### 3. Behavioral Design Patterns

These patterns focus specifically on communication, interaction, and the distribution of responsibilities between objects. They ensure that independent components can pass messages back and forth fluidly while remaining loosely coupled.

- **Chain of Responsibility:** Passes a request along a chain of potential handlers until one handles it.
- **Command:** Encapsulates a request as an object, allowing you to parameterize clients with queues, logs, or undoable operations.
- **Interpreter:** Specifies how to evaluate sentences in a particular language or grammar.
- **Iterator:** Provides a way to sequentially access the elements of a collection without exposing its underlying structure.
- **Mediator:** Restricts direct communications between objects and forces them to collaborate solely through a central mediator object.
- **Memento:** Captures and externalizes an object's internal state so that it can be restored later without violating encapsulation.
- **Observer:** Defines a subscription mechanism to notify multiple observing objects automatically about any state changes in the subject they are watching.
- **State:** Allows an object to alter its behavior when its internal state changes, appearing as if the object changed its class.
- **Strategy:** Defines a family of interchangeable algorithms and encapsulates each one inside a separate class.
- **Template Method:** Defines the skeleton of an algorithm in a superclass, deferring specific execution steps to subclasses.
- **Visitor:** Separates an algorithm from the complex object structure on which it operates, allowing you to add new operations without modifying the structure.

---

### 4. J2EE Design Patterns

Identified by the Sun Java Center, these patterns focus specifically on the enterprise layer, integration tiers, and the presentational logic of Java 2 Enterprise Edition (J2EE) environments.

- **MVC (Model-View-Controller):** Divides an application into three interconnected components—Model (data), View (UI), and Controller (logic)—to strictly separate concerns.

## Pros and Cons of Design Patterns

### Benefits

- **Elevates Developer Productivity:** Avoids reinventing solutions to common engineering problems.
- **High Reusability:** Provides abstract frameworks that are significantly easier to adapt across diverse platforms than hardcoded block logic.
- **Architectural Consistency:** Ensures uniform design choices across large corporate codebases, making team-wide code reviews predictable.
- **Proven Reliability:** Drastically lowers systemic bugs by replacing speculative code paths with optimized, battle-tested solutions.

### Drawbacks

- **Learning Curve Overhead:** Requires developers to study, master, and correctly apply a wide catalog of sophisticated patterns.
- **Risk of Over-Engineering:** Teams can fall prey to _Patternitis_—applying patterns aggressively where simple, direct code would suffice, introducing unnecessary abstraction layers.
- **Cultural Pushback (NIH Syndrome):** "Not Invented Here" syndrome or developer pride can cause engineers to reject standardized patterns in favor of custom, fragmented solutions.
- **Buzzword Dilution:** Often overused by non-technical stakeholders or marketing personnel as a vague shorthand for quality, distorting the precise engineering definitions.

## Resources

- https://refactoring.guru/design-patterns/what-is-pattern
