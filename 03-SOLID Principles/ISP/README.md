# Interface Segregation Principle (ISP)

## 📌 Core Concept

> **"Clients should not be forced to depend upon interfaces that they do not use."**

The Interface Segregation Principle (ISP) dictates that it is architecturally superior to design multiple, highly specific, and cohesive interfaces rather than one massive, general-purpose "fat" interface. When a class implements an interface, it should actually require and utilize every method defined within that contract. Forcing a class to implement meaningless methods creates rigid, tightly coupled systems.

---

## ❌ The Anti-Pattern: `wrong_code.java`

In `wrong_code.java`, the system dictates a single monolithic interface called `Shape`. It establishes a blanket contract requiring every shape to calculate both area and volume:

```java
interface Shape {
    double calculateArea();
    double calculateVolume();
}

```

The architectural flaw is exposed immediately when implementing 2D shapes. Because a `Circle` or a `Square` inherently lacks a Z-axis, it has no volume. To bypass the compiler, the developer is forced to write dummy methods that intentionally crash:

```java
@Override
public double calculateVolume() {
    throw new UnsupportedOperationException("Volume calculation is not supported for Circle shapes.");
}

```

### Why is this problematic?

* **Bloated Contracts:** The `Circle` and `Square` classes are burdened with methods and dependencies that have absolutely no relevance to their domain.
* **Cascading LSP Violations:** By throwing an unexpected `UnsupportedOperationException`, the 2D shapes directly break the Liskov Substitution Principle (LSP). If a client iterates over a generic list of `Shape` objects and calls `calculateVolume()`, the application will crash.
* **Fragile Client Code:** To compensate for the poor interface design, the client code (in the `main` method) is forced to wrap standard method calls in extensive `try-catch` blocks, littering the business logic with unnecessary boilerplate.

---

## ✅ The Refactored Solution: `correct_code.java`

In `correct_code.java`, the "fat" interface is dismantled. It is segregated into highly specific, role-based contracts that reflect the actual geometric capabilities of the shapes:

1. **`Shape2D`**: A precise contract requiring only `calculateArea()`.
2. **`Shape3D`**: A specialized contract requiring `calculateVolume()` and `calculateSurfaceArea()`.

```java
interface Shape2D {
    double calculateArea();
}

interface Shape3D {
    double calculateVolume();
    double calculateSurfaceArea();
}

```

Now, the concrete classes map perfectly to their real-world capabilities without writing a single line of dead code:

* `Circle` and `Square` confidently implement `Shape2D`.
* `Sphere` and `Cube` implement `Shape3D`.

### The Client-Side Impact:

The system is entirely predictable. The client code never has to catch an `UnsupportedOperationException` because the type system strictly guarantees that a 2D shape will never even possess a volume calculation method.

---

## 🚀 Key Takeaways

By strictly adhering to ISP:

* **High Cohesion:** Interfaces describe exact, isolated behaviors rather than a grab-bag of unrelated utility methods.
* **Zero Dead Code:** Concrete classes are never forced to provide empty methods, return null values, or throw exceptions simply to satisfy a bloated parent interface.
* **Decoupled Architecture:** Modifying the methods inside the `Shape3D` interface will never force the recompilation, testing, or modification of the 2D shape classes.