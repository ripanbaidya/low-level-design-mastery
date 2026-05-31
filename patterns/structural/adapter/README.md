# Adapter Pattern

## Definition

The Adapter Design pattern is a type of structural design pattern, It allows two incompatible interfaces to work
together by converting one interface into another that the client expects.

**Formal Definition (For Interview)**: Adapter pattern acts as a bridge between two incompatible interfaces without
modifying their existing code.

**Real-Life Example**: Consider a situation where you have a power adapter for Type A plugs in India. If you travel to a
country where Type B plugs are used, you can't use your adapter from India. An adapter pattern can help here. It
provides a Type B plug interface that can be used in the foreign country. The adapter, in this case, adapts the Type A
plug interface to the Type B plug interface, allowing you to use your power adapter in the foreign country.

## Core Components

In every adapter pattern example, you’ll always find these 4 roles:

1. Client
    - Uses the Target interface
    - Doesn’t know about the adaptee

2. Target (Interface)
    - What the client expects

3. Adaptee
    - Existing class with incompatible interface

4. Adapter

    - Implements Target
    - Internally uses Adaptee
    - Translates requests

📌 Interview tip:

If you can name these four roles, interviewer knows you understand the pattern.

### Step By Step Working

1. Client calls method on Target
2. Adapter receives the call
3. Adapter converts it into Adaptee’s format
4. Adaptee performs the action
5. Result goes back to client

🔁 Client → Adapter → Adaptee

## When to use and When to avoid

### When to use ✅

- You want to use an existing class, but its interface doesn’t match
- You are integrating legacy code
- You are using third-party libraries
- You want to avoid modifying already tested code
- You want reusability without changing client code

Real-world examples:

- JDBC Driver (Java → DB vendors)
- Payment gateway integrations
- Media Storage (AWS S3 or Cloudinary) integrations
- Media players (MP3 vs VLC vs MP4)

### When to avoid ❌

- You can directly modify the existing class
- Interfaces are already compatible
- Too many adapters → code complexity
- Adapter logic becomes heavy (business logic inside adapter is a smell)

📌 Red flag answer:

“Adapter should not contain business logic, only translation logic.”

## Advantages and Disadvantages

| Advantages                                       | Disadvantages                        |
|--------------------------------------------------|--------------------------------------|
| Works with legacy code                           | Extra class increases complexity     |
| Follows Open/Closed Principle                    | Too many adapters → hard to maintain |
| Improves reusability                             | Debugging can be harder              |
| Clean separation between client & implementation |                                      |

## Common Interview Questions

Q.1. Why not modify the existing class instead of adapter?

👉 Reasons to avoid modifying existing class:

- It may be third-party
- It may be already tested
- Violates Open/Closed Principle

Q2. Can Adapter pattern work with multiple adaptees?

👉 Yes, Object Adapter can adapt multiple classes using composition.

Q3. Is Adapter pattern structural or behavioral?

👉 Structural pattern

Q4. Does Adapter violate SOLID?

👉 ❌ No , It Supports Open/Closed Principle

Q5. Real project use?

👉 “I used adapter to integrate different payment gateways / APIs with a common interface.”

## Reference

- Article
    - [TutorialsPoint](https://www.tutorialspoint.com/design_pattern/adapter_pattern.htm)
    - [Algomaster](https://algomaster.io/learn/lld/adapter)
- Video
    - [Daily Code Buffer - Adapter Design Pattern](https://www.youtube.com/watch?v=H1O0Kl8exnk)