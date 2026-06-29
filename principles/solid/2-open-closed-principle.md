## Open-Closed Principle (OCP)

<p align="right">Last updated - 29.06.26</p>

The **Open-Closed Principle (OCP)** is the "O" in the SOLID design principles. It was introduced by Bertrand Meyer in 1988 and forms the foundation for writing code that can grow over time without breaking existing functionality.

The core definition is: **"Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification."**

- **Open for extension:** You should be able to add new features or behaviors to the class easily.

- **Closed for modification:** You should _not_ have to change the existing, already-tested code to add those new features.

## The Real-World Analogy: The Wall Outlet

Think of a standard electrical wall outlet in your home.

- It is **closed for modification**: You don't rip open the wall and rewire your house every time you buy a new appliance (like a toaster or a vacuum).

- It is **open for extension**: The outlet provides a standard interface (the plug slots). As long as your new appliance complies with that plug interface, you can plug it in and extend your home's functionality instantly.

## Example: The Bad vs. The Good

Let's say you are building a system that calculates the area of different shapes.

### ❌ The Bad Way (Violating OCP)

In this approach, every time we add a new shape, we are forced to modify the existing `AreaCalculator` class by adding another `if/else` or `switch` case.

```java
public class Rectangle {
    public double length;
    public double width;
}

public class Circle {
    public double radius;
}

// The Violator
public class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.length * r.width;
        } else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius * c.radius;
        }
        // What happens when we add a Triangle?
        // We must modify this class again!
        return 0;
    }
}

```

#### Why this is bad:

Every time a new shape comes along, you must open `AreaCalculator.java` and modify its logic. If you make a typo, you risk breaking the area calculation for `Rectangle` and `Circle`, which were already working perfectly.

### ✅ The Good Way (Adhering to OCP)

To fix this, we introduce an **Interface** or an **Abstract Class**. We push the responsibility of calculating the area down to the individual shapes.

```java
// 1. Create a common interface
public interface Shape {
    double calculateArea();
}

// 2. Implement the interface in concrete classes
public class Rectangle implements Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}

public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// 3. The Calculator is now CLOSED to modification
public class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea(); // Polymorphism at work
    }
}

```

#### Why this is brilliant:

If you want to add a `Triangle` tomorrow, you simply create a new class `Triangle implements Shape` and write its area logic there. **You don't touch a single line of existing code** in `AreaCalculator`, `Rectangle`, or `Circle`. Your system is completely open to extension but closed to modification.

## Key Benefits of OCP

- **Zero Regression Risk:** Because you aren't changing old code, you drastically lower the risk of introducing new bugs into existing features.
- **Loose Coupling:** Components interact through abstractions (interfaces) rather than concrete implementations.
- **Plug-and-Play Architecture:** It allows frameworks and plugins to work smoothly. You can inject new behaviors dynamically without altering the core engine.

## References
