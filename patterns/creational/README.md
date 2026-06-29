## Creational Design Pattern

<p align="right">Last updated - 25.02.2026</p>

## Introduction

Creational Design Patterns are a specific category of software design patterns that abstract and manage object instantiation mechanisms. Instead of creating objects directly using the `new` operator—which couples your application tightly to concrete implementations—these patterns provide controlled, flexible, and reusable strategies for object creation.

> **Core Philosophy:** Creational patterns decouple a system from how its objects are created, composed, and represented.

By using these patterns, you can:

- Reduce tight coupling between classes.
- Encapsulate object creation logic in a single location.
- Promote code reusability and scalability.
- Adhere strictly to SOLID principles, specifically the Single Responsibility Principle (SRP) and the Open-Closed Principle (OCP).

## The Problem: Tight Coupling with Direct Instantiation

Consider an enterprise backend system where you instantiate a service directly across multiple files:

```java
PaymentService paymentService = new PaymentService();
```

If business requirements change later and you need to:

- Switch to an entirely different implementation provider,
- Inject cross-cutting concerns like logging or caching,
- Or dynamically return a specific subclass based on runtime conditions,

You are forced to locate and modify every instance where the `new` keyword was used. This results in code that is tightly coupled, highly resistant to extension, and in direct violation of the Open-Closed Principle.

## The Five Creational Design Patterns

The Gang of Four (GoF) reference manual defines five distinct creational patterns. Each pattern addresses a specific architectural problem:

| Pattern              | Strategic Purpose                                                                                                                  |
| -------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **Singleton**        | Ensures a class has only one global instance and provides a single point of access to it.                                          |
| **Factory Method**   | Defines an interface for creating an object, but allows subclasses to decide which class to instantiate.                           |
| **Abstract Factory** | Provides an interface for creating families of related or dependent objects without specifying their concrete classes.             |
| **Builder**          | Separates the construction of a complex object from its structural representation, enabling step-by-step assembly.                 |
| **Prototype**        | Specifies the kinds of objects to create using a prototypical instance, creating new objects by copying or cloning this prototype. |

> 💡 **Note:** Must know Creational Design Patterns - Singleton, Factory and Builder.

### 1. Singleton Pattern

Ensures a single instance of a class manages a particular resource across the entire application lifecycle.

- **Common Use Cases:** Managing shared hardware drivers, database connection pools, global configuration managers, or application-wide logging systems.

### 2. Factory Method Pattern

Delegates object creation responsibility to subclasses, preventing the calling code from depending directly on concrete implementation classes.

- **Common Use Cases:** Frameworks where the exact type of component to render or process is decided dynamically at runtime based on user configuration.

### 3. Abstract Factory Pattern

Acts as a factory of factories. It bundles individual, distinct factories that share a common theme or operational requirement without exposing their concrete implementations.

- **Common Use Cases:** Designing cross-platform GUI kits (e.g., matching a `WindowsButton` and `WindowsCheckbox` versus a `MacButton` and `MacCheckbox`).

### 4. Builder Pattern

Assembles complex, immutable objects incrementally using a step-by-step construction sequence. This removes the anti-pattern of maintaining bloated constructors with long, confusing lists of optional parameters.

- **Example Application:**

    ```java
    User user = new User.Builder("Ripan")
                        .age(21)
                        .email("ripan@email.com")
                        .build();
    ```

### 5. Prototype Pattern

Creates new instances by duplicating existing archetypes. This completely bypasses the computational overhead of invoking expensive database queries, heavy configuration scripts, or network handshakes required to build a fresh object from scratch.

- **Example Application:**

    ```java
    Object copy = original.clone();
    ```

## Architectural Evaluation Matrix

Apply creational patterns systematically when your system exhibits the following architectural traits:

- **High Structural Complexity:** The instantiation process requires complex data parsing, external configuration files, or strict dependencies.
- **Dynamic Variation:** The specific types of objects your system needs to generate fluctuate based on runtime parameters.
- **Component Autonomy:** Your application components should remain entirely agnostic regarding how their product dependencies are generated and assembled.

## Conclusion

Creational patterns replace rigid, hardcoded instantiation patterns with flexible architectural boundaries. By relying on abstraction rather than direct implementation, your system remains robust, testable, and completely adaptable to shifting infrastructural demands.

## Resources

- https://refactoring.guru/design-patterns/creational-patterns
