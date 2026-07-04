## Abstraction in Object-Oriented Programming (OOP)

<p align="right">Last updated - 04.07.2026</p>

## Introduction

**Abstraction** is one of the four fundamental principles of Object-Oriented Programming (OOP). It allows you to hide **implementation details** while exposing only the necessary parts of an object. This helps in reducing complexity and increasing maintainability.

Abstraction in Java is mainly achieved using:

1. **Abstract Classes**
2. **Interfaces**

## What is Abstraction?

**Abstraction** means showing only the **essential details** and hiding the **implementation**. It allows programmers to focus on **what an object does** rather than **how it does it**.

### Key Benefits of Abstraction

- **Reduces complexity**: Hides unnecessary implementation details.
- **Increases code reusability**: Encourages the reuse of abstracted logic.
- **Enhances security**: Protects internal object details from unintended modifications.
- **Improves maintainability**: Makes code easier to manage and update.

### 1. Abstraction Using Abstract Classes

An **abstract class** in Java is a class that cannot be instantiated. It is used to define common behavior that multiple subclasses should implement.

### Example

```java
// Abstract class
abstract class Vehicle {
    String brand;

    // Constructor
    Vehicle(String brand) {
        this.brand = brand;
    }

    // Abstract method (must be implemented by subclasses)
    abstract void start();

    // Concrete method (can be inherited)
    void displayBrand() {
        System.out.println("Brand: " + brand);
    }
}

// Subclass implementing the abstract method
class Car extends Vehicle {
    Car(String brand) {
        super(brand);
    }

    @Override
    void start() {
        System.out.println("Car is starting...");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle myCar = new Car("Toyota");
        myCar.displayBrand();
        myCar.start();
    }
}
```

Output:

```
Brand: Toyota
Car is starting...
```

Why Use Abstract Classes?

- Allows defining common behavior that subclasses must implement.
- Enables partial abstraction (can have both abstract and concrete methods).
- Prevents direct instantiation of base classes.

### 2. Abstraction Using Interfaces

An **interface** in Java is a blueprint that defines a contract for classes to follow. It contains **only abstract methods** (until Java 8 introduced default and static methods).

### Example

```java
// Defining an interface
interface Animal {
    void makeSound(); // Abstract method
}

// Implementing the interface in Dog class
class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

// Implementing the interface in Cat class
class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal myDog = new Dog();
        myDog.makeSound();

        Animal myCat = new Cat();
        myCat.makeSound();
    }
}
```

### **Output:**

```
Dog barks
Cat meows
```

**Why Use Interfaces?**

- Promotes **full abstraction** (hides all implementation details).
- Supports **multiple inheritance** in Java (a class can implement multiple interfaces).
- Provides a standard way for different classes to implement behaviors.

## Abstract Class vs Interface - Architectural Comparison

Choosing between an abstract class and an interface dictates how your system scales. Use the architectural comparison below to guide your design choices.

| Architectural Feature    | Abstract Class                                           | Interface                                                    |
| ------------------------ | -------------------------------------------------------- | ------------------------------------------------------------ |
| **Relationship Model**   | **IS-A** relationship (Strict hierarchy)                 | **CAN-DO** relationship (Capabilities/Behavior)              |
| **Inheritance Limit**    | Single inheritance (A class can extend only one class)   | Multiple inheritance (A class can implement many interfaces) |
| **State Management**     | Can hold instance fields (mutable, private, protected)   | Constants only (`public static final`)                       |
| **Constructors**         | Allowed; used to initialize base class state             | Banned; cannot hold instantiable state                       |
| **Evolution/Changes**    | Safe to add concrete methods without breaking subclasses | Adding a standard abstract method breaks implementers        |
| **Behavioral Injection** | Restricted to the inheritance tree                       | Allows `default` (Java 8+) and `private` (Java 9+) methods   |

## Real-World Example: Payment System

Abstraction is widely used in real-world applications, such as payment processing.

### Example: Payment System with Abstraction

```java
// Abstract class for Payment
abstract class Payment {
    double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    abstract void pay(); // Abstract method
}

// Implementing payment methods
class CreditCardPayment extends Payment {
    CreditCardPayment(double amount) {
        super(amount);
    }

    @Override
    void pay() {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PayPalPayment extends Payment {
    PayPalPayment(double amount) {
        super(amount);
    }

    @Override
    void pay() {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

public class Main {
    public static void main(String[] args) {
        Payment payment;

        payment = new CreditCardPayment(150.75);
        payment.pay();

        payment = new PayPalPayment(200.50);
        payment.pay();
    }
}
```

Output:

```
Paid 150.75 using Credit Card
Paid 200.50 using PayPal
```

**Why Use Abstraction in Payment Systems?**

- Allows multiple payment methods without modifying existing code.
- Improves maintainability and scalability.
- Provides a **common contract** for different payment types.

## Resources

- https://www.baeldung.com/java-abstract-class
- https://www.baeldung.com/java-interface-vs-abstract-class
