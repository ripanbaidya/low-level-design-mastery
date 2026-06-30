## Prototype Design Pattern

<p align="right">Last updated - 30.06.2026</p>

The **Prototype Pattern** is a **Creational Design Pattern** that allows new objects to be created by copying an existing object instead of constructing one from scratch.

Rather than repeatedly executing expensive initialization logic, the application creates one fully configured object (called a **prototype**) and clones it whenever a similar object is required.

This pattern is particularly useful when object creation is expensive, complex, or when many objects share almost identical configurations.

## Introduction

In many applications, creating an object isn't as simple as calling the `new` keyword.

An object may require:

- Reading configuration from files
- Fetching data from a database
- Calling external APIs
- Creating multiple nested objects
- Performing validation or preprocessing

Executing all of this initialization every time a new object is needed wastes CPU time and memory.

Instead of rebuilding the object repeatedly, we can build it **once**, keep it as a prototype, and create new objects by cloning it.

> **Core Idea:** Configure an object once and use it as a blueprint for creating similar objects.

## The Problem

Imagine an architect designing luxury apartments.

Most customers choose the same base apartment design:

- Same floor plan
- Same kitchen layout
- Same bathroom design
- Same electrical wiring
- Same furniture arrangement

Only a few things differ between customers:

- Wall color
- Flooring
- Curtains
- Sofa style

Without the Prototype pattern, every customer's apartment is designed from scratch.

```java
Apartment apartment1 = new Apartment(
    new FloorPlan(...),
    new Kitchen(...),
    new Furniture(...),
    "White"
);

Apartment apartment2 = new Apartment(
    new FloorPlan(...),
    new Kitchen(...),
    new Furniture(...),
    "Blue"
);
```

Although 95% of the apartment is identical, the entire construction process is repeated every time.

## Problems Without Prototype

- **Expensive Object Creation** – The same complex initialization logic executes repeatedly.
- **Code Duplication** – Client code repeatedly creates nearly identical objects.
- **Tight Coupling** – Clients must understand every detail of object construction.

## The Solution

Instead of creating every apartment from scratch, the architect prepares one **master blueprint**.

Whenever a new customer arrives, the blueprint is cloned.

Only customer-specific customizations are applied afterward.

```java
Apartment luxuryTemplate = new Apartment(...);

Apartment customerApartment = luxuryTemplate.clone();
customerApartment.setWallColor("Blue");
```

The original blueprint remains unchanged while every customer receives an independent copy.

This is exactly how the Prototype pattern works.

## Shallow Copy vs Deep Copy

Understanding cloning requires understanding how objects are copied in memory.

There are two ways of copying objects, _Shallow Copy_ & _Deep Copy_.

### Shallow Copy

A shallow copy duplicates only the top-level object.
Any nested reference objects are **shared** between the original and the clone.

Suppose an apartment contains a `Furniture` object.

```
Apartment
├── Wall Color
└── Furniture
      ├── Sofa
      ├── Dining Table
      └── Bed
```

When a shallow copy is created:

```
Original Apartment
        |
        |------> Furniture
        |
Cloned Apartment
```

Both apartments point to the **same Furniture object**.

If the customer replaces the sofa:

```java
clonedApartment.getFurniture().setSofa("Leather Sofa");
```

The original apartment now also contains the leather sofa because both apartments share the same `Furniture` instance. This side effect is usually undesirable.

#### Example of Shallow Copy

```java
class Apartment implements Cloneable {

    private String wallColor;
    private Furniture furniture;

    public Apartment(String wallColor, Furniture furniture) {
        this.wallColor = wallColor;
        this.furniture = furniture;
    }

    @Override
    protected Apartment clone() throws CloneNotSupportedException {
        // Performs a shallow copy
        return (Apartment) super.clone();
    }
}
```

Since `super.clone()` only copies references, both apartments share the same `Furniture` object.

### Deep Copy

A deep copy duplicates both the parent object **and** every nested object it contains.

```
Original Apartment
      |
      |------> Furniture

Cloned Apartment
      |
      |------> Furniture (New Copy)
```

Now each apartment owns its own furniture.

Changing the sofa inside the cloned apartment:

```java
clonedApartment.getFurniture().setSofa("Leather Sofa");
```

does **not** affect the original apartment.

```
Original Apartment
    Sofa -> Fabric Sofa

Cloned Apartment
    Sofa -> Leather Sofa
```

Deep copying completely isolates both objects.

#### Example of Deep Copy

```java
@Override
protected Apartment clone() throws CloneNotSupportedException {

    Apartment cloned = (Apartment) super.clone();

    cloned.furniture = new Furniture(this.furniture);

    return cloned;
}
```

Here a new `Furniture` object is created, preventing both apartments from sharing the same reference.

> **Production Recommendation**
>
> Avoid relying heavily on Java's built-in `Cloneable` interface and `Object.clone()`. They perform shallow copies by default and bypass constructors, making them error-prone.
>
> A better approach is to implement cloning using **copy constructors** or custom cloning methods.

## Structural Components

| Component               | Responsibility                                        |
| ----------------------- | ----------------------------------------------------- |
| **Prototype Interface** | Declares the cloning operation.                       |
| **Concrete Prototype**  | Implements the cloning logic.                         |
| **Client**              | Creates new objects by cloning an existing prototype. |

## UML Representation

![UML](/resources/images/patterns/creational/prototype-uml.png)

## Implementation

### 1. Prototype Interface

```java
public interface Prototype<T> {

    T clone();

}
```

### 2. Furniture

```java
public class Furniture implements Prototype<Furniture> {

    private String sofa;

    public Furniture(String sofa) {
        this.sofa = sofa;
    }

    public Furniture(Furniture target) {
        this.sofa = target.sofa;
    }

    public void setSofa(String sofa) {
        this.sofa = sofa;
    }

    @Override
    public Furniture clone() {
        return new Furniture(this);
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "sofa='" + sofa + '\'' +
                '}';
    }
}
```

### 3. Apartment

```java
public class Apartment implements Prototype<Apartment> {

    private String wallColor;
    private Furniture furniture;

    public Apartment(String wallColor, Furniture furniture) {
        this.wallColor = wallColor;
        this.furniture = furniture;
    }

    public Apartment(Apartment target) {

        if (target != null) {
            this.wallColor = target.wallColor;

            // Deep Copy
            this.furniture = target.furniture.clone();
        }
    }

    public void setWallColor(String wallColor) {
        this.wallColor = wallColor;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    @Override
    public Apartment clone() {
        return new Apartment(this);
    }

    public void display() {
        System.out.println(
                "Wall Color : " + wallColor +
                ", " + furniture
        );
    }
}
```

### 4. Client

```java
public class Application {

    public static void main(String[] args) {

        Apartment luxuryBlueprint =
                new Apartment(
                        "White",
                        new Furniture("Fabric Sofa")
                );

        Apartment customerOne = luxuryBlueprint.clone();

        customerOne.setWallColor("Blue");
        customerOne.getFurniture().setSofa("Leather Sofa");

        Apartment customerTwo = luxuryBlueprint.clone();

        customerTwo.setWallColor("Grey");

        System.out.println("Blueprint");
        luxuryBlueprint.display();

        System.out.println();

        System.out.println("Customer One");
        customerOne.display();

        System.out.println();

        System.out.println("Customer Two");
        customerTwo.display();
    }
}
```

## Output

```text
Blueprint
Wall Color : White, Furniture{sofa='Fabric Sofa'}

Customer One
Wall Color : Blue, Furniture{sofa='Leather Sofa'}

Customer Two
Wall Color : Grey, Furniture{sofa='Fabric Sofa'}
```

Notice that changing the sofa in **Customer One's** apartment does not affect either the blueprint or **Customer Two's** apartment because each apartment owns its own `Furniture` object.

## Real-World Use Cases

- **Document editors** – Duplicate templates for invoices, resumes, or reports.
- **Graphic design software** – Clone shapes, layers, and design components.
- **Game development** – Spawn enemies, weapons, or NPCs from preconfigured templates.
- **CAD and architecture software** – Duplicate building blueprints and customize them for different clients.
- **ORM frameworks** – Copy entity states before applying modifications.

## Trade-Off Analysis

### Advantages

- Avoids expensive object creation.
- Reduces repetitive initialization code.
- Simplifies creating similar objects.
- Hides complex construction logic from clients.
- Supports runtime object configuration.

### Disadvantages

- Deep cloning can become difficult for large object graphs.
- Circular object references require careful handling.
- Incorrect cloning may accidentally share mutable objects.
- Maintaining clone logic becomes harder as the class grows.

## When to Use vs. When to Avoid

### Use when:

- Object creation is expensive.
- Many objects share almost identical state.
- Creating new instances repeatedly hurts performance.
- You want to avoid exposing complex construction logic.

### Avoid when:

- Objects are lightweight and inexpensive to create.
- Objects are immutable.
- Cloning logic is more complicated than simply constructing a new object.


## FAQs

**Q: Explain Prototype Design pattern in brief?**

Ans: Prototype Design pattern provides us a way to create new objects by using existing objects. In other words, we can copy data from the existing object and store it in our new object by using this pattern.

**Q: Explain Shallow Copy and Deep Copy in Prototype Design patterns?**

Ans: Shallow Copy and Deep Copy are types of cloning in Prototype Design patterns. In shallow copy, we only cloned the parent object and not its containing objects while in deep copy, we cloned the parent object as well as its containing objects.