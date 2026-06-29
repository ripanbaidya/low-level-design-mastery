## SOLID Principles

<p align="right">Last updated - 29.06.2026</p>

The **SOLID Principles** are five object-oriented design principles that help you write code that is:

- Easy to maintain
- Easy to extend
- Easy to test
- Less coupled
- More reusable

The term **SOLID** is an acronym, where each letter represents one of the following _five design principles_:

1. [S — Single Responsibility Principle (SRP)](/principles/solid/1-single-responsibility-principle.md)

   > A class should have only one responsibility and therefore only one reason to change.

2. [O — Open/Closed Principle (OCP)](/principles/solid/2-open-closed-principle.md)

   > Software entities should be open for extension but closed for modification.

3. [L — Liskov Substitution Principle (LSP)](/principles/solid/3-liskov-substitution-principle.md)

   > Child classes should be substitutable for their parent classes without altering the correctness of the program.

4. [I — Interface Segregation Principle (ISP)](/principles/solid/4-interface-segregation-principle.md)

   > A class should not be forced to implement interfaces it does not use.

5. [D — Dependency Inversion Principle (DIP)](/principles/solid/5-dependency-inversion-principle.md)

   > Depend on abstractions, not concrete implementations., High-level modules should not directly depend on low-level modules.

## SOLID Summary Table

| Principle | Meaning               | Goal                                                          |
| --------- | --------------------- | ------------------------------------------------------------- |
| **S**     | Single Responsibility | One class, one responsibility                                 |
| **O**     | Open/Closed           | Extend behavior without modifying existing code               |
| **L**     | Liskov Substitution   | Subclasses should be usable wherever the parent is expected   |
| **I**     | Interface Segregation | Create small, focused interfaces                              |
| **D**     | Dependency Inversion  | Depend on interfaces/abstractions instead of concrete classes |

## Conclusion

"SOLID is a set of five object-oriented design principles that improve maintainability, scalability, and testability. SRP ensures a class has a single responsibility. OCP allows extending behavior without modifying existing code. LSP ensures subclasses can safely replace their parent classes. ISP recommends creating small, focused interfaces so clients implement only what they need. DIP states that high-level modules should depend on abstractions rather than concrete implementations, which is commonly achieved in Spring Boot through dependency injection."

## References
