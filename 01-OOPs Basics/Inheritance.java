/*  Inheritance Definition -> Inheritance is a mechanism in which one class acquires the properties (fields) and behaviors (methods) of another class. 
                            The class which inherits the properties of another is known as a subclass (derived class, child class), 
                            and the class whose properties are inherited is known as a superclass (base class, parent class).
    Inheritance Examples ->  Take the example of a Vehicle class and its subclasses like Car, Bike, etc. 
                            The Vehicle class has properties like speed, color, and methods like start(), stop(). 
                            The Car and Bike classes inherit these properties and methods from the Vehicle class.
    Types of Inheritance in Java ->  There are five types of inheritance in Java:
                            1. Single Inheritance: A subclass inherits from one superclass.
                            2. Multilevel Inheritance: A subclass inherits from a superclass, and another subclass inherits from that subclass.
                            3. Hierarchical Inheritance: Multiple subclasses inherit from a single superclass.
                            4. Multiple Inheritance (through interfaces): A class can implement multiple interfaces.
                            5. Hybrid Inheritance: A combination of two or more types of inheritance.
 */

// Vehicle class is the superclass
class Vehicle {
    protected String color; // Protected variable, accessible in subclasses
    protected String model; // Protected variable, accessible in subclasses
    protected String brand; // Protected variable, accessible in subclasses
    // Constructor to initialize the Vehicle object
    public Vehicle(String color) {
        this.color = color;
    }

    // Method to start the vehicle
    public void start() {
        System.out.println(brand + " " + model + " is starting");
    }

    // Method to stop the vehicle
    public void stop() {
        System.out.println(brand + " " + model + " is stopping");
    }
}
// Manual Car class inherits from Vehicle class
class ManualCar extends Vehicle {
    private int gear; // Private variable, accessible only within the ManualCar class
    // Constructor to initialize the ManualCar object
    public ManualCar(String color, String model, String brand, int gear) {
        super(color); // Call the constructor of the superclass (Vehicle)
        this.model = model; // Initialize model variable
        this.brand = brand; // Initialize brand variable
        this.gear = gear; // Initialize gear variable
    }

    // Method to change the gear of the manual car
    public void changeGear(int newGear) {
        this.gear = newGear;
        System.out.println(brand + " " + model + " which is a manual car has changed its gear to: " + gear);
    }
}
// Automatic Car class inherits from Vehicle class
class AutomaticCar extends Vehicle {
    private String transmissionType; // Private variable, accessible only within the AutomaticCar class
    // Constructor to initialize the AutomaticCar object
    public AutomaticCar(String color, String model, String brand, String transmissionType) {
        super(color); // Call the constructor of the superclass (Vehicle)
        this.model = model; // Initialize model variable
        this.brand = brand; // Initialize brand variable
        this.transmissionType = transmissionType; // Initialize transmissionType variable
    }

    // Method to change the transmission type of the automatic car
    public void changeTransmission(String newTransmissionType) {
        this.transmissionType = newTransmissionType;
        System.out.println("Automatic Car transmission changed to: " + transmissionType);
    }
}
// Main method to demonstrate inheritance
public class Inheritance {
    public static void main(String[] args) {
        // Creating an object of ManualCar class and calling the methods inherited from Vehicle class
        ManualCar manualCar = new ManualCar("Red", "Thar", "Tata", 5);
        manualCar.start(); // Calling start method from Vehicle class
        manualCar.changeGear(3); // Calling changeGear method from ManualCar class
        manualCar.stop(); // Calling stop method from Vehicle class
        // Creating an object of AutomaticCar class and calling the methods inherited from Vehicle class
        AutomaticCar automaticCar = new AutomaticCar("Yellow", "EV", "Tesla", "Automatic");
        automaticCar.start(); // Calling start method from Vehicle class
        automaticCar.changeTransmission("Semi-Automatic"); // Calling changeTransmission method from AutomaticCar class
        automaticCar.stop(); // Calling stop method from Vehicle class
    }
}