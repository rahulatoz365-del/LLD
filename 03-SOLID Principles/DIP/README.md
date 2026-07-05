# Dependency Inversion Principle (DIP)

## 📌 Core Concept

> **"High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details. Details should depend on abstractions."**

The Dependency Inversion Principle (DIP) shifts your code from a rigid, top-down dependency structure to a flexible, decoupled architecture.

* **High-Level Modules:** The core business rules and use cases of your application (e.g., managing user actions).
* **Low-Level Modules:** The infrastructure, tools, and mechanical details (e.g., specific databases, network utilities, file systems).

Instead of your core business logic depending directly on concrete storage tools, both layers must depend on a shared **abstraction** (like a Java `interface`). This decouples the core architecture from implementation details.

---

## ❌ The Anti-Pattern: `WrongCode.java`

In `WrongCode.java`, the high-level `UserService` directly instantiates and controls the low-level database classes using the `new` keyword:

```java
class UserService {  
    private final MySQLDatabase sqlDb = new MySQLDatabase();      
    private final MongoDBDatabase mongoDb = new MongoDBDatabase();
    // ...
}

```

### Why is this problematic?

* **Tight Coupling:** `UserService` is completely bound to `MySQLDatabase` and `MongoDBDatabase`. If either low-level class changes its method names, configuration, or constructor, the high-level class breaks immediately.
* **Violates OCP:** If your application needs to transition to a third database (like PostgreSQL or Firebase), you must open `UserService.java` and physically add new concrete dependencies and new methods.
* **Untestable Logic:** You cannot test the core user storage business logic in isolation. Any test run on `UserService` will force direct operations on the database classes.

---

## ✅ The Refactored Solution: `CorrectCode.java`

In `CorrectCode.java`, the tight dependency graph is completely inverted by introducing a central abstraction layer: the `Database` interface.

```java
interface Database {
    void save(String data);
}

```

The low-level modules (`MySQLDatabase` and `MongoDBDatabase`) now implement this interface, adapting to the format defined by the business logic rather than dictating it.

Meanwhile, `UserService` no longer instantiates anything. It accepts the abstraction dynamically via its constructor, a design pattern known as **Dependency Injection**:

```java
class UserService {
    private final Database db;

    public UserService(Database database) { // Injected dependency
        this.db = database;
    }

    public void storeUser(String user) {
        db.save(user); // Communicates entirely with the interface
    }
}

```

### Why this follows DIP:

* **True Decoupling:** `UserService` does not know (and does not care) whether it is talking to a SQL server, a NoSQL instance, or a simulated in-memory test double. It only cares that the object implements `save()`.
* **Seamless Extension:** To add a new database provider, you write a new class implementing `Database`. The `UserService` code remains untouched and safe from regressions.
* **Effortless Testing:** You can easily inject a mock database object during unit testing to test the orchestration flow without making actual network or database calls.

---

## 🚀 Key Takeaways

By structuring systems around DIP:

* **The Flow of Control is Inverted:** High-level modules dictate the interface contract, and low-level modules must conform to it.
* **Pluggable Architecture:** Infrastructure tools can be swapped out like plugins at runtime without modifying the core system logic.