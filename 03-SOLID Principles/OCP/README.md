# Open/Closed Principle (OCP)

## 📌 Core Concept

> **"Software entities should be open for extension, but closed for modification."**

The Open/Closed Principle (OCP) ensures that your code is resilient to changing requirements.

* **Open for extension:** You should be able to add new features or behaviors to your application easily.
* **Closed for modification:** You should be able to add these new features *without* changing or rewriting code that is already written, tested, and running in production.

---

## ❌ The Violation: `WrongCode.java`

In `WrongCode.java`, look closely at the `DatabaseSaver` class:

```java
class DatabaseSaver {
    public void saveToSQL(ShoppingCart cart) {
        System.out.println("Saving shopping cart to SQL database...");
    }
    
    public void saveToMongoDB(ShoppingCart cart) {
        System.out.println("Saving shopping cart to MongoDB database...");
    }
}

```

### Why does this violate OCP?

* **Forced Modification:** If your application expands and needs to support a third database engine (like PostgreSQL or Firebase), you are forced to open the existing `DatabaseSaver.java` file and add a new method, such as `saveToPostgreSQL()`.
* **High Risk of Regressions:** Every time you open a working production class to modify it for a new feature, you risk introducing bugs into the existing code paths (`saveToSQL` or `saveToMongoDB`).
* **Tight Coupling:** The main executable program depends heavily on a single concrete class that grows more bloated over time with complex logic or massive `if-else`/`switch` statements.

---

## ✅ The Solution: `CorrectCode.java`

In `CorrectCode.java`, the problem is elegantly resolved by introducing an abstraction layer using a Java **interface**.

```java
interface DatabaseSaver {
    void saveToDatabase(ShoppingCart cart);
}

```

Instead of keeping all database types in one massive class, the storage behavior is abstracted out. Each specific database provider now implements this common interface:

```java
class MySQLDatabaseSaver implements DatabaseSaver {
    @Override
    public void saveToDatabase(ShoppingCart cart) {
        System.out.println("Saving shopping cart to MySQL database...");
    }
}

class MongoDBDatabaseSaver implements DatabaseSaver {
    @Override
    public void saveToDatabase(ShoppingCart cart) {
        System.out.println("Saving shopping cart to MongoDB database...");
    }
}

```

### Why this follows OCP:

* **True Extension:** If you need to add support for a new database provider (e.g., PostgreSQL), you do not touch a single line of existing code. You simply create a brand-new class:
```java
class PostgreSQLDatabaseSaver implements DatabaseSaver {
    @Override
    public void saveToDatabase(ShoppingCart cart) {
        System.out.println("Saving to PostgreSQL...");
    }
}

```


* **Zero Contamination:** Because the original classes (`MySQLDatabaseSaver` or `MongoDBDatabaseSaver`) are completely left alone, there is zero risk of breaking your existing database saving mechanisms.
* **Plug-and-Play Design:** The core application treats any database engine generically as a `DatabaseSaver`, allowing implementations to be swapped out cleanly at runtime.

---

## 🚀 Key Takeaways

By designing around OCP:

* **Scalability skyrockets:** Your code handles changing requirements smoothly by appending new classes instead of breaking open old ones.
* **Testing is isolated:** New code can be unit-tested on its own without requiring full regression tests on old features.