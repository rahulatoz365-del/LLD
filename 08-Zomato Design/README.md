# 🍅 Tomato: Online Food Ordering System - Technical Documentation

## 1. System Overview

Tomato is a console-based, object-oriented food ordering application built in Java. The system simulates a complete user journey: discovering restaurants, browsing menus, managing a shopping cart, scheduling orders (Now or Later), and processing payments.

The architecture follows a modular, layered approach separating data models, business logic (managers/services), creational logic (factories), and algorithmic behaviors (strategies).

### **Directory Architecture**

* `models/`: Contains the core domain entities (`User`, `Restaurant`, `MenuItem`, `Cart`, `Order`, etc.).
* `managers/`: Handles the centralized state and lifecycle of core entities (`RestaurantManager`, `OrderManager`).
* `strategies/`: Encapsulates interchangeable payment algorithms.
* `factories/`: Abstracts the complex creation logic of different order types.
* `services/`: Contains auxiliary business services (`NotificationService`).
* `utils/`: Houses reusable utility functions (`TimeUtils`).

---

## 2. Design Patterns Implemented

The codebase relies heavily on Gang of Four (GoF) design patterns to ensure scalability and maintainability.

### **A. Strategy Pattern (Behavioral)**

* **Where it is used:** `PaymentStrategy`, `CreditCardPaymentStrategy`, `UpiPaymentStrategy`.
* **How it works:** The `Order` class does not hardcode how a payment is processed. Instead, it holds a reference to a `PaymentStrategy` interface. When `order.processPayment()` is called, it delegates the execution to the injected strategy.
* **Why it was used:** To allow the application to switch payment methods at runtime and to easily add new payment methods (e.g., NetBanking) without altering the core `Order` or `TomatoApp` logic.

### **B. Abstract Factory / Factory Method (Creational)**

* **Where it is used:** `OrderFactory` (Interface), `NowOrderFactory`, `ScheduledOrderFactory`.
* **How it works:** Creating an order is complex (requires calculating totals, mapping user/restaurant data, and determining if it is a `DeliveryOrder` or `PickupOrder`). The `TomatoApp` delegates this creation to a specific factory. `NowOrderFactory` sets the time to "Now", while `ScheduledOrderFactory` injects a user-defined future time.
* **Why it was used:** It extracts the complex instantiation logic out of the main orchestrator (`TomatoApp`), keeping the code clean and focused on workflow rather than object assembly.

### **C. Singleton Pattern (Creational)**

* **Where it is used:** `RestaurantManager`, `OrderManager`.
* **How it works:** These classes use a private constructor and a static `getInstance()` method to ensure that only one instance of the manager exists throughout the application's lifecycle.
* **Why it was used:** To provide a single, globally accessible, centralized, and consistent data store for all restaurants and orders in memory.

### **D. Facade Pattern (Structural)**

* **Where it is used:** `TomatoApp`.
* **How it works:** `TomatoApp` acts as a unified, simplified interface to the complex subsystems (Factories, Managers, Models). The `Main` class doesn't need to know how to interact with the `RestaurantManager` or `OrderFactory` directly; it just calls `tomato.checkoutNow(...)`.
* **Why it was used:** To decouple the client (in this case, `Main.java`) from the complex internal workings of the system.

---

## 3. SOLID Principles Analysis

The system demonstrates a strong grasp of object-oriented design, but like many real-world applications, it makes a few trade-offs. Here is a deep dive into how the 5 SOLID principles were handled.

### **1. Single Responsibility Principle (SRP)**

*A class should have one, and only one, reason to change.*

* ✅ **Followed:** * `TimeUtils` solely formats time.
* `NotificationService` solely prints notifications.
* `MenuItem` is a pure data carrier.


* ❌ **Broken:** * `Cart`: It manages its state (adding/clearing items) but also calculates the business logic for `getTotalCost()`. If pricing logic changes (e.g., adding taxes or discounts), the `Cart` class must change.
* `TomatoApp`: While it is a Facade, it handles console I/O directly (e.g., `printUserCart`). Presentation logic (printing to the console) is mixed with business orchestration.



### **2. Open/Closed Principle (OCP)**

*Software entities should be open for extension, but closed for modification.*

* ✅ **Followed:** * **Payments:** If you want to add a `CryptoPaymentStrategy`, you simply create a new class implementing `PaymentStrategy`. You do not have to touch a single line of code in the `Order` or `TomatoApp` classes.
* ❌ **Broken:** * **Order Factories:** Look at `NowOrderFactory.java` and `ScheduledOrderFactory.java`:
`java if (orderType.equals("Delivery")) { // create DeliveryOrder } else { // create PickupOrder } `
If the business introduces a "Dine-In" order type, you will be forced to open and modify both factory classes to add an `else if` block. This violates OCP.

### **3. Liskov Substitution Principle (LSP)**

*Subtypes must be substitutable for their base types without altering the correctness of the program.*

* ✅ **Followed:** * `DeliveryOrder` and `PickupOrder` seamlessly extend the abstract `Order` class. Anywhere in the code that expects an `Order` (such as `OrderManager.addOrder(Order order)` or `NotificationService.notify(Order order)`), you can pass a `DeliveryOrder` or `PickupOrder` without the program crashing or requiring complex type-checking.

### **4. Interface Segregation Principle (ISP)**

*Clients should not be forced to depend upon interfaces that they do not use.*

* ✅ **Followed:** * The interfaces are highly focused and lean. `PaymentStrategy` forces implementation of a single method: `pay(double amount)`. `OrderFactory` forces a single method: `createOrder(...)`. Classes implementing these do not have to write boilerplate code for unused methods.

### **5. Dependency Inversion Principle (DIP)**

*High-level modules should not depend on low-level modules. Both should depend on abstractions.*

* ✅ **Followed:** * The `Order` class (high-level) depends on the `PaymentStrategy` interface (abstraction), not on `CreditCardPaymentStrategy` (low-level detail).
* `TomatoApp.checkout(...)` accepts the `OrderFactory` interface rather than depending strictly on a concrete factory implementation.


* ❌ **Minor Violation:** * `TomatoApp` directly instantiates concrete factories in its overloaded methods (`new NowOrderFactory()`, `new ScheduledOrderFactory()`). A true Dependency Injection approach would pass these factories in from the outside (e.g., via the client or an IoC container).

---

## 4. Architectural Vulnerabilities & Future Improvements

1. **Thread Safety (Singletons):** The `getInstance()` methods in `OrderManager` and `RestaurantManager` are not thread-safe. In a multi-threaded web environment, concurrent access could result in multiple instances being created. This can be fixed using Double-Checked Locking or eager initialization.
2. **Hardcoded Strings:** Order types (`"Delivery"`) are passed as hardcoded Strings. This is prone to typo-related bugs. This should be refactored into an `Enum` (e.g., `OrderType.DELIVERY`, `OrderType.PICKUP`).
3. **Missing validation:** The `User` object is instantiated without deep validation, and the `Cart` allows adding items before checking if they actually exist in the current restaurant's specific menu (it relies on `TomatoApp` to do this check). Domain validation should ideally live closer to the domain models.
4. **Coupling in Services:** `NotificationService` relies heavily on `System.out.println`. Passing an output stream or a Logger interface to the service would make it testable and more extensible.