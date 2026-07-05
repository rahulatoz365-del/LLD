# Single Responsibility Principle (SRP)

## 📌 Core Concept

> **"A class should have one, and only one, reason to change."**

The Single Responsibility Principle (SRP) mandates that a module, class, or function should be strictly responsible for a single part of the software's functionality. When a class assumes multiple responsibilities, those responsibilities become coupled. A change to one responsibility can inadvertently impair or break the others, leading to a fragile and rigid codebase.

---

## ❌ The Anti-Pattern: `WrongCode.java`

In the provided `WrongCode.java`, the `ShoppingCart` class is an example of a "God Object"—a class that knows too much and does too much. It currently has **three distinct reasons to change**:

1. **Core Business Logic:** Managing the list of items and calculating the total price (`addProduct`, `removeProduct`, `calculateTotal`).
2. **Presentation/Output Logic:** Formatting and printing the receipt (`printInvoice`).
3. **Persistence/Infrastructure Logic:** Handling database operations (`saveToDatabase`).

### Why is this problematic?

* **Fragility:** If you decide to change the database from MySQL to MongoDB, you must modify the `ShoppingCart` class. During this modification, you risk introducing a bug into the pricing calculation logic.
* **Poor Testability:** To test the `calculateTotal` method, you are forced to instantiate a class that contains database connection logic and console output logic, making unit testing unnecessarily complex.
* **Rigidity:** If you want to output the invoice as a PDF instead of a console string, you have to alter the core shopping cart file.

---

## ✅ The Refactored Solution: `CorrectCode.java`

In `CorrectCode.java`, the bloated `ShoppingCart` class has been dismantled. The responsibilities have been isolated and delegated to highly cohesive, specialized classes.

### Separation of Concerns:

* **`ShoppingCart`:** Now acts strictly as a domain model. Its sole responsibility is managing the in-memory collection of `Product` objects and calculating their cumulative total. *Reason to change: A change in how cart totals are calculated.*
* **`InvoicePrinter`:** Handles all presentation logic. It takes a `ShoppingCart` object and formats its data for output. *Reason to change: A change in the invoice format (e.g., adding taxes, converting to PDF).*
* **`DatabaseSaver`:** Manages all persistence logic. It extracts the necessary data and interacts with the storage layer. *Reason to change: A change in the database schema or storage engine.*

---

## 🚀 Key Takeaways

By applying SRP, the resulting architecture achieves:

* **High Cohesion:** Each class does exactly what its name implies and nothing more.
* **Isolated Impact:** Changing how an invoice is printed has zero risk of breaking the database saving mechanism.
* **Reusability:** The `InvoicePrinter` and `DatabaseSaver` can potentially be abstracted and reused for other entities (like `WishList` or `OrderHistory`) without duplicating code.