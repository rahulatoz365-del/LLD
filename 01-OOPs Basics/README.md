# 🏗️ OOP Core Concepts

A quick-reference guide to the four fundamental pillars of Object-Oriented Programming (OOP) with concise Java examples.

---

## 1. 🧩 Abstraction
**Definition:** The process of hiding the implementation details and showing only the essential features of an object[cite: 1]. 
**How it works:** Achieved through abstract classes and interfaces[cite: 1]. The interface states the methods, while subclasses define the specific behavior[cite: 1].

```java
interface Animal {
    void makeSound(); // Essential feature declared[cite: 1]
}

class Dog implements Animal {
    @Override 
    public void makeSound() {
        System.out.println("Dog barks"); // Implementation hidden in subclass[cite: 1]
    }
}

```

---

## 2. 🛡️ Encapsulation

**Definition:** Binding the data (variables) and code (methods) together as a single unit. Variables are hidden from other classes and accessed only through specific methods (data hiding).
**How it works:** Uses access modifiers (`private`, `default`, `protected`, `public`) to restrict access.

```java
class BankAccount {
    private double balance; // Data hidden from outside[cite: 2]

    public void deposit(double amount) { // Controlled access method[cite: 2]
        if (amount > 0) balance += amount;
    }
    
    public double getBalance() { return balance; }
}

```

---

## 3. 🧬 Inheritance

**Definition:** A mechanism where one class (subclass/child) acquires the properties and behaviors of another class (superclass/parent).
**Types in Java:** Single, Multilevel, Hierarchical, Multiple (via interfaces), and Hybrid.

```java
class Vehicle {
    protected String brand; // Parent property[cite: 3]
    public void start() { System.out.println("Starting vehicle"); } // Parent behavior[cite: 3]
}

class Car extends Vehicle {
    private int gear; // Child-specific property[cite: 3]
    
    public Car(String brand) { this.brand = brand; } // Inherits brand from Vehicle[cite: 3]
}

```

---

## 4. 🎭 Polymorphism

**Definition:** The ability of an object to take on many forms, allowing methods to do different things based on the object acting upon them.
**How it works:**

* **Compile-time:** Method Overloading (same method name, different parameters).


* **Runtime:** Method Overriding (subclass provides specific implementation of a parent method).



```java
class Calculator {
    // Compile-time Polymorphism (Overloading)[cite: 4]
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; }
}

class Cat extends Animal { // Assuming Animal from earlier example
    // Runtime Polymorphism (Overriding)[cite: 4]
    @Override
    public void makeSound() {
        System.out.println("Cat meows"); // Specific implementation[cite: 4]
    }
}

```