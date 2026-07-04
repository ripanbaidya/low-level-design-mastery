## The "final" Keyword in Java

<p align="right">Last updated - 04.07.2026</p>

## Introduction

In Java, the `final` keyword is a non-access modifier used to restrict extensibility, modification, and reassignment. It serves as a tool for enforcing immutability, ensuring API security, and optimizing runtime performance.

The application of `final` fundamentally alters the behavior of **classes**, **methods**, and **variables**.

## 1. Final Classes

A class declared as `final` **cannot be subclassed** or extended. This cuts off the inheritance tree at that specific node.

```java
final class Vehicle {
    // Structural implementation
}

// class Car extends Vehicle { }
// ❌ Compile-time error: Cannot inherit from final 'utility.Vehicle'

```

### Architectural Rationale

- **Immutability Contracts:** Core Java library classes such as `java.lang.String`, `java.lang.Integer`, and `java.lang.Double` are marked `final`. If `String` were not final, a subclass could override behaviors to masquerade as a secure string while secretly altering data, compromising JVM security.
- **Performance Optimization:** The compiler and JVM can optimize method calls within `final` classes (e.g., via aggressive method inlining), knowing that no subclass will ever alter the behavior.

## 2. Final Methods

A method declared as `final` **cannot be overridden** by any subclass.

```java
class Parent {
    public final void computeMetrics() {
        System.out.println("Executing unalterable core engine logic.");
    }
}

class Child extends Parent {
    // @Override
    // public void computeMetrics() { }
    // ❌ Compile-time error: 'computeMetrics()' cannot override 'computeMetrics()' in 'Parent'; overridden method is final
}

```

### Architectural Rationale

- **Preserving Invariants:** Use `final` methods when the base class prescribes a rigid control loop or algorithmic sequence (e.g., the Template Method Design Pattern) that sub-classes must not corrupt.
- **Security Barriers:** Critical system check operations should be marked `final` to prevent malicious actors from extending the component and overriding security verifications to return `true`.

## 3. Final Variables

Marking a variable `final` guarantees that its value mapping can be assigned **exactly once**. Once initialized, any attempt to rebind the variable results in a compile-time error.

### 3.1 Primitive Variables

For primitives, `final` freezes the literal value itself.

```java
public void calculate() {
    final int baseThreshold = 100;
    // baseThreshold = 200;
    // ❌ Compile-time error: Cannot assign a value to final variable 'baseThreshold'
}

```

### 3.2 Reference Variables

For reference variables, `final` freezes the **memory address pointer** held by the reference variable. It does **not** make the underlying heap object immutable.

```java
class Cat {
    private int weight;
    public void setWeight(int w) { this.weight = w; }
}

public class Main {
    public static void main(String[] args) {
        final Cat cat = new Cat(); // Allocated address e.g., @0x9f is locked into 'cat'

        // cat = new Cat();
        // ❌ Compile-time error: Cannot reassign final variable 'cat'

        cat.setWeight(5); // ✅ Completely valid: Internal object state is mutable
    }
}

```

### 3.3 Static Final Variables (Constants)

Used to create compile-time thread-safe constants. By convention, these are named using `UPPER_CASE` with underscore separators.

```java
public class AppConfig {
    public static final double PLANCK_CONSTANT = 6.62607015e-34;
}

```

- `static`: Accessible via class metadata context without instantiating objects.
- `final`: Value cannot be altered post-class-loading.

### 3.4 Final Parameters

Declaring method arguments as `final` locks the local variable parameter inside the scope of the method execution thread frame, preventing accidental modification within the method body.

```java
public void processTransaction(final double orderTotal) {
    // orderTotal = orderTotal * 0.90;
    // ❌ Compile-time error: The final local variable orderTotal cannot be assigned
}

```

## Architectural Impact

| Application Target | Structural Constraint                                    | Architectural Value                                                                             |
| ------------------ | -------------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| **Class**          | Prevents class extension/inheritance.                    | Guarantees immutability frameworks, closes security vulnerabilities, enables compiler inlining. |
| **Method**         | Blocks polymorphism overriding in child classes.         | Safeguards design patterns (Template Method), locks critical business logic.                    |
| **Variable**       | Prevents value or memory address reference reassignment. | Ensures reference thread-safety, establishes global constants, protects local scopes.           |

## Production Design Principles

- **Design for Inheritance or Else Prohibit It:** Follow Joshua Bloch's advice in _Effective Java_. If a class isn't explicitly designed, documented, and tested to support safe inheritance subclasses, explicitly mark it `final`.
- **Thread Safety via Immutability:** Combining `private final` fields inside a class without exposing setters is the simplest mechanism for creating thread-safe immutable objects (e.g., Value Objects/DTOs) that require no synchronized block overhead in highly concurrent environments.
- **Effectively Final:** Since Java 8, local variables that are not explicitly marked `final` but do not change values after initialization are treated as _effectively final_. This allows them to be safely captured by Lambdas and Anonymous Inner Classes without explicit boilerplate code.
