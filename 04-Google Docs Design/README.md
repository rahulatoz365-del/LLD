# 📄 Document Editor Low-Level Design (LLD): A SOLID Principles Case Study
Welcome to the **Document Editor LLD** repository. This project demonstrates the critical transition from a fragile, tightly-coupled codebase to a robust, scalable architecture using **SOLID Object-Oriented Design Principles**.
By comparing BadDesign.java and GoodDesign.java, this repository serves as a practical guide to identifying design anti-patterns and refactoring them into enterprise-grade software.
## 🚀 Overview
The codebase models a simplified Document Editor (similar to Google Docs) capable of handling text, images, and various storage mechanisms.
 * **BadDesign.java**: A monolithic approach where a single class manages state, rendering, and file I/O operations.
 * **GoodDesign.java**: A refactored, modular approach utilizing interfaces, polymorphism, and dependency injection to achieve a decoupled and extensible system.
## 🚩 The Problem: Analyzing the Anti-Pattern (BadDesign.java)
In BadDesign.java, the DocumentEditor class acts as a "God Object." It handles too many concerns, making the code rigid and difficult to maintain. Here is a deep dive into exactly where and how it violates SOLID principles.
### 1. Single Responsibility Principle (SRP) Broken ❌
**The Principle:** A class should have one, and only one, reason to change.
**Where it was broken:** The structural skeleton of the DocumentEditor class mixes state management, string parsing/rendering, and file system operations all into one place.
```java
class DocumentEditor{
    // Responsibility 1: State management
    private List<String> documentContent; //[span_4](start_span)[span_4](end_span)

    // Responsibility 2: Business/Rendering logic
    public String renderDocument() { //[span_5](start_span)[span_5](end_span)
        // ... string manipulation and type-checking logic ...
    }

    // Responsibility 3: Infrastructure/File I/O logic
    public void saveDocument() { //[span_6](start_span)[span_6](end_span)
        try{
            FileWriter writer = new FileWriter("document.txt"); //[span_7](start_span)[span_7](end_span)
            // ... file writing logic ...
        } // ...
    }
}

```
**Why this is a problem:** If we change how elements are rendered, we modify this class. If we change how files are saved, we modify this *same* class. It has multiple reasons to change, making it prone to regression bugs.
### 2. Open/Closed Principle (OCP) Broken ❌
**The Principle:** Software entities should be open for extension, but closed for modification.
**Where it was broken:** The violation happens right in the middle of the renderDocument() method.
```java
for (String content : documentContent) { //[span_9](start_span)[span_9](end_span)
    // ⚠️ VIOLATION: Rigid type-checking via if/else
    if (content.length() > 4 && (content.endsWith(".png") || content.endsWith(".jpg") || content.endsWith(".jpeg"))) { //[span_10](start_span)[span_10](end_span)
        sb.append("[Image: ").append(content).append("]\n"); //[span_11](start_span)[span_11](end_span)
    } else {
        sb.append(content).append("\n"); //[span_12](start_span)[span_12](end_span)
    }
}

```
**Why this is a problem:** If a new requirement dictates that the editor must support .gif files or HTML tables, you are forced to physically open renderDocument() and add a chain of else if statements. Modifying existing, working logic to add new features increases the risk of breaking current functionality.
### 3. Dependency Inversion Principle (DIP) Broken ❌
**The Principle:** High-level modules should not depend on low-level modules. Both should depend on abstractions.
**Where it was broken:** Look closely at the saveDocument() method.
```java
public void saveDocument() { //[span_14](start_span)[span_14](end_span)
    try{
        // ⚠️ VIOLATION: Tightly coupling high-level logic to a specific low-level tool
        FileWriter writer = new FileWriter("document.txt"); //[span_15](start_span)[span_15](end_span)
        
        writer.write(renderDocument()); //[span_16](start_span)[span_16](end_span)
        writer.close(); //[span_17](start_span)[span_17](end_span)
    } catch (IOException e) { //[span_18](start_span)[span_18](end_span)
        // ...
    }
}

```
**Why this is a problem:** DocumentEditor is our high-level business logic. FileWriter is a low-level, specific implementation of local disk I/O. By using the new keyword, the editor is permanently bolted to the local hard drive. You cannot save to a cloud database or easily write unit tests without hitting the real file system.
## ✅ The Solution: Inside GoodDesign.java
The refactored design addresses every violation by breaking the system down into small, focused, and interchangeable components.
### 1. Fixing SRP: Delegating Responsibilities
Instead of one massive class, responsibilities are now cleanly divided:
 * **DocumentElement (and its implementations):** Handle only how a specific element represents itself via the render() method.
 * **Document:** Acts purely as a collection, managing the state (the list of elements).
 * **Persistance (and its implementations):** Handle only the infrastructure logic of saving data.
 * **DocumentEditor:** Acts merely as an orchestrator tying the components together.
### 2. Fixing OCP: Embracing Polymorphism
The rigid if/else rendering loop is gone. We introduced the DocumentElement interface:
```java
interface DocumentElement { //[span_25](start_span)[span_25](end_span)
    public abstract String render(); //[span_26](start_span)[span_26](end_span)
}

```
Now, the Document class simply loops through its elements and calls .render().
```java
// Inside Document class
public String render() { //[span_28](start_span)[span_28](end_span)
    StringBuilder sb = new StringBuilder(); //[span_29](start_span)[span_29](end_span)
    for (DocumentElement element : elements) { //[span_30](start_span)[span_30](end_span)
        sb.append(element.render()); // Polymorphism in action[span_31](start_span)[span_31](end_span)!
    }
    return sb.toString(); //[span_32](start_span)[span_32](end_span)
}

```
If you want to add a VideoElement tomorrow, you simply create a new class implementing DocumentElement. **You do not need to modify a single line of existing code.** The system is now open for extension, but closed for modification.
### 3. Fixing DIP: Dependency Injection
The high-level DocumentEditor no longer cares *how* a document is saved. It depends entirely on the Persistance abstraction:
```java
interface Persistance { //[span_34](start_span)[span_34](end_span)
    void save(String content); //[span_35](start_span)[span_35](end_span)
}

```
Through **Constructor Injection**, we pass a concrete storage strategy into the DocumentEditor when we initialize it:
```java
// We can easily swap FileStorage for DBStorage here!
Persistance persistance = new FileStorage(); // or new DBStorage();[span_37](start_span)[span_37](end_span)
DocumentEditor editor = new DocumentEditor(document, persistance); //[span_38](start_span)[span_38](end_span)

```
The editor relies entirely on the interface, meaning we can swap from local storage to a database like DBStorage at runtime without altering the editor's core logic.
## 🔍 Part 2: What about LSP and ISP?
While SRP, OCP, and DIP are the most visibly broken in BadDesign.java, the **Liskov Substitution Principle (LSP)** and **Interface Segregation Principle (ISP)** tell a fascinating story about structural foundations.
### ⚠️ The Void: LSP and ISP in BadDesign.java
Strictly speaking, BadDesign.java does not explicitly *break* LSP or ISP because **it lacks the foundational object-oriented architecture required to even apply them.** Both LSP and ISP rely on the existence of interfaces or inheritance hierarchies, which are entirely absent here.
 * **Why ISP doesn't exist here:** There are no interfaces to segregate. DocumentEditor acts as a giant, bloated API for everything.
 * **Why LSP doesn't exist here:** There are no subclasses. Instead of using polymorphic objects, the developer used **Primitive Obsession**, forcing plain String types to act as both text and images.
**The Conceptual Violation:** Even though there are no subclasses, the *spirit* of LSP is violated in the renderDocument() method. Because a String is being used to represent an Image, the code has to check the string's content (content.endsWith(".png")) to treat it differently. In a healthy system, objects should behave predictably without the parent system having to check their internal "type" to avoid breaking the layout.
### ✅ The Triumph: LSP and ISP in GoodDesign.java
In GoodDesign.java, both LSP and ISP are beautifully maintained. They act as the structural glue that makes the OCP and DIP refactoring possible.
#### 4. Liskov Substitution Principle (LSP) Maintained 🏆
**The Principle:** Objects of a superclass/interface shall be replaceable with objects of its subclasses without breaking the application.
**How it is maintained:**
Look at how the Document class handles rendering in GoodDesign.java:
```java
class Document { //[span_46](start_span)[span_46](end_span)
    private List<DocumentElement> elements= new ArrayList<>(); //[span_47](start_span)[span_47](end_span)

    public String render() { //[span_48](start_span)[span_48](end_span)
        StringBuilder sb = new StringBuilder(); //[span_49](start_span)[span_49](end_span)
        // 🌟 LSP IN ACTION: 
        for (DocumentElement element : elements) { //[span_50](start_span)[span_50](end_span)
            sb.append(element.render());  //[span_51](start_span)[span_51](end_span)
        }
        return sb.toString(); //[span_52](start_span)[span_52](end_span)
    }
}

```
**Why this is perfect:** The Document class iterating through the loop has absolutely no idea if the element is a TextElement, an ImageElement, or a NewLineElement. And it doesn't need to know!
Because every subclass perfectly honors the DocumentElement interface contract (they all return a valid String when .render() is called), they can be seamlessly substituted for one another. You can swap a TextElement for an ImageElement at runtime, and the application will not crash or require type-checking. This is LSP working flawlessly.
#### 5. Interface Segregation Principle (ISP) Maintained 🏆
**The Principle:** No code should be forced to depend on methods it does not use. Interfaces should be small and highly focused.
**How it is maintained:**
Notice how the refactored code defines its interfaces. They are extremely narrow and specific to a single capability:
```java
// 🌟 ISP IN ACTION: Narrow, focused interfaces
interface DocumentElement { //[span_56](start_span)[span_56](end_span)
    public abstract String render(); // Only cares about rendering[span_57](start_span)[span_57](end_span)
}

interface Persistance { //[span_58](start_span)[span_58](end_span)
    void save(String content);       // Only cares about saving[span_59](start_span)[span_59](end_span)
}

```
**Why this is perfect:** Imagine if the developer had created a single, "fat" interface instead that required both render() and saveToDisk(). The TextElement class would be forced to implement a saving method that it doesn't need (since text elements shouldn't know how to save files).
By segregating the interfaces into DocumentElement (for UI presentation) and Persistance (for data storage), GoodDesign.java ensures that implementers like TextElement and DBStorage only write code for exactly what they do, keeping the architecture lean.
## 🛠️ Architecture Map (Good Design)
 * **Core Domain:**
   * DocumentElement *(Interface)*
     * ↳ TextElement
     * ↳ ImageElement
     * ↳ NewLineElement
     * ↳ TabSpaceElement
 * **State Management:**
   * Document *(Holds List<DocumentElement>)*
 * **Infrastructure Layer:**
   * Persistance *(Interface)*
     * ↳ FileStorage
     * ↳ DBStorage
 * **Orchestration Layer:**
   * DocumentEditor *(Coordinates Document and Persistance)*