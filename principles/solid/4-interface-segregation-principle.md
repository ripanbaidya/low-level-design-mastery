## Interface Segregation Principle (ISP)

<p align="right">Last updated - 29.06.26</p>

The **Interface Segregation Principle (ISP)** is the "I" in the SOLID design principles.

Its core philosophy is: **"Clients should not be forced to depend upon interfaces that they do not use."**

In simpler terms, it is better to have many small, specific interfaces rather than one large, multi-purpose interface. When you create bloated interfaces (often called "fat" or "polluted" interfaces), you force implementing classes to write useless code for methods they don't even need.

## The Real-World Analogy: A Restaurant Menu

Imagine going to a specialized coffee shop, but they hand you a massive 50-page restaurant menu containing sushi, Italian pasta, steaks, and tacos just so you can order an espresso.

Worse yet, if the restaurant decides to update their sushi recipes, they have to reprint the entire massive menu, impacting the coffee section too. Instead, it makes much more sense to separate the menus: a drink menu for the cafe, a sushi menu for the sushi bar, and a dinner menu for the kitchen.

## Example: The Bad vs. The Good

Let's look at a system managing smart office devices like printers, scanners, and faxes.

### ❌ The Bad Way (Violating ISP)

Here, we design a single "fat" interface that bundles all possible actions together.

```java
// A polluted, "fat" interface
public interface SmartDevice {
    void print(String document);
    void scan(String document);
    void fax(String document);
}

// Old-fashioned printer that can only print
public class BasicPrinter implements SmartDevice {
    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }

    @Override
    public void scan(String document) {
        // VIOLATION: Forced to provide an empty implementation or throw an exception
        throw new UnsupportedOperationException("Scan not supported!");
    }

    @Override
    public void fax(String document) {
        // VIOLATION: Forced to provide an empty implementation or throw an exception
        throw new UnsupportedOperationException("Fax not supported!");
    }
}

```

#### Why this breaks ISP:

The `BasicPrinter` class is forced to depend on `scan()` and `fax()` methods, even though it physically cannot perform those actions. If a change is made to the `fax()` method signature in the interface, `BasicPrinter` will break and must be recompiled, despite not using it.

### The Good Way (Adhering to ISP)

To fix this, we break down (segregate) the large interface into role-specific, focused interfaces.

```java
// Segregated, single-purpose interfaces
public interface Printer {
    void print(String document);
}

public interface Scanner {
    void scan(String document);
}

public interface FaxMachine {
    void fax(String document);
}

// This printer only implements what it actually does
public class BasicPrinter implements Printer {
    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }
}

// A high-end office machine can implement multiple interfaces seamlessly
public class SuperCopier implements Printer, Scanner {
    @Override
    public void print(String document) {
        System.out.println("High-speed printing: " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("Scanning document...");
    }
}

```

#### Why this is brilliant:

Now, `BasicPrinter` is completely decoupled from scanning and faxing. If the `Scanner` interface changes, only classes that actually scan (like `SuperCopier`) are affected. Your code becomes highly modular and robust.

## ISP vs. Single Responsibility Principle (SRP)

They sound similar, but they address different perspectives:

- **SRP** is about the **implementation** (the class). A class should have only one reason to change and handle one cohesive job.

- **ISP** is about the **client/consumer** (the interface). An interface should not force its users to see or implement methods they don't care about.

## Key Benefits of ISP

- **Leaner Codebases:** Eliminates dummy methods that do nothing or throw `UnsupportedOperationException`.

- **Reduced Side Effects:** Changing one piece of functionality doesn't cause a cascade of compilation errors across unrelated classes.

- **Better Readability:** When a developer looks at an interface, its intent is immediately obvious without being clouded by unrelated methods.

## References
