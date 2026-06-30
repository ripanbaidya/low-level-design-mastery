## Factory Design Pattern

<p align="right">Last updated - 30.06.2026</p>

## Introduction

The **Factory Pattern** is a **Creational Design Pattern** that defines an interface (or abstract class) for creating objects but allows subclasses to decide which concrete object should be instantiated.

Instead of creating objects directly using the `new` keyword throughout the application, the responsibility of object creation is delegated to specialized factory classes.

This provides two important benefits:

- The client depends on abstractions rather than concrete implementations.
- New object types can be introduced without modifying existing client code.

The Factory Method pattern follows the **Open/Closed Principle** by making the system open for extension while closed for modification.

## The Problem

Imagine you're building a notification service for an application.

Initially, the application only supports email notifications.

```java
Notification notification = new EmailNotification();
notification.send("Welcome!");
```

Everything works fine.<br>
Later, the business requirements change.

The application must now support:

- Email notifications
- Push notifications
- SMS notifications
- WhatsApp notifications

A straightforward solution would be:

```java
if(type.equals("EMAIL")){
    notification = new EmailNotification();
}
else if(type.equals("PUSH")){
    notification = new PushNotification();
}
else if(type.equals("SMS")){
    notification = new SmsNotification();
}
```

Although this works, it introduces several problems.

### Problems

- Client code becomes tightly coupled to concrete classes.
- Every new notification type requires modifying existing code.
- Object creation logic becomes scattered throughout the application.
- The application gradually violates the **Open/Closed Principle**.

As the number of notification types grows, maintaining the system becomes increasingly difficult.

## The Solution

Instead of allowing the client to decide which notification object to create, the Factory Method pattern delegates that responsibility to factory classes.

Each factory knows how to create exactly one type of notification.

The client only interacts with the factory.

```java
NotificationFactory factory = new EmailNotificationFactory();

factory.sendNotification("Welcome!");
```

If tomorrow you introduce `SmsNotification`, you simply create a new `SmsNotificationFactory`.

No existing client code needs to change and this makes the system easier to maintain and extend.

## How Factory Method Works

The pattern separates object creation from object usage.

The workflow is straightforward:

1. The client chooses a factory.
2. The factory creates the appropriate product.
3. The client uses the product through its interface.
4. The client never knows which concrete class was instantiated.

Instead of writing:

```text
Client
   ↓
new EmailNotification()
```

the flow becomes:

```text
Client
   ↓
NotificationFactory
   ↓
createNotification()
   ↓
EmailNotification
```

The client depends only on the abstraction.

## Components

### Product

The Product defines the common interface shared by all objects created by the factory.

Every concrete product implements this interface.

```java
public interface Notification {
    void send(String message);
}
```

Because all notification types implement the same interface, the client can work with any notification without knowing its implementation.

### Concrete Products

Concrete Products provide the actual implementation of the Product interface.

Examples:

- EmailNotification
- PushNotification
- SmsNotification

Each class implements its own notification behavior while exposing the same interface.

### Creator

The Creator declares the factory method. It may also contain common business logic that depends on the Product.

Notice that the Creator never creates concrete products directly. Instead, it delegates that responsibility to subclasses.

```java
public abstract class NotificationFactory {

    protected abstract Notification createNotification();

    public void sendNotification(String message) {

        Notification notification = createNotification();

        notification.send(message);
    }
}
```

The factory method is:

```java
createNotification()
```

### Concrete Creators

Concrete Creators override the factory method.

Each creator returns a different concrete product.

For example:

```text
EmailNotificationFactory
        ↓
EmailNotification
```

```text
PushNotificationFactory
        ↓
PushNotification
```

Each factory knows how to create only one product.

## Client

The client interacts only with the Creator, It never creates products directly.

```java
NotificationFactory factory = new EmailNotificationFactory();

factory.sendNotification("Welcome");
```

This reduces coupling between the client and the implementation classes.

## UML Representation

![UML](/resources/images/patterns/creational/factory-uml.png)

## Implementation

#### Folder Structure

```text
app
├── model
│   └── Notification.java
│
├── implementation
│   ├── EmailNotification.java
│   └── PushNotification.java
│
├── factory
│   ├── NotificationFactory.java
│   ├── EmailNotificationFactory.java
│   ├── PushNotificationFactory.java
│   └── SimpleNotificationFactory.java
│
└── client
    └── FactoryApplication.java
```

### Product Interface

```java
public interface Notification {

  void send(String message);
}
```

### Concrete Products

#### Email Notification

```java
public class EmailNotification implements Notification {

  @Override
  public void send(String message) {
    System.out.println("Sending Email Notification: " + message);
  }
}
```

#### Push Notification

```java
public class PushNotification implements Notification {

  @Override
  public void send(String message) {
    System.out.println("Sending Push Notification: " + message);
  }
}
```

### Simple Factory (Not GoF, but commonly used)

This is NOT the Factory Method pattern officially, It centralizes object creation using condition logic.

```java
public class SimpleNotificationFactory {

public static Notification createNotification(String type) {

    return switch (type.toUpperCase()) {
      case "EMAIL" -> new EmailNotification();
      case "PUSH" -> new PushNotification();
      default -> throw new IllegalArgumentException("Invalid notification type");
    };
  }
}
```

> Problem: It Violates the Open/Closed Principle (needs modification when new type is added).

### Creator (Factory Method Pattern Core)

```java
public abstract class NotificationFactory {

  protected abstract Notification createNotification();

  public void sendNotification(String message) {
    Notification notification = createNotification();
    notification.send(message);
  }
}
```

### Concrete Factories

#### Email Factory

```java
public class EmailNotificationFactory extends NotificationFactory {

  @Override
  protected Notification createNotification() {
    return new EmailNotification();
  }
}
```

#### Push Factory

```java
public class PushNotificationFactory extends NotificationFactory {

  @Override
  protected Notification createNotification() {
    return new PushNotification();
  }
}
```

### Client Code

```java
public class FactoryApplication {
  public static void main(String[] args) {

    NotificationFactory creator = null;

    // Email notification
    creator = new EmailNotificationFactory();
    creator.sendNotification("You got offer from Amazon!");

    // Push notification
    creator = new PushNotificationFactory();
    creator.sendNotification("Congratulations!!");
  }
}
```

## Simple Factory vs Factory Method

Many developers confuse these two patterns.

Although they solve similar problems, they are different.

### Simple Factory

A Simple Factory uses conditional logic to decide which object to create.

```java
switch(type){

case EMAIL:
    return new EmailNotification();

case PUSH:
    return new PushNotification();

}
```

This centralizes object creation, but every new product requires modifying the factory. Therefore, it violates the **Open/Closed Principle**.

It is also **not** one of the original GoF design patterns.

### Factory Method

Factory Method removes conditional logic. Instead of using `if` or `switch`, each product has its own factory.

```text
EmailNotificationFactory
        ↓
EmailNotification

PushNotificationFactory
        ↓
PushNotification
```

Adding a new notification requires creating a new factory class instead of modifying existing ones.

This makes the design extensible.

### Why Factory Method Is Better Than Simple Factory

| Simple Factory                   | Factory Method                                            |
| -------------------------------- | --------------------------------------------------------- |
| Uses conditional statements      | Uses polymorphism                                         |
| One factory creates every object | Every product has its own factory                         |
| Violates Open/Closed Principle   | Follows Open/Closed Principle                             |
| Requires modifying existing code | New factories can be added without changing existing ones |
| Suitable for small applications  | Better for scalable applications                          |

## Execution Flow

Suppose the client wants to send an email notification.

The execution happens in the following order:

```text
FactoryApplication
        │
        ▼
EmailNotificationFactory
        │
        ▼
createNotification()
        │
        ▼
EmailNotification
        │
        ▼
      send()
```

The client never directly creates an `EmailNotification`.

## Real-World Use Cases

Factory Method is widely used in real-world software.

Some common examples include:

- Notification systems (Email, SMS, Push)
- Database drivers (MySQL, PostgreSQL, Oracle)
- Payment gateways (Stripe, PayPal, Razorpay)
- Logging frameworks
- Cloud storage providers (AWS S3, Azure Blob Storage, Google Cloud Storage)
- Document exporters (PDF, Excel, CSV)
- GUI frameworks that create platform-specific UI components

## Trade-Off Analysis

### Advantages

- Reduces coupling between the client and concrete classes.
- Follows the Open/Closed Principle.
- Encapsulates object creation.
- Makes the code easier to maintain.
- Improves scalability as new products are introduced.
- Promotes polymorphism over conditional logic.

### Disadvantages

- Introduces more classes.
- Slightly increases the complexity of the design.
- May be unnecessary for applications with only one or two product types.

## When to Use vs. When to Avoid

### Use when

Use the Factory Method pattern when:

- Object creation is complex.
- The client should not know concrete classes.
- New product types are expected in the future.
- You want to follow the Open/Closed Principle.
- Different subclasses create different objects.

### Avoid when

Avoid using the Factory Method pattern when:

- Object creation is simple and unlikely to change.
- The application has only one concrete implementation.
- Introducing additional factory classes adds unnecessary complexity.

## Conclusion

The Factory Method Pattern separates **object creation** from **object usage** by delegating the responsibility of creating objects to factory classes.

Rather than instantiating concrete classes directly, the client works with abstractions and relies on factories to provide the appropriate implementation.

This approach reduces coupling, improves maintainability, and makes the application easier to extend. While it introduces additional classes, the benefits become significant as the number of product types grows, making Factory Method one of the most widely used creational design patterns in modern object-oriented software development.