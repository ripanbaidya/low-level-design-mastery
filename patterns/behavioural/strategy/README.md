## Strategy Design Pattern

<p align="right">Last updated - 30.06.2026</p>

## Introduction

Have you ever looked at a massive block of nested `if-else` or `switch` statements and felt a sudden wave of dread? We've all been there. As applications grow, conditional logic can quickly spiral out of control, making your codebase rigid, brittle, and terrifying to modify.

Enter the **Strategy Pattern**.

The Strategy Pattern is a **behavioral design pattern** that solves this exact headache.

> **The Core Definition:**
> The Strategy Pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime. It allows the algorithm to vary independently from the clients that use it.

In plain English: It lets you swap out the behavior of a system on the fly, without rewriting or breaking the system itself.

## Real-Life Analogy: The Navigation App

Imagine you are building **Google Maps**. When a user wants to get from Point A to Point B, they need a route.

Depending on their situation, they might select different modes of transit:

- 🚗 **Car Strategy:** Optimizes for highways, avoids traffic jams.
- 🚲 **Bike Strategy:** Prefers bike lanes, avoids steep hills.
- 🚶 **Walk Strategy:** Looks for pedestrian walkways and shortcuts through parks.

The core application framework remains completely identical: the map UI is the same, the GPS tracker is the same, and the destination pin is the same. The only thing changing is the **routing algorithm** underneath.

Because these algorithms are decoupled from the main app, you can easily add a **✈️ Flight Strategy** or a **🚊 Public Transit Strategy** later without touching a single line of your map rendering logic. That is the essence of the Strategy Pattern.

## The Problem: The Dreaded `if-else` Trap

Let’s say we are tasked with building a high-throughput **E-Commerce Payment Gateway** that processes transactions via **Credit Card**, **UPI**, and **PayPal**.

Without thinking about design patterns, your first instinct might be to write code like this:

```java
public class PaymentService {
    public void processPayment(String paymentType, BigDecimal amount) {
        if (paymentType.equalsIgnoreCase("CREDIT_CARD")) {
            System.out.println("Validating card limits...");
            System.out.println("Charging Credit Card: ₹" + amount);
        } else if (paymentType.equalsIgnoreCase("UPI")) {
            System.out.println("Generating UPI Intent QR Code...");
            System.out.println("Processing UPI transaction: ₹" + amount);
        } else if (paymentType.equalsIgnoreCase("PAYPAL")) {
            System.out.println("Redirecting to PayPal secure sandbox...");
            System.out.println("Charging PayPal account: ₹" + amount);
        } else {
            throw new IllegalArgumentException("Unsupported payment method!");
        }
    }
}

```

### Why is this bad software engineering?

- **Violates the Open/Closed Principle (OCP):** If the business decides to support _Crypto Payments_ tomorrow, you must open this existing class and modify its core logic. Any typo here could break your entire payment infrastructure.
- **Poor Testability:** You cannot unit test the UPI logic without dragging along the dependencies and variables required for Credit Cards.
- **High Cognitive Load:** As more payment methods are added, this single file turns into a 2,000-line "God Class" that no one wants to maintain.

## The Solution: Structural Breakdown ⚙️

The Strategy Pattern breaks this problem down into four highly cooperative components:

| Component                   | Role                                                                              | Real-World Analogy                                        |
| --------------------------- | --------------------------------------------------------------------------------- | --------------------------------------------------------- |
| **Strategy (Interface)** | The contract defining **what** needs to be done.                                  | The general idea of "Paying".                             |
| **Concrete Strategy**    | The specific implementation of **how** it is done.                                | The exact mechanics of a UPI vs. Credit Card transaction. |
| **Context**              | The engine that **uses** the strategy. It holds a reference to a Strategy object. | The Checkout Cart/Billing System.                         |
| **Client**               | The orchestrator that **chooses** the strategy and passes it to the Context.      | The actual user clicking the "Pay via UPI" button.        |

## UML Representation

![](/resources/images/patterns/behavioural/strategy.png)

## Implementation

Let's clean up our payment system using modern, robust Java. We'll separate our concerns cleanly into packages.

### 1. Strategy Interface

This defines our common execution standard.

```java
import java.math.BigDecimal;

public interface PaymentStrategy {
    void pay(BigDecimal amount);
}

```

### 2. Concrete Strategies

Each strategy is an isolated, single-purpose class that is incredibly easy to test or replace.

```java
import java.math.BigDecimal;
import java.util.Objects;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final String cardHolderName;

    public CreditCardPaymentStrategy(String cardNumber, String cardHolderName) {
        this.cardNumber = Objects.requireNonNull(cardNumber, "Card number cannot be null");
        this.cardHolderName = Objects.requireNonNull(cardHolderName, "Holder name cannot be null");
    }

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("💳 Processing credit card payment...");
        System.out.println("   Holder: " + cardHolderName + " | Card: XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("   Amount Paid: ₹" + amount);
    }
}

```

```java
import java.math.BigDecimal;
import java.util.Objects;

public class UpiPaymentStrategy implements PaymentStrategy {
    private final String upiId;

    public UpiPaymentStrategy(String upiId) {
        this.upiId = Objects.requireNonNull(upiId, "UPI ID cannot be null");
    }

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("📱 Processing UPI payment...");
        System.out.println("   VPA: " + upiId);
        System.out.println("   Amount Paid: ₹" + amount);
    }
}

```

```java
import java.math.BigDecimal;
import java.util.Objects;

public class PaypalPaymentStrategy implements PaymentStrategy {
    private final String email;

    public PaypalPaymentStrategy(String email) {
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("🏦 Processing PayPal payment...");
        System.out.println("   Account: " + email);
        System.out.println("   Amount Paid: ₹" + amount);
    }
}

```

### 3. Context

The Context doesn’t care _how_ a payment happens. It just accepts a strategy and tells it to run.

```java
import com.designpatterns.strategy.strategy.PaymentStrategy;
import java.math.BigDecimal;
import java.util.Objects;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    // Inject strategy via Constructor
    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "Initial strategy cannot be null");
    }

    // Allow dynamic runtime switching via Setter
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "Strategy cannot be null");
    }

    public void executePayment(BigDecimal amount) {
        paymentStrategy.pay(amount);
    }
}

```

### 4. Simple Strategy Factory

To shield our client from complex instantiation details, we hide creation behind a clean Factory utility.

```java
import com.designpatterns.strategy.strategy.*;

public final class PaymentStrategyFactory {
    private PaymentStrategyFactory() {} // Prevent instantiation

    public static PaymentStrategy createCreditCardStrategy(String cardNumber, String cardHolderName) {
        return new CreditCardPaymentStrategy(cardNumber, cardHolderName);
    }

    public static PaymentStrategy createUpiStrategy(String upiId) {
        return new UpiPaymentStrategy(upiId);
    }

    public static PaymentStrategy createPaypalStrategy(String email) {
        return new PaypalPaymentStrategy(email);
    }
}

```

### 5. Client Application

The orchestrator that wires everything together. Watch how seamlessly we change payment mechanics at runtime!

```java
import com.designpatterns.strategy.context.PaymentContext;
import com.designpatterns.strategy.factory.PaymentStrategyFactory;
import com.designpatterns.strategy.strategy.PaymentStrategy;
import java.math.BigDecimal;

public class StrategyApplication {
    public static void main(String[] args) {
        System.out.println("=== Welcome to the Checkout Counter ===\n");

        // 1. Client decides to use Credit Card
        PaymentStrategy cardStrategy = PaymentStrategyFactory
                .createCreditCardStrategy("1234-5678-9876-5432", "Ripan Baidya");

        PaymentContext context = new PaymentContext(cardStrategy);
        context.executePayment(BigDecimal.valueOf(1500.00));

        System.out.println("\n--- User changes mind & switches method ---\n");

        // 2. Dynamic switch to UPI at runtime
        PaymentStrategy upiStrategy = PaymentStrategyFactory.createUpiStrategy("ripan@upi");
        context.setPaymentStrategy(upiStrategy);
        context.executePayment(BigDecimal.valueOf(750.50));
    }
}

```

### Output

```text
Processing credit card payment...
Card Holder: Ripan Baidya
Amount Paid: ₹1500.0

--- User changes mind & switches method ---

Processing UPI payment...
UPI ID: ripan@upi
Amount Paid: ₹750.5
```

## When to Use and When Not to Use

### Use when:

- **Runtime Swapping Needed:** Use this when an object must perform variations of an action dynamically based on state, user preferences, or configurations.
- **Isolating Core Logic from Data:** If your algorithms use private, platform-dependent data, the pattern isolates this execution logic away from the main application flow.
- **Eliminating Conditional Bloat:** If you see a complex `switch` or `if-else` blocks controlling variations of the exact same task, refactor immediately to Strategy.

### NOT to Use when:

- **Static Behaviors:** If you only have one or two algorithms that almost never change, introducing Strategy is over-engineering. Stick to a simple method.
- **Functional Alternatives Exist:** In modern functional programming languages (or Java 8+ using Lambdas), you can pass a function or method reference as a parameter directly instead of generating entire files for a simple 1-line behavior.

## Trade-offs Analysis

### The Advantages

1. **Strict Adherence to SOLID:** You can add new strategies without changing existing code (Open/Closed) and isolate distinct code behaviors cleanly (Single Responsibility).
2. **Runtime Flexibility:** Allows an application to react gracefully to user inputs on the fly.
3. **Encourages Composition over Inheritance:** Avoids rigid class hierarchies created by relying on child classes to modify parent behaviors.

### The Disadvantages

1. **Explosion of Classes:** If you have 20 different behaviors, you now have 20 new `.java` files to maintain.
2. **Client-Side Awareness:** The client application _must_ know how strategies differ to choose the correct one. It leaks business logic context up to the controller layer.

## 💡 Crushing the Design Pattern Interview

When an interviewer brings up the Strategy Pattern, distinguish yourself by offering these quick, expert points:

> - **The Punchy Summary:** "The Strategy pattern is about favoring object composition over inheritance. It decouples the context from algorithmic implementation details by leaning on an interface contract."

> - **JDK Real-World Example:** Don't just give the payment example; point out that Java uses it natively! `java.util.Comparator#compare()` is a classic example of the Strategy Pattern. Every time you pass a custom sorting comparator to `Collections.sort()`, you are providing a structural sorting strategy.

> - **The Dynamic Key:** "The magic of the Strategy pattern isn't just decoupling; it’s the fact that changing strategies happens dynamically at _runtime_ using structural setters, instead of locking down logic at compilation."