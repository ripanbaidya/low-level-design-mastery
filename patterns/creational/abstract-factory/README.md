## Abstract Factory Pattern

<p align="right">Last Updated: 30.06.2026</p>

## Introduction

The **Abstract Factory Pattern** is a **Creational Design Pattern** that provides an interface for creating **families of related or dependent objects** without specifying their concrete classes.

Unlike the Factory Method pattern, which focuses on creating a **single product**, the Abstract Factory pattern creates **multiple related products** that are designed to work together.

The client interacts only with the abstract factory and abstract product interfaces, making the application independent of the concrete implementations.

This pattern is especially useful when a system must support multiple product families while ensuring that objects from different families are never mixed together.

## The Problem

Imagine you're building an e-commerce platform that supports multiple payment providers.

Initially, the application integrates with **Stripe**.

Every payment provider requires three components:

- Payment Processor
- Refund Processor
- Webhook Handler

The client creates them directly.

```java
PaymentProcessor paymentProcessor = new StripePaymentProcessor();
RefundProcessor refundProcessor = new StripeRefundProcessor();
WebhookHandler webhookHandler = new StripeWebhookHandler();
```

Everything works correctly.

Now the business decides to support **Razorpay** in addition to Stripe.

Without a proper design, developers might accidentally mix implementations.

```java
PaymentProcessor paymentProcessor = new StripePaymentProcessor();

RefundProcessor refundProcessor = new RazorpayRefundProcessor();

WebhookHandler webhookHandler = new StripeWebhookHandler();
```

Although the code compiles successfully, it introduces a serious business problem.

- Payments are processed using Stripe.
- Refunds are processed using Razorpay.
- Webhooks are handled by Stripe again.

The application is now working with objects that were never designed to work together.

These kinds of inconsistencies often lead to production issues that are difficult to diagnose.

## Problems Without Abstract Factory

- Client code directly depends on concrete implementations.
- Product families can accidentally be mixed.
- Switching providers requires changes throughout the application.
- Object creation logic becomes scattered across the codebase.
- Maintaining consistency becomes increasingly difficult as more providers are introduced.

## The Solution

Instead of allowing the client to create each component individually, delegate the responsibility to a factory.

Each payment provider has its own factory that creates every component belonging to that provider.

For example,

A **Stripe factory** always creates:

- StripePaymentProcessor
- StripeRefundProcessor
- StripeWebhookHandler

A **Razorpay factory** always creates:

- RazorpayPaymentProcessor
- RazorpayRefundProcessor
- RazorpayWebhookHandler

The client simply chooses one factory.

```java
PaymentGatewayFactory factory = new StripePaymentGatewayFactory();
```

Every object produced by the factory automatically belongs to the Stripe family.

This guarantees consistency throughout the application.

## How Abstract Factory Works

The client never creates concrete objects directly.

Instead, the workflow looks like this.

```text
Client
      │
      ▼
PaymentGatewayFactory
      │
      ├──────────────► PaymentProcessor
      │
      ├──────────────► RefundProcessor
      │
      └──────────────► WebhookHandler
```

Changing the entire payment provider requires changing only one line.

```java
PaymentGatewayFactory factory =
        new RazorpayPaymentGatewayFactory();
```

Everything else continues to work without modification.

## Components

### Abstract Products

Abstract Products define the contracts shared by all concrete products.

In our example, they are

- PaymentProcessor
- RefundProcessor
- WebhookHandler

Every payment provider implements these interfaces.

### Concrete Products

Concrete Products provide provider-specific implementations.

For the Stripe family,

- StripePaymentProcessor
- StripeRefundProcessor
- StripeWebhookHandler

For the Razorpay family,

- RazorpayPaymentProcessor
- RazorpayRefundProcessor
- RazorpayWebhookHandler

Each family is designed to work together.

### Abstract Factory

The Abstract Factory declares methods for creating every product in a family.

```java
public interface PaymentGatewayFactory {

    PaymentProcessor createPaymentProcessor();

    RefundProcessor createRefundProcessor();

    WebhookHandler createWebhookHandler();
}
```

Notice that it never mentions Stripe or Razorpay.

It only returns abstractions.

### Concrete Factories

Each concrete factory creates one complete family of products.

For example,

```text
StripePaymentGatewayFactory

├── StripePaymentProcessor
├── StripeRefundProcessor
└── StripeWebhookHandler
```

Similarly,

```text
RazorpayPaymentGatewayFactory

├── RazorpayPaymentProcessor
├── RazorpayRefundProcessor
└── RazorpayWebhookHandler
```

A concrete factory guarantees that compatible products are created together.

### Client

The client depends only on the abstract factory.

```java
PaymentGatewayFactory factory =
        new StripePaymentGatewayFactory();
```

It never creates concrete implementations directly.

As a result, switching providers becomes effortless.

## UML Representation

![UML](/resources/images/patterns/creational/abstract-factory-uml.png)

## Implementation

```text
app
├── product
│   ├── PaymentProcessor.java
│   ├── RefundProcessor.java
│   └── WebhookHandler.java
│
├── stripe
│   ├── StripePaymentProcessor.java
│   ├── StripeRefundProcessor.java
│   └── StripeWebhookHandler.java
│
├── razorpay
│   ├── RazorpayPaymentProcessor.java
│   ├── RazorpayRefundProcessor.java
│   └── RazorpayWebhookHandler.java
│
├── factory
│   ├── PaymentGatewayFactory.java
│   ├── StripePaymentGatewayFactory.java
│   └── RazorpayPaymentGatewayFactory.java
│
└── client
    └── Application.java
```

### Abstract Products

**PaymentProcessor**

```java
public interface PaymentProcessor {

    void processPayment(double amount);
}
```

**RefundProcessor**

```java
public interface RefundProcessor {

    void processRefund(String transactionId);
}
```

**WebhookHandler**

```java
public interface WebhookHandler {

    void handleWebhook(String payload);
}
```

### Stripe Family Implementation: Concrete Product A

**StripePaymentProcessor**

```java
public class StripePaymentProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment via Stripe: ₹" + amount);
    }
}
```

**StripeRefundProcessor**

```java
public class StripeRefundProcessor implements RefundProcessor {

    @Override
    public void processRefund(String transactionId) {
        System.out.println("Processing refund via Stripe for transaction: " + transactionId);
    }
}
```

**StripeWebhookHandler**

```java
public class StripeWebhookHandler implements WebhookHandler {

    @Override
    public void handleWebhook(String payload) {
        System.out.println("Handling Stripe webhook: " + payload);
    }
}
```

### Razorpay Family Implementation: Concrete Product B

**RazorpayPaymentProcessor**

```java
public class RazorpayPaymentProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment via Razorpay: ₹" + amount);
    }
}
```

**RazorpayRefundProcessor**

```java
public class RazorpayRefundProcessor implements RefundProcessor {

    @Override
    public void processRefund(String transactionId) {
        System.out.println("Processing refund via Razorpay for transaction: " + transactionId);
    }
}
```

**RazorpayWebhookHandler**

```java
public class RazorpayWebhookHandler implements WebhookHandler {

    @Override
    public void handleWebhook(String payload) {
        System.out.println("Handling Razorpay webhook: " + payload);
    }
}
```

### Abstract Factory

```java
public interface PaymentGatewayFactory {

    PaymentProcessor createPaymentProcessor();

    RefundProcessor createRefundProcessor();

    WebhookHandler createWebhookHandler();
}
```

### Concrete Factories

**Stripe Factory**

```java
public class StripePaymentGatewayFactory implements PaymentGatewayFactory {

    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new StripePaymentProcessor();
    }

    @Override
    public RefundProcessor createRefundProcessor() {
        return new StripeRefundProcessor();
    }

    @Override
    public WebhookHandler createWebhookHandler() {
        return new StripeWebhookHandler();
    }
}
```

**Razorpay Factory**

```java
public class RazorpayPaymentGatewayFactory implements PaymentGatewayFactory {

    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new RazorpayPaymentProcessor();
    }

    @Override
    public RefundProcessor createRefundProcessor() {
        return new RazorpayRefundProcessor();
    }

    @Override
    public WebhookHandler createWebhookHandler() {
        return new RazorpayWebhookHandler();
    }
}
```

### Client Code

```java
public class EcommerceApplication {

    public static void main(String[] args) {

        // Choose payment provider
        PaymentGatewayFactory factory = new StripePaymentGatewayFactory();

        PaymentProcessor paymentProcessor = factory.createPaymentProcessor();
        RefundProcessor refundProcessor = factory.createRefundProcessor();
        WebhookHandler webhookHandler = factory.createWebhookHandler();

        paymentProcessor.processPayment(5000);
        refundProcessor.processRefund("TXN12345");
        webhookHandler.handleWebhook("payment_success_event");
    }
}
```

## Output

```
Processing payment via Stripe: ₹5000.0
Processing refund via Stripe for transaction: TXN12345
Handling Stripe webhook: payment_success_event
```

## Execution Flow

Suppose Stripe is selected.

The execution flow becomes

```text
EcommerceApplication
        │
        ▼
StripePaymentGatewayFactory
        │
        ├────────► StripePaymentProcessor
        │
        ├────────► StripeRefundProcessor
        │
        └────────► StripeWebhookHandler
```

If Razorpay is selected instead,

```text
EcommerceApplication
        │
        ▼
RazorpayPaymentGatewayFactory
        │
        ├────────► RazorpayPaymentProcessor
        │
        ├────────► RazorpayRefundProcessor
        │
        └────────► RazorpayWebhookHandler
```

Only the factory changes.

The client code remains exactly the same.


## Factory Method vs Abstract Factory

Many developers confuse these two patterns because both deal with object creation.

| Factory Method             | Abstract Factory                           |
| -------------------------- | ------------------------------------------ |
| Creates one product        | Creates a family of related products       |
| One factory method         | Multiple factory methods                   |
| Focuses on one object      | Focuses on object compatibility            |
| Uses inheritance           | Usually uses composition                   |
| Example: EmailNotification | Example: Stripe Payment + Refund + Webhook |

A simple way to remember the difference is:

- **Factory Method creates one product.**
- **Abstract Factory creates multiple products that belong together.**

## Real-World Use Cases

The Abstract Factory pattern is commonly used in systems that support multiple implementations of related components.

Examples include:

- Payment gateways (Stripe, Razorpay, PayPal)
- Database providers (MySQL, PostgreSQL, Oracle)
- Cloud providers (AWS, Azure, Google Cloud)
- Cross-platform GUI toolkits (Windows, macOS, Linux)
- Messaging providers (Kafka, RabbitMQ, ActiveMQ)
- Logging frameworks
- Authentication providers (OAuth, LDAP, SAML)

## Trade-Off Analysis

### Advantages

- Guarantees product compatibility.
- Prevents mixing incompatible implementations.
- Encapsulates object creation.
- Supports the Open/Closed Principle.
- Makes switching entire product families easy.
- Improves maintainability and scalability.

### Disadvantages

- Introduces many interfaces and classes.
- Adding a new product type requires updating every concrete factory.
- Increases architectural complexity for small applications.
- Can be unnecessary when only one product family exists.

## When to Use vs. When to Avoid

### Use when

Use the Abstract Factory pattern when:

- Multiple related objects must always be used together.
- You need to prevent incompatible implementations from being mixed.
- The application supports multiple product families.
- The client should remain independent of concrete implementations.
- Switching between implementations should require minimal code changes.

### Avoid when

Avoid the Abstract Factory pattern when:

- The application has only one product family.
- Products are unrelated to one another.
- A Factory Method is sufficient.
- The additional abstraction introduces unnecessary complexity.

## Conclusion

The Abstract Factory Pattern provides a consistent way to create **families of related objects** without exposing their concrete implementations.

By delegating object creation to factories, the client works entirely with abstractions while each concrete factory guarantees that compatible products are created together.

This approach improves maintainability, prevents accidental mixing of incompatible implementations, and makes it easy to switch entire product families by changing only the selected factory. Although the pattern introduces additional classes, it becomes invaluable in large applications that support multiple providers, platforms, or environments.
