# Decorator Design Pattern

| Pattern Name | Category   | Complexity |
| ------------ | ---------- | ---------- |
| Decorator    | Structural | Medium     |

**Intent (One-liner)**

Dynamically add responsibilities to objects without modifying their structure or existing code.

## 1. Introduction (What + Why)

The **Decorator Pattern** allows behavior to be added to an individual object **at runtime** by wrapping it with decorator objects.

Instead of creating multiple subclasses for every possible combination of features, decorators **compose behavior dynamically** using object composition.

📌 It follows **Open–Closed Principle**

> _Open for extension, closed for modification_

## 2. Problem Statement (Why this pattern exists)

Imagine a system where:

- Objects can have **many optional features**
- Features can be **combined in any order**
- New features should not require changing existing classes

### ✅ With Decorator

- Add features dynamically
- Combine them freely
- No modification of original class

## 3. When to Use

Use when:

- You want to **add responsibilities dynamically**
- Subclassing would cause **too many classes**
- You want to **extend behavior without changing existing code**
- You need **runtime composition of behaviors**

## 4. When NOT to Use

Avoid when:

- Object behavior is **static and simple**
- Too many decorators would **reduce readability**
- Performance is extremely critical (due to multiple wrappers)
- A simple inheritance hierarchy is sufficient

## 5. Core Components (Participants)

| Component          | Responsibility               |
| ---                | ---                          |
| Component          | Defines the common interface |
| Concrete Component | Base object to be decorated  |
| Base Decorator     | Holds reference to Component |
| Concrete Decorator | Adds extra behavior          |
| Client             | Client Application           |

## 6. UML / Structure (Mental Model)

```
Client --uses----> Component ---------------------------▶ BaseDecorator                    
                      ▲                                          ▲
                ______|_______                          _________|________
                |            |                          |                |
        ConcreteComponentA ConcreteComponentB    ConcreteDecoratorA   ConcreteDecoratorB          
```

🧠 **Mental model**:

> “Decorator _is-a_ Component and _has-a_ Component”

## 7. Code (Gift Example)

**Problem Statement:** 

Imagine an online gift-preparation system where customers can buy a gift item (for example, a Watch) and optionally decorate it in multiple ways — such as adding a wrapper, a bow, or a name tag.

**Challenges:**

- Decorations are optional and can be combined in any order
- New decoration types may be added in the future
- We should not modify the core gift preparation logic every time a new decoration is introduced
- **Creating subclasses like:**
    - WrappedWatch
    - WrappedBowWatch
    - WrappedBowNameTagWatch
    
    quickly leads to class explosion and rigid code

**Goal:**

Design a solution that allows:

- Dynamic addition of responsibilities (decorations)
- Runtime composition of features
- Adherence to the Open/Closed Principle (open for extension, closed for modification)

This is where the **Decorator Pattern** fits perfectly.

### Component (Gift)

Defines a common interface for all gifts and decorators. Both the base gift and all decorations follow the same contract, making them interchangeable.

```java
package gift;

public interface Gift {
    String prepare();
}
```

### Concrete Component (Watch)

Represents the core gift item. Contains only the basic gift preparation logic and has no knowledge of any decorations.

```java
package gift;

public class Watch implements Gift {

    private final String brand;

    public Watch(String brand) {
        this.brand = brand;
    }

    @Override
    public String prepare() {
        return "Watch [" + brand + "]";
    }
}
```

### Abstract/Base Decorator (GiftDecorator)

Acts as a base class for all decorators. Wraps another Gift object and forwards calls to it, enabling dynamic behavior extension.

```java
package decorators;

import gift.Gift;

public abstract class GiftDecorator implements Gift {

    protected final Gift gift;

    protected GiftDecorator(Gift gift) {
        this.gift = gift;
    }
}
```

### Concrete Decorators

Each decorator adds a specific decoration to the gift. They enhance the result of prepare() without altering the original gift or other decorators.

```java
package decorators;

import gift.Gift;

public class WrapperDecorator extends GiftDecorator {

    public WrapperDecorator(Gift gift) {
        super(gift);
    }

    @Override
    public String prepare() {
        return gift.prepare() + " wrapped in paper";
    }
}
```

```java
package decorators;

import gift.Gift;

public class BowDecorator extends GiftDecorator {

    public BowDecorator(Gift gift) {
        super(gift);
    }

    @Override
    public String prepare() {
        return gift.prepare() + " with a bow";
    }
}
```

```java
package decorators;

import gift.Gift;

public class NameTagDecorator extends GiftDecorator {

    public NameTagDecorator(Gift gift) {
        super(gift);
    }

    @Override
    public String prepare() {
        return gift.prepare() + " and a name tag";
    }
}
```

### Client Code

Composes the gift by wrapping it with decorators at runtime. Allows flexible decoration combinations while avoiding subclass explosion.

```java
import decorators.*;
import gift.*;

public class Application {
    public static void main(String[] args) {
        Gift gift = new Watch("Peter England");
        System.out.println(gift.prepare());

        Gift wrappedGift = new WrapperDecorator(gift);
        System.out.println(wrappedGift.prepare());

        Gift wrappedBowGift = new BowDecorator(new WrapperDecorator(gift));
        System.out.println(wrappedBowGift.prepare());

        Gift fullyDecoratedGift = new NameTagDecorator(
                new BowDecorator(new WrapperDecorator(gift)));

        System.out.println(fullyDecoratedGift.prepare());
    }
}
```

## 8. Real-World Use Cases

- Java I/O Streams (`BufferedInputStream`, `DataInputStream`)
- Spring `HttpServletRequestWrapper`
- UI components (scrollbar, border, shadow)
- Logging / Metrics / Security wrappers
- Coffee / Pizza add-ons (classic example)

## 9. Pros & Cons

### ✅ Pros

- Follows Open–Closed Principle
- Avoids class explosion
- Flexible runtime composition
- Single Responsibility per decorator

### ❌ Cons

- Many small objects
- Harder debugging
- Order of decorators matters
- Can reduce readability if overused

## 10. Related Patterns

| Pattern   | Difference                |
| --------- | ------------------------- |
| Adapter   | Changes interface         |
| Proxy     | Controls access           |
| Composite | Treats objects uniformly  |
| Strategy  | Replaces behavior         |
| Decorator | Adds behavior dynamically |

📌 **Common confusion**:
Decorator **adds** behavior
Strategy **switches** behavior

## 11. Summary (Last Minute)

- Decorator = **wrap object to add behavior**
- Uses **composition over inheritance**
- Base decorator is **abstract**
- Wrapped object is **protected**
- Behavior added **dynamically**
- Client remains unaware of decorations

🧠 **One-line recall**:

> “Decorator wraps an object to extend its behavior without modifying the original class.”

## 12. Resources

### Articles

- [https://algomaster.io/learn/lld/decorator](https://algomaster.io/learn/lld/decorator)
- [https://refactoring.guru/design-patterns/decorator](https://refactoring.guru/design-patterns/decorator)

### Videos

- https://youtu.be/tUmJmsXefL8