## Builder Design Pattern

<p align="right">Last updated - 30.06.2026</p>

The Builder pattern is an essential creational design pattern used to construct complex objects step-by-step. It is particularly effective when an object requires multiple configuration parameters, some of which are mandatory and others optional.

## Introduction

In enterprise software engineering, constructors can easily become cluttered when a class contains numerous fields. This introduces a structural anti-pattern known as **Telescoping Constructors**.

The Builder design pattern solves this by separating the construction of a complex object from its actual representation. This separation enables clean object creation, improves code readability, and provides native support for immutability and state validation.

## The Problem

Consider a backend service for a publishing application where you need to create a `Post` object. A post consists of several attributes:

- `title` (Mandatory)
- `content` (Mandatory)
- `author` (Mandatory)
- `category` (Optional)
- `imagePreviewUrl` (Optional)
- `visibilityStatus` (Optional)

If you attempt to accommodate every permutation of optional attributes using traditional constructors, your class structure resembles this:

```java
public class Post {
    public Post(String title, String content, String author) { ... }
    public Post(String title, String content, String author, String category) { ... }
    public Post(String title, String content, String author, String category, String imagePreviewUrl) { ... }
    public Post(String title, String content, String author, String category, String imagePreviewUrl, String visibilityStatus) { ... }
}

```

### Consequences of This Approach

1. **The Telescoping Constructor Problem:** Code becomes difficult to maintain as the parameter list grows.
2. **Brittle Codebase:** If two adjacent fields share the same data type (e.g., `String category` and `String imagePreviewUrl`), a developer can easily swap the order of arguments without triggering a compile-time error.
3. **Immutability Roadblocks:** Using standard setters (`setCategory()`) to resolve long constructors destroys immutability, exposing your objects to race conditions in multi-threaded environments.

## The Solution

The Builder pattern addresses this problem by delegating object creation to a dedicated, separate inner class called a **Builder**.

Instead of calling a massive constructor directly, the client interacts with the builder, specifying attributes step-by-step using descriptive method chaining. Once all desired fields are assigned, the client invokes a terminating `build()` method to run validations and safely compile the final immutable object.

### Structural Components

| Component   | Responsibility                                                                                                                                                              |
| ----------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Product** | The complex object being constructed. It features a private constructor to prevent direct instantiation and `final` fields to ensure immutability.                          |
| **Builder** | A public static inner class nested inside the Product. It duplicates the Product's fields, exposes fluent API methods for assignment, and contains the instantiation logic. |
| **Client**  | The component that triggers the fluid method chain to configure and instantiate the Product.                                                                                |

## UML Representation

![Builder UML Representation](/resources/images/patterns/creational/builder-uml.png)

## Implementation

Below is a robust implementation of the Builder pattern in Java. It includes business rule validation to enforce constraints before the final object is generated.

### The Product and Nested Builder Class

```java
public class Post {

    // Final fields guarantee complete thread-safe immutability after construction
    private final String title;
    private final String content;
    private final String author;
    private final String category;
    private final String imagePreviewUrl;

    // The package-private/private constructor receives the configured Builder instance
    private Post(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.category = builder.category;
        this.imagePreviewUrl = builder.imagePreviewUrl;
    }

    // Expose only getters to enforce a read-only state
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getImagePreviewUrl() { return imagePreviewUrl; }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", imagePreviewUrl='" + imagePreviewUrl + '\'' +
                '}';
    }

    // Static Inner Builder Class
    public static class Builder {
        private String title;
        private String content;
        private String author;
        private String category;
        private String imagePreviewUrl;

        // Setter-like methods that return 'this' to facilitate a fluid API interface
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder imagePreviewUrl(String imagePreviewUrl) {
            this.imagePreviewUrl = imagePreviewUrl;
            return this;
        }

        /**
         * The orchestrating method that validates internal state constraints
         * before bringing the final Product object into lifecycle existence.
         */
        public Post build() {
            // Business Validation Rules
            if (this.title == null || this.title.trim().isEmpty()) {
                throw new IllegalStateException("Validation Error: Title is a required field.");
            }
            if (this.content == null || this.content.trim().isEmpty()) {
                throw new IllegalStateException("Validation Error: Content is a required field.");
            }
            if (this.author == null || this.author.trim().isEmpty()) {
                throw new IllegalStateException("Validation Error: Author is a required field.");
            }

            // Safe instantiation pass
            return new Post(this);
        }
    }
}

```

### Client Usage Execution

```java
public class Application {
    public static void main(String[] args) {

        // Execute clean, expressive, step-by-step object configuration
        Post analyticalPost = new Post.Builder()
                .title("Mastering the Builder Pattern")
                .content("A deep dive into creational software design principles.")
                .author("Sarah Connor")
                .category("Software Architecture")
                // imagePreviewUrl is omitted here; it gracefully defaults to null without error
                .build();

        System.out.println("Successfully instantiated product:");
        System.out.println(analyticalPost);
    }
}

```

### Execution Output

```text
Successfully instantiated product:
Post{title='Mastering the Builder Pattern', content='A deep dive into creational software design principles.', author='Sarah Connor', category='Software Architecture', imagePreviewUrl='null'}

```

## Real-World Use Cases

- **Core Java Libraries:** `java.lang.StringBuilder#append()` and `java.nio.file.Paths` configuration tools.
- **Network & Security Frameworks:** Constructing highly specific requests via Java's native HTTP Client (`java.net.http.HttpRequest.Builder`).
- **Enterprise Frameworks:** Spring Security configuration definitions and Apache Commons utility generation tools.

## Trade-Off Analysis

### Advantages

- **Eliminates Structural Clutter:** Keeps your codebase clean by preventing complex, telescoping constructors.
- **Supports Strict Immutability:** Eliminates the need for public setter methods, making it ideal for concurrent programming.
- **Centralized Validation Layer:** Business logic constraints are evaluated together inside the `build()` method before an object is created.
- **Readable API Design:** Client code reads like a structured configuration file rather than an ambiguous list of parameter values.

### Disadvantages

- **Boilerplate Overhead:** Requires writing double the amount of field definitions and builder setter blocks. (Note: In production environments, tools like Lombok's `@Builder` annotation can automate this generation).
- **Increased Memory Footprint:** Instantiating the temporary `Builder` object introduces slight runtime allocation overhead, which may impact highly constrained, low-latency micro-environments.

## When to Use vs. When to Avoid

### Use When:

- The target class requires more than 4 or 5 optional configuration parameters.
- The system demands that target domain structures remain completely immutable after creation.
- Object validation depends on inspecting multiple input parameters together before instantiation.

### Avoid When:

- The data structures are simple, lightweight domain models holding fewer than 3 or 4 attributes.
- The target instances are meant to be highly mutable structures that change state continuously across processing layers.
