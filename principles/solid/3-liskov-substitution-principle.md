## Liskov Substitution Principle (LSP)

<p align="right">Last updated - 29.06.26</p>

The **Liskov Substitution Principle (LSP)** is the "L" in the SOLID design principles. It was introduced by `Barbara Liskov` in a 1987 conference keynote and later formalized in a 1994 paper.

The core definition is: **"Subtypes must be substitutable for their base types."**

In plain English: If you have a program that works using a base (parent) class, you should be able to pass in any of its subclasses (child classes) instead, and the program should still run perfectly without crashing or behaving unexpectedly. The child class must honor the "contract" established by the parent class.

## The Real-World Analogy: The Duck Test gone wrong

You might have heard the phrase: _"If it looks like a duck and quacks like a duck, it's a duck."_ LSP warns us about inheritance traps. Imagine you have a toy rubber duck that inherits behavior from a real duck. They both look like ducks. But if the real duck flies away, and your code tries to make the rubber duck fly, it will crash because the rubber duck needs batteries first.

Even though a rubber duck is technically "a duck" conceptually, it **cannot substitute** a real duck in practice without breaking expectations.

## Example: The Bad vs. The Good

The most classic violation of LSP involves a `Rectangle` and a `Square`. Mathematically, a square _is_ a rectangle. But in object-oriented programming, inheriting a Square from a Rectangle breaks LSP.

### ❌ The Bad Way (Violating LSP)

Watch what happens when we force `Square` to behave like a `Rectangle`.

```java
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // A square must have equal sides
    }

    @Override
    public void setHeight(int height) {
        this.width = height; // A square must have equal sides
        this.height = height;
    }
}

```

#### Why this breaks LSP:

Let's look at a client method that expects a regular `Rectangle`:

```java
public class Test {
    public void verifyArea(Rectangle r) {
        r.setWidth(5);
        r.setHeight(4);

        // If 'r' is a Rectangle: 5 * 4 = 20. Correct!
        // If 'r' is a Square: the height override changed the width to 4! 4 * 4 = 16.
        if (r.getArea() != 20) {
            throw new RuntimeException("LSP Violation: Broken behavior!");
        }
    }
}

```

Passing a `Square` into `verifyArea` completely breaks the system because it violates the behavior the user expected from a `Rectangle`. `Square` is not safely substitutable for `Rectangle`.

### ✅ The Good Way (Adhering to LSP)

To fix this, we recognize that `Square` and `Rectangle` shouldn't inherit from each other. Instead, they should both implement a more general interface, or remain completely independent if their behaviors conflict.

```java
// 1. Define a shared abstraction that doesn't make assumptions about side modification
public interface Shape {
    int getArea();
}

// 2. Implement Rectangle independently
public class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    @Override
    public int getArea() { return width * height; }
}

// 3. Implement Square independently
public class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public void setSide(int side) { this.side = side; }

    @Override
    public int getArea() { return side * side; }
}

```

Now, if a method accepts a `Shape`, it only expects to call `.getArea()`. Both `Rectangle` and `Square` can fulfill this contract flawlessly without mutating internal state in a way that surprises the caller.

## Common Signs of LSP Violations

If you see these red flags in your Java code, you are likely violating LSP:

1. **Empty Method Overrides:** A subclass overrides a parent method but leaves it completely blank because it "doesn't need it".

2. **Throwing `UnsupportedOperationException`:** A subclass overrides a parent method but throws an error because it can't perform that action.

3. **Type Checking (`instanceof`):** Code in your client application explicitly checks the type of subclass to decide how to act (e.g., `if (shape instanceof Square)`). This means your abstraction failed.

## Key Benefits

- **True Polymorphism:** You can confidently use polymorphism knowing that any subclass will act properly without needing custom type-checking.

- **Code Predictability:** Enhances trust in inheritance structures. You won't face random, runtime bugs when swapping out implementation dependencies.

## References
