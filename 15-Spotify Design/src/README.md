# 🎵 Spotify Clone: Music Player Application - Technical Documentation

## 1. System Overview

The Music Player Application is a console-based, object-oriented audio streaming system built in Java. The application simulates the complex backend orchestration of a modern media player like Spotify: navigating song libraries, curating playlists, routing audio to various hardware outputs (Bluetooth, Wired, Speakers), and dynamically switching playback algorithms (Sequential, Random, Custom Queue).

The architecture is heavily modularized, separating external hardware API dependencies from core playback logic, and utilizing a facade to simplify client interactions.

### **Directory Architecture**

* `models/`: Contains the pure domain entities (`Songs`, `Playlist`).
* `managers/`: Handles the centralized state and memory lifecycle (`PlaylistManager`, `StrategyManager`, `DeviceManager`).
* `strategies/`: Encapsulates interchangeable playback algorithms and queue behaviors.
* `core/`: Contains the state machine for the playback engine (`AudioEngine`).
* `device/` & `externals/`: Houses hardware interfaces and adapters to bridge internal logic with external/legacy audio APIs.
* `factory/`: Abstracts the creational logic for instantiating hardware connections.
* `enums/`: Shared system constants (`DeviceType`, `PlayerStrategy`).

---

## 2. Design Patterns Implemented

The codebase leverages five major Gang of Four (GoF) design patterns to ensure scalability, loose coupling, and maintainability.

### **A. Strategy Pattern (Behavioral)**

* **Where it is used:** `PlayStrategy`, `SequentialStrategy`, `RandomStrategy`, `CustomStrategy`.
* **How it works:** The `MusicPlayerFacade` does not hardcode the rules for "what song plays next." Instead, it holds a reference to a `PlayStrategy` interface. When `next()` or `previous()` is called, it delegates the execution to the currently injected strategy.
* **Why it was used:** To allow the application to switch playback algorithms dynamically at runtime without cluttering the core engine with massive `if/else` statements.

### **B. Adapter Pattern (Structural)**

* **Where it is used:** `IAudioOutputDevice` (Target), `BluetoothAdapter` (Adapter), `BluetoothAPI` (Adaptee).
* **How it works:** The `AudioEngine` only knows how to talk to `IAudioOutputDevice.playAudio()`. The simulated external hardware libraries have their own proprietary methods (e.g., `bluetoothSound()`, `wiredSound()`). The Adapters wrap these incompatible APIs and translate the calls.
* **Why it was used:** To protect the core application from breaking if third-party hardware APIs change in the future.

### **C. Facade Pattern (Structural)**

* **Where it is used:** `MusicPlayerFacade`.
* **How it works:** It provides a simplified, unified interface to a set of complex subsystems (`AudioEngine`, `DeviceManager`, `StrategyManager`).
* **Why it was used:** To decouple the client (`Main.java` or `MusicPlayerApp`) from the internal workings. The client doesn't need to manually synchronize the audio engine with the specific output device or strategy; the Facade handles the cross-communication.

### **D. Simple Factory Pattern (Creational)**

* **Where it is used:** `DeviceFactory`.
* **How it works:** A static method `createDevice(DeviceType)` evaluates an enum and returns the correctly instantiated Adapter.
* **Why it was used:** It extracts the boilerplate creation logic of hardware connections out of the `DeviceManager`, keeping the manager strictly focused on state tracking.

### **E. Singleton Pattern (Creational)**

* **Where it is used:** `MusicPlayerApp`, `MusicPlayerFacade`, `DeviceManager`, `PlaylistManager`, `StrategyManager`.
* **How it works:** Implemented via private constructors and `public static synchronized getInstance()` methods.
* **Why it was used:** Ensures there is only one global state for the active device, current playlist, loaded strategies, and playback engine across the entire application runtime.

---

## 3. SOLID Principles Analysis

The system demonstrates a strong grasp of object-oriented architecture, effectively isolating hardware dependencies. However, it makes a few structural trade-offs. Here is a deep dive into how the 5 SOLID principles were handled.

### **1. Single Responsibility Principle (SRP)**

*A class should have one, and only one, reason to change.*

* ✅ **Followed:**
* `AudioEngine` is strictly responsible for play/pause state management.
* `Models` (`Songs`, `Playlist`) act as pure data carriers.


* ❌ **Broken:**
* `CustomStrategy`: This class manages three separate concerns: sequential fallback logic, a Stack for playback history, and a Queue for custom-added next tracks. If the rules for *any* of these change, the class must be modified.



### **2. Open/Closed Principle (OCP)**

*Software entities should be open for extension, but closed for modification.*

* ✅ **Followed:** * **Hardware Extension:** If the business introduces a `WiFiCastingAPI`, you simply create a new `WiFiAdapter` implementing `IAudioOutputDevice`. You do not have to touch a single line of code in the `AudioEngine`.
* ❌ **Broken:** * **Device Factory:** Look at `DeviceFactory.java`:
`java switch (deviceType) { case BLUETOOTH: return new BluetoothAdapter(new BluetoothAPI()); case WIRED: return new WiredAdapter(new WiredAPI()); // ... } `
If a new output device is added, you are forced to open and modify this factory class to add a new `case` block.

### **3. Liskov Substitution Principle (LSP)**

*Subtypes must be substitutable for their base types without altering the correctness of the program.*

* ✅ **Followed:** * The `AudioEngine` accepts ANY `IAudioOutputDevice`. Passing a `BluetoothAdapter` or a `WiredAdapter` yields the exact same programmatic behavior from the engine's perspective without crashing.
* ❌ **Broken:** * `RandomStrategy`'s `previous()` method will throw a hard `RuntimeException` if the history stack is empty. A client substituting `SequentialStrategy` (which safely calculates numerical indices) with `RandomStrategy` might encounter unexpected runtime crashes if they don't account for this specific exception.

### **4. Interface Segregation Principle (ISP)**

*Clients should not be forced to depend upon interfaces that they do not use.*

* ✅ **Followed:** * `IAudioOutputDevice` is highly focused and lean. It forces a single contract: `playAudio(Songs song)`.
* ❌ **Broken:** * `PlayStrategy` includes a `default void addToNext(Songs song)` method. This behavior *only* applies to the `CustomStrategy` (Queue behavior). `SequentialStrategy` and `RandomStrategy` inherit this method despite it being entirely useless to their algorithms, polluting their API.

### **5. Dependency Inversion Principle (DIP)**

*High-level modules should not depend on low-level modules. Both should depend on abstractions.*

* ✅ **Followed:** * The `AudioEngine` (high-level) depends entirely on `IAudioOutputDevice` (abstraction), not on `SpeakerAPI` or `WiredAPI` (low-level details).
* ❌ **Minor Violation:** * `MusicPlayerFacade` directly instantiates its dependency in its constructor (`audioEngine = new AudioEngine();`). True Dependency Injection would pass the engine in from the outside, making the Facade easier to unit test with a mock engine.

---

## 4. Architectural Vulnerabilities & Future Improvements

1. **Thread Safety Bottlenecks:** The `getInstance()` methods in all Managers use the `synchronized` keyword directly on the method signature. While thread-safe, this locks the entire method on every call, causing performance overhead in a highly concurrent environment. **Fix:** Refactor to use Double-Checked Locking or eager initialization.
2. **Broad Exception Handling:** The application heavily relies on throwing standard `RuntimeException` with string messages (e.g., `throw new RuntimeException("No playlist loaded.")`). **Fix:** Implement a custom exception hierarchy (e.g., `EmptyPlaylistException`, `DeviceNotConnectedException`) to allow the client to catch and handle specific errors gracefully.
3. **Presentation Coupling:** Core domain and engine classes (like `AudioEngine` and adapters) rely heavily on `System.out.println`. **Fix:** Abstract the logging mechanism by passing an `OutputStream` or utilizing a logging interface (like `SLF4J`), which would make the core logic fully decoupled from the console and easier to test.
4. **Missing Input Validation:** The `Songs` model blindly accepts any string for the `filePath`. **Fix:** Add basic regex or URI validation at the model or manager level to ensure malformed paths aren't loaded into the engine.