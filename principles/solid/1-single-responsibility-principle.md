## Single Responsibility Principle (SRP)

<p align="right">Last updated - 29.06.26</p>

The **Single Responsibility Principle (SRP)** is the "S" in the famous SOLID design principles. It was coined by Robert C. Martin (Uncle Bob) and is one of the foundational pillars of clean, maintainable code.

The core definition is simple: **"A class should have one, and only one, reason to change."**

In other words, a class should have exactly **one job** or responsibility. If a class does multiple things, it becomes "tightly coupled" to different parts of your application. When one requirement changes, you risk breaking unrelated functionality.

## The Real-World Analogy: The Swiss Army Knife

Think of a specialized tool versus a giant Swiss Army knife.

- If you break the blade on your Swiss Army knife, you have to send the whole tool (which contains your scissors, can opener, and screwdriver) to get repaired.

- If you use a standalone screwdriver, it does one thing perfectly, and fixing or replacing it has absolutely zero impact on your scissors.

## Example: The Bad vs. The Good

Let's look at a common scenario: handling an invoice system.

### ❌ The Bad Way (Violating SRP)

In this example, the `Invoice` class handles calculating the total, printing the invoice to the console, and saving it to a database.

```java
public class Invoice {
    private double amount;
    private double taxRate;

    public Invoice(double amount, double taxRate) {
        this.amount = amount;
        this.taxRate = taxRate;
    }

    // Responsibility 1: Business Logic (Calculation)
    public double calculateTotal() {
        return amount + (amount * taxRate);
    }

    // Responsibility 2: Presentation Logic (Printing)
    public void printInvoice() {
        System.out.println("Invoice Total: $" + calculateTotal());
    }

    // Responsibility 3: Data Persistence (Database)
    public void saveToDatabase() {
        System.out.println("Connecting to Database...");
        System.out.println("Saving invoice data...");
    }
}

```

#### Why this violates SRP:

This class has **three reasons to change**:

1. If the tax calculation logic changes (Business Logic).
2. If you want to change the print format from plain text to HTML or PDF (Presentation).
3. If you switch your database from MySQL to MongoDB (Persistence).

---

### ✅ The Good Way (Adhering to SRP)

To adhere to SRP, we break this massive class down into three distinct, highly focused classes. Each class now has exactly one reason to change.

```java
// 1. Handles ONLY the data and core calculation
public class Invoice {
    private double amount;
    private double taxRate;

    public Invoice(double amount, double taxRate) {
        this.amount = amount;
        this.taxRate = taxRate;
    }

    public double calculateTotal() {
        return amount + (amount * taxRate);
    }
}

// 2. Handles ONLY how an invoice is printed/displayed
public class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("Invoice Total: $" + invoice.calculateTotal());
    }
}

// 3. Handles ONLY saving the data
public class InvoiceRepository {
    public void save(Invoice invoice) {
        System.out.println("Connecting to Database...");
        System.out.println("Saving invoice total: $" + invoice.calculateTotal());
    }
}

```

## SRP vs. Separation of Concerns (SoC)

You might wonder: _Isn't this just Separation of Concerns?_
They are closely related, but they operate at different scales:

- **Separation of Concerns (SoC)** is a broad architectural mindset (e.g., keeping your entire UI layer separate from your database layer).

- **Single Responsibility Principle (SRP)** is the specific implementation of SoC at the class and module level. It's the micro-level tactical application of keeping things separated.

## Benefits of SRP

- **Maximized Maintainability:** If your database logic changes, you only touch `InvoiceRepository`. The math and printing code remain completely safe.

- **Easy Testing:** Testing `Invoice` is now trivial because you don't need to mock database connections or capture console outputs just to verify a math formula.

- **Avoids Merge Conflicts:** On a dev team, one person can optimize the database code while another updates the print layout, and they will never conflict because they are working in entirely different files.

## References
