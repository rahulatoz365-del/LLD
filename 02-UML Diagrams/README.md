# 📘 Introduction to UML for Low-Level Design (LLD)

Object-Oriented Programming (OOP) and Low-Level Design require a clear blueprint before writing code. **Unified Modeling Language (UML)** serves as this blueprint. It is a standardized visual language used to specify, visualize, construct, and document the artifacts of a software system.

This guide covers:

1. **Class Diagrams** (Static Structure)
2. **Object Relationships & Associations** (How classes interact)
3. **Sequence Diagrams** (Dynamic Interaction)
4. **Real-World Case Study:** ATM Cash Withdrawal Sequence Diagram

---

## 1. Class Diagrams (The Structural Blueprint)

A **Class Diagram** represents the static structure of an application. It describes what classes exist, what data they hold (attributes/variables), and what actions they can perform (methods/behaviors).

### Structure of a Class

A standard UML class is represented by a box divided into three sections:

1. **Top Section:** Class Name
2. **Middle Section:** Attributes / Variables (Data)
3. **Bottom Section:** Methods / Functions (Behaviors)

### 🔐 Access Modifiers (Visibility)

* **`+` Public:** Accessible from any other class.
* **`-` Private:** Accessible only within the same class (used for data hiding/encapsulation).
* **`#` Protected:** Accessible within the class and its subclasses.

### 🚗 Diagrammatic Example: The Car Class

Following strict OOP encapsulation rules, data variables are marked as private (`-`), while the methods used to interact with that data are marked as public (`+`).

```text
+------------------------------------------+
|                   Car                    |  <-- Class Name
+------------------------------------------+
| - brand : String                         |  <-- Private Attributes (Data Hiding)
| - model : String                         |
| - engineCC : int                         |
+------------------------------------------+
| + getBrand() : String                    |  <-- Public Getters/Setters
| + setBrand(b : String) : void            |
|                                          |
| + startEngine() : void                   |  <-- Public Behaviors
| + stopEngine() : void                    |
| + accelerate() : void                    |
| + brake() : void                         |
+------------------------------------------+

```

---

## 2. Object Associations & Relationships

Classes rarely exist in isolation. They need to communicate and relate to one another. Object relationships are broadly classified into **Class Associations** (Compile-time) and **Object Associations** (Runtime).

### A. Inheritance (is-a relationship)

Inheritance allows a subclass to inherit attributes and methods from a superclass.

* **Symbol:** An open/hollow arrow pointing from the child class to the parent class (`△`).
* **Real-World Example:** A Cow **is an** Animal.

```text
   +-----------------+
   |     Animal      | (Super / Parent Class)
   +-----------------+
            △
            |  (Inheriting / Is-A)
   +-----------------+
   |       Cow       | (Sub / Child Class)
   +-----------------+

```

### B. Simple Association

A basic semantic connection between two completely independent classes.

* **Symbol:** A simple straight line, sometimes with a directional arrow and a label (`->`).
* **Real-World Example:** Arjun **has a** Book. If Arjun leaves, the book still exists independently.

```text
+----------+      "has a"       +----------+
|  Arjun   | -----------------> |   Book   |
+----------+                    +----------+

```

### C. Aggregation (Weak has-a relationship)

A relationship where a child class can exist independently of the parent class.

* **Symbol:** An open/hollow diamond (`◇`) on the side of the parent/container class.
* **Real-World Example:** A Room contains a Bed, Sofa, and Chair. If the room is destroyed, the furniture still exists.

```text
+--------+                 +--------+
|  Bed   | <------◇------- |  Room  |
+--------+                 +--------+
                             |    |
                   ┌─────────┘    └─────────┐
                   ▼                        ▼
               +--------+               +--------+
               |  Sofa  |               | Chair  |
               +--------+               +--------+

```

### D. Composition (Strong has-a relationship)

A strict, high-dependency relationship where the child class **cannot** exist without its parent.

* **Symbol:** A filled/solid diamond (`◆`) on the side of the parent/owner class.
* **Real-World Example:** A Chair is composed of a Wheel, a Seat, and an Armrest. If you destroy the chair completely, those specific functional components tied to it are effectively gone/repurposed.

```text
+---------+                +---------+
|  Wheel  | <------◆------ |  Chair  |
+---------+                +---------+
                             |     |
                   ┌─────────┘     └─────────┐
                   ▼                         ▼
               +---------+               +---------+
               |  Seat   |               | Armrest |
               +---------+               +---------+

```

---

## 3. Sequence Diagrams (The Dynamic Interaction Blueprint)

While class diagrams show how classes are *built*, **Sequence Diagrams** show how objects *interact over time*. They track the messages passed between objects to complete a specific task.

### 🧱 Core Components of a Sequence Diagram

1. **Actor/Object Box:** Represents the instance participating in the interaction.
2. **Lifeline:** A dashed or solid vertical line representing the existence of an object over time.
3. **Activation Bar:** A thick vertical rectangle (`┌┴┐` to `└┬┘`) on the lifeline showing when an object is actively performing an operation or occupying the thread of execution.
4. **Messages:** Arrows showing communication between objects.

### Types of Messages

* **Synchronous (Sync):** The sender blocks and waits for a response before proceeding.
* *Representation:* Solid arrow line with a filled arrowhead (`->`).


* **Asynchronous (Async):** The sender sends the message and continues working immediately without waiting.
* *Representation:* Solid arrow line with an open arrowhead (`->`).


* **Return Message:** Sends back data to the caller.
* *Representation:* Dashed line with an arrow (`< - - -`).



---

## 4. Real-World Case Study: ATM Cash Withdrawal

Let's model an ATM Cash Withdrawal sequence to bring it all together, utilizing proper activation bars to show exactly when each object is active.

### Use Case Context

1. A **User** requests a withdrawal (e.g., $50) from the **ATM**.
2. The **ATM** passes this to a **Transaction** handler.
3. The transaction verifies the user's account balance with the **Account** database.
4. If approved, the ATM triggers the hardware **Cash Dispenser** to dispense money.

### 🎬 Sequence Diagram Layout

```text
 User             ATM           Transaction      Account     Cash Dispenser
  │                │                 │              │              │
  │  withdraw()   ┌┴┐                │              │              │
  ├──────────────>│ │ create()      ┌┴┐             │              │
  │               │ │──────────────>│ │             │              │
  │               │ │               │ │ checkBal() ┌┴┐             │
  │               │ │               │ │───────────>│ │             │
  │               │ │               │ │            │ │             │
  │               │ │               │ │ return bal │ │             │
  │               │ │               │ │< - - - - - └┬┘             │
  │               │ │               │ │             │              │
  │               │ │  dispense()   │ │             │              │
  │               │ │<──────────────└┬┘             │              │
  │               │ │                X (destroyed)  │              │
  │               │ │                                              │
  │               │ │ dispenseCash()                              ┌┴┐
  │               │ │────────────────────────────────────────────>│ │
  │               │ │                                             │ │
  │               │ │< - - - - - - - - - - - - - - - - - - - - - -└┬┘
  │  return cash  │ │                                              │
  │< - - - - - - -└┬┘                                              │
  │                │                                               │

```

### 🔲 Advanced Control Frames (Logic Blocks)

When designing complex logic in sequence diagrams, use these conditional blocks:

* **alt (Alternatives / If-Else):** Used when only one of multiple paths can be taken (e.g., `alt Balance >= Amount` vs `Balance < Amount`).
* **opt (Option / If):** Used for optional steps that happen only under specific conditions.
* **loop (For / While):** Used when an interaction repeats multiple times (e.g., counting bills).