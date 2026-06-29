## Dependency Inversion Principle (DIP)

<p align="right">Last updated - 29.06.26</p>

The **Dependency Inversion Principle (DIP)** is the final principle in the SOLID acronym ("D").

Its core definition is:

1. **High-level modules should not import anything from low-level modules. Both should depend on abstractions (e.g., interfaces).**

2. **Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions.**

In simple terms: **Depend on interfaces, not on concrete classes.**

## The Real-World Analogy: Wiring Your House

Imagine if you bought a new television, and instead of plugging it into a standard wall outlet, you had to slice open your walls and solder the TV's power cables directly into your house’s main electrical wiring.

If you wanted to replace that TV later, you'd have to cut the wires, risk electrocution, and solder the new TV in.

Instead, houses use an **abstraction**: the wall socket. Your house provides a socket (interface), and your TV implements a plug (detail). Because both depend on the same standard interface, you can plug in a TV, a vacuum cleaner, or a lamp without altering your house's electrical infrastructure.

## Example: The Bad vs. The Good

Let's look at a notification system that sends alerts to users.

### ❌ The Bad Way (Violating DIP)

Here, the high-level `NotificationManager` directly instantiates and depends on the low-level `EmailSender` class.

```java
// Low-level module
public class EmailSender {
    public void sendEmail(String message) {
        System.out.println("Email sent: " + message);
    }
}

// High-level module
public class NotificationManager {
    // VIOLATION: Hardcoded dependency on a concrete class
    private EmailSender emailSender = new EmailSender();

    public void sendAlert(String message) {
        emailSender.sendEmail(message);
    }
}

```

#### Why this breaks DIP:

The high-level `NotificationManager` is tightly coupled to `EmailSender`. If you want to add the ability to send SMS alerts or WhatsApp messages, you have to completely modify the `NotificationManager` class. The high-level logic is at the mercy of the low-level details.

### ✅ The Good Way (Adhering to DIP)

To fix this, we "invert" the dependency by introducing an interface. Now, both the high-level and low-level classes depend on that interface.

```java
// 1. The Abstraction (The Interface)
public interface MessageService {
    void sendMessage(String message);
}

// 2. Low-level modules implement the interface
public class EmailSender implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}

public class SMSSender implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("SMS sent: " + message);
    }
}

// 3. High-level module depends ONLY on the abstraction
public class NotificationManager {
    private final MessageService messageService;

    // Dependency is injected via constructor (Constructor Injection)
    public NotificationManager(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendAlert(String message) {
        messageService.sendMessage(message);
    }
}

```

#### Why this is brilliant:

`NotificationManager` no longer knows or cares _how_ the message is sent. It only knows that whatever object it is handed will have a `.sendMessage()` method.

If you want to switch from email to SMS, you don't change a single line of code inside `NotificationManager`. You simply pass a different implementation into its constructor:

```java
NotificationManager emailAlerts = new NotificationManager(new EmailSender());
NotificationManager smsAlerts = new NotificationManager(new SMSSender());

```

## DIP vs. Dependency Injection (DI)

It's easy to confuse these two terms because they sound similar and work together:

- **Dependency Inversion Principle (DIP)** is the high-level _architectural design goal_ (decoupling modules via interfaces).

- **Dependency Injection (DI)** is the _technical pattern technique_ used to pass the dependency into the class (like using constructors, setters, or frameworks like Spring). DI is the tool used to achieve DIP.

## Key Benefits of DIP

- **Decoupled Architecture:** High-level business logic is protected from changes made to underlying low-level data technologies, APIs, or frameworks.

- **Unmatched Flexibility:** Swapping out behaviors (e.g., switching from a local database to a cloud database) becomes an architectural setting rather than a massive refactoring job.

- **Flawless Unit Testing:** You can easily pass a "mock" or "fake" implementation of an interface to isolate and test your high-level business logic without making real database or network calls.

## References
