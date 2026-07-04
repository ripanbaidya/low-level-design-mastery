## Access Modifiers in Java

<p align="right">Last updated - 04.07.2026</p>

## Introduction

Access modifiers in Java regulate the visibility and accessibility of classes, methods, variables, and constructors. They form the foundation of **encapsulation** and **information hiding** in Low-Level Design (LLD), ensuring that internal object states are protected from unauthorized modification and tight coupling.

### Key Rules of Scope

- **Top-Level Level:** Can only use `public` or `default` (package-private).
- **Member Level:** Can use all four modifiers (`public`, `protected`, `default`, `private`).

### 1. Public

- **Scope:** Globally accessible across the entire application, including external packages.
- **Use Case:** Exposing APIs, interface definitions, or public constants that require unrestricted access.

**Example**

```java
package utility;

public class PublicExample {
    public void display() {
        System.out.println("Accessible globally.");
    }
}

```

```java
package main;

import utility.PublicExample;

public class Main {
    public static void main(String[] args) {
        PublicExample example = new PublicExample();
        example.display(); // Compiles successfully
    }
}

```

### 2. Private

- **Scope:** Restricted strictly to the declaring class. It is completely hidden from subclasses and external classes within the same package.
- **Use Case:** Hiding internal state variables (fields) and helper methods to prevent direct manipulation, forcing interaction through public getters/setters (encapsulation).

**Example**

```java
package utility;

public class PrivateExample {
    private void display() {
        System.out.println("Accessible only within PrivateExample.");
    }
}

```

```java
package main;

import utility.PrivateExample;

public class Main {
    public static void main(String[] args) {
        PrivateExample example = new PrivateExample();
        // example.display(); // Compile-time error: display() has private access
    }
}

```

### 3. Protected

- **Scope:** Accessible within the same package and by subclasses located in different packages.
- **Use Case:** Providing extension hooks for framework inheritance. It allows derived classes to customize or reuse baseline logic while keeping members hidden from the rest of the application.

> **Critical Caveat (Cross-Package Subclassing):** When a subclass accesses a `protected` member across packages, it can only do so via an instance of **itself** (the subclass type), not via a reference of the parent type.

**Example**

```java
package utility;

public class ProtectedExample {
    protected void display() {
        System.out.println("Accessible via package or subclass.");
    }
}

```

```java
package main;

import utility.ProtectedExample;

public class Main extends ProtectedExample {
    public static void main(String[] args) {
        ProtectedExample parentRef = new ProtectedExample();
        // parentRef.display(); // Compile-time error: protected access across packages requires subclass context

        Main subclassRef = new Main();
        subclassRef.display(); // Compiles successfully
    }
}

```

### 4. Default (Package-Private)

- **Scope:** Activated when no keyword is declared. Visibility is strictly bound to the containing package.
- **Use Case:** Grouping highly cohesive classes within a single package component. It allows package-level entities to collaborate freely without exposing internal implementation details to the public API surface.

**Example**

```java
package utility;

public class DefaultExample {
    void display() { // Package-private
        System.out.println("Accessible only within the 'utility' package.");
    }
}

```

```java
package main;

import utility.DefaultExample;

public class Main {
    public static void main(String[] args) {
        DefaultExample example = new DefaultExample();
        // example.display(); // Compile-time error: display() is not public; cannot be accessed from outside package
    }
}

```

## Visibility Matrix

The following table matrix highlights the access levels ranging from most permissive to most restrictive:

| Modifier    | Same Class | Same Package | Subclass (Diff Package)          | Other Package |
| ----------- | ---------- | ------------ | -------------------------------- | ------------- |
| `public`    | Yes        | Yes          | Yes                              | Yes           |
| `protected` | Yes        | Yes          | Yes (Via subclass instance only) | No            |
| `default`   | Yes        | Yes          | No                               | No            |
| `private`   | Yes        | No           | No                               | No            |

## Some Design Principles

- **Principle of Least Privilege:** Always default to the most restrictive modifier (`private`) and scale up visibility (`default` $\rightarrow$ `protected` $\rightarrow$ `public`) only when explicitly dictated by architectural requirements.
- **Immutability and Defense:** Keep fields `private final` whenever possible. Exposing mutable state fields via `public` or `protected` modifiers breaks object invariants and leads to thread-safety issues in concurrent environments.
- **Package Modularity:** Leverage `default` access to build self-contained modules. Hide factory implementations or concrete strategy components inside the package, exposing only clean, public interfaces or abstract classes to the consumer layer.

## Resources

- https://www.baeldung.com/java-access-modifiers
