# Liskov Substitution Principle (LSP)

## 📌 Core Concept

> **"Subtypes must be substitutable for their base types."**

The Liskov Substitution Principle (LSP) dictates that objects of a superclass should be replaceable with objects of its subclasses without breaking the application. In practice, this means that a child class must honor the contract established by its parent interface or class. It should not implement a method just to throw an exception, nor should it change the expected behavior so drastically that the client code fails.

---

## ❌ The Anti-Pattern: `WrongCode.java`

In `WrongCode.java`, the system defines a broad `Account` interface with two methods: `deposit()` and `withdraw()`.

The problem arises with the `FixedTermAccount`. A fixed-term account inherently does not allow withdrawals before a certain date. To bypass this, the subclass throws an exception:

```java
@Override
public void withdraw(double amount) {
    throw new UnsupportedOperationException("Withdrawal not allowed in Fixed Term Account!");
}

```

### Why is this problematic?

* **Broken Contracts:** The `Account` interface promises that any implementing class can perform a `withdraw` operation. `FixedTermAccount` lies about this capability.
* **Runtime Crashes:** The `BankClient` expects to iterate uniformly over a list of `Account` objects. When it encounters the `FixedTermAccount`, the application throws an exception and disrupts the flow. The subtype is **not substitutable** for its base type.

---

## ⚠️ The Band-Aid: `PartialCorrectCode.java`

In `PartialCorrectCode.java`, a developer attempted to fix the runtime crash without addressing the underlying architectural flaw. The `BankClient` now explicitly checks the object type before calling the method:

```java
if (acc instanceof FixedTermAccount) {
    System.out.println("Skipping withdrawal...");
} else {
    acc.withdraw(500);
}

```

### Why is this still bad design?

* **Type-Checking Smell:** Relying on `instanceof` or `getType()` checks inside a loop is a glaring indicator of an LSP violation. The client code is now forced to micromanage the specific implementations.
* **Violates OCP:** If you introduce a new account type that also restricts withdrawals (e.g., `FrozenAccount`), you must open the `BankClient` class and add another `else if` condition. The code is no longer closed for modification.

---

## ✅ The Refactored Solution: `CorrectCode.java`

In `CorrectCode.java`, the inheritance tree is restructured to reflect the true capabilities of the domain objects. Instead of one monolithic interface, the contracts are segregated by behavior:

1. **`DepositOnlyAccount`**: The base capability. All accounts allow deposits.
2. **`WithdrawableAccount`**: Extends `DepositOnlyAccount` to add withdrawal capabilities.

```java
interface DepositOnlyAccount {
    void deposit(double amount);
}

interface WithdrawableAccount extends DepositOnlyAccount {
    void withdraw(double amount);
}

```

Now, the classes only implement the interfaces they can actually fulfill:

* `SavingAccount` and `CurrentAccount` implement `WithdrawableAccount`.
* `FixedTermAccount` implements **only** `DepositOnlyAccount`.

### The Client-Side Impact:

The `BankClient` now accepts precisely what it needs. It processes `WithdrawableAccount` objects knowing that a `withdraw()` call is 100% guaranteed to be supported, eliminating the need for `try-catch` blocks or `instanceof` checks.

---
Here is a clear, code-focused breakdown of the 7 rules of the Liskov Substitution Principle (LSP). You can add this directly into your repository's documentation to give readers concrete examples of what to do and what to avoid.

---

## ⚖️ The 7 Rules of LSP: Code Examples

To ensure a subclass is completely substitutable for its parent, it must adhere to three categories of rules.

### I. Signature Rules

These dictate how method definitions can change in subclasses.

**1. Method Argument Rule (Contravariance)**
A subclass method can accept broader arguments than its parent, but never narrower.
*Note: Java strictly enforces identical method signatures for overriding. Attempting to change the parameter type in Java results in method overloading, not overriding.*

```java
class Parent {
    public void process(String data) { }
}

class Child extends Parent {
    @Override
    public void process(String data) { } // In Java, arguments MUST match exactly.
}

```

**2. Return Type Rule (Covariance)**
A subclass method can return the exact same type or a **narrower** (more specific) type than the parent method. It cannot return a broader type.

```java
class Animal { }
class Dog extends Animal { }

class Parent {
    public Animal create() { return new Animal(); }
}

class Child extends Parent {
    @Override
    public Dog create() { return new Dog(); } // ✅ Valid: Dog is a specific Animal.
}

```

**3. Exception Rule**
A subclass method can throw fewer or **narrower** checked exceptions than the parent. It must never throw new or broader checked exceptions that the client code isn't expecting.

```java
class Parent {
    public void read() throws IOException { }
}

class Child extends Parent {
    @Override
    public void read() throws FileNotFoundException { } // ✅ Valid: Narrower exception.
    
    // @Override
    // public void read() throws Exception { } // ❌ VIOLATION: Broader exception.
}

```

---

### II. Method Rules (Design by Contract)

These rules govern the expectations of data before and after a method runs.

**4. Preconditions Cannot be Strengthened**
A precondition is a requirement that must be met *before* a method executes. A subclass cannot make these rules stricter than the parent.

```java
class User {
    public void setPassword(String password) {
        if (password.length() < 6) throw new Error("Too short"); // Parent requires 6 chars
    }
}

class AdminUser extends User {
    @Override
    public void setPassword(String password) {
        if (password.length() < 10) throw new Error("Too short"); // ❌ VIOLATION: Stricter than parent
    }
}

```

**5. Postconditions Cannot be Weakened**
A postcondition is a guarantee *after* a method executes. A subclass must fulfill all the guarantees of the parent (though it can add more).

```java
class Car {
    protected int speed = 50;
    public void brake() { 
        speed -= 10; // Guarantee: Speed must decrease
    }
}

class HybridCar extends Car {
    @Override
    public void brake() {
        // speed -= 10; If we omit this, we ❌ VIOLATE the postcondition!
        super.brake();     // ✅ Fulfills parent guarantee
        chargeBattery();   // ✅ Valid: Adding an extra guarantee is fine
    }
}

```

---

### III. Properties Rules

These rules protect the structural integrity and state of the objects over time.

**6. Class Invariants Must be Maintained**
An invariant is a business rule that must always be true for the lifetime of the object. A subclass cannot introduce behavior that breaks this rule.

```java
class BankAccount {
    protected int balance = 0; // Invariant: Balance can NEVER be negative
    
    public void deposit(int amount) { balance += amount; }
}

class HackAccount extends BankAccount {
    public void forceWithdrawal() {
        balance -= 1000; // ❌ VIOLATION: Breaks the non-negative invariant rule
    }
}

```

**7. History Constraint Must be Honored**
Subclasses cannot alter or lock state in a way that the base class did not allow. If a parent class allows a property to be modified, the subclass cannot suddenly disable that modification.

```java
class MutableConfig {
    protected String theme = "Light";
    
    public void setTheme(String newTheme) { 
        this.theme = newTheme; // Parent allows state change
    }
}

class LockedConfig extends MutableConfig {
    @Override
    public void setTheme(String newTheme) {
        throw new UnsupportedOperationException("Theme is locked!"); // ❌ VIOLATION: Blocks allowed state change
    }
}
```
---

## 🚀 Key Takeaways

By adhering to LSP:

* **Predictability:** Interfaces become reliable contracts. If a class implements a method, the caller can trust it to work as intended.
* **Cleaner Client Code:** The need for defensive programming, type-checking, and unexpected exception handling is eliminated.
* **True Polymorphism:** Subclasses can be seamlessly swapped in and out of the application architecture without causing regressions.
