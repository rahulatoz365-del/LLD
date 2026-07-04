/* Polymorphism Definition -> Polymorphism is the ability of an object to take on many forms. In Java, polymorphism allows methods to do different things based on the object that it is acting upon. It is one of the core concepts of Object-Oriented Programming (OOP) and can be achieved through method overloading and method overriding.

    Polymorphism Examples ->  Take the example of a Shape class and its subclasses like Circle, Rectangle, etc. 
                            The Shape class has a method called draw(). 
                            The Circle and Rectangle classes override this method to provide their specific implementation.
                            When we call the draw() method on a Shape reference, it will execute the appropriate draw() method based on the actual object type (Circle or Rectangle).

    Types of Polymorphism in Java ->  There are two types of polymorphism in Java:
                            1. Compile-time Polymorphism (Method Overloading): This occurs when multiple methods have the same name but different parameters (different type or number of parameters).
                            2. Runtime Polymorphism (Method Overriding): This occurs when a subclass provides a specific implementation of a method that is already defined in its superclass.
*/
//Method Overloading Example
class Calculator {
    // Method to add two integers
    public int add(int a, int b) {
        return a + b;
    }

    // Overloaded method to add three integers
    public int add(int a, int b, int c) {
        return a + b + c;
    }

    // Overloaded method to add two double values
    public double add(double a, double b) {
        return a + b;
    }
}
// Method Overriding Example
class Animal {
    // Method to make sound
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}
class Dog extends Animal {
    // Overriding the makeSound method of Animal class
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}
class Cat extends Animal {
    // Overriding the makeSound method of Animal class
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}
// Main Method
public class Polymorphism {
    public static void main(String[] args) {
        // Compile-time Polymorphism (Method Overloading)
        Calculator calculator = new Calculator();
        System.out.println("Sum of two integers: " + calculator.add(5, 10));
        System.out.println("Sum of three integers: " + calculator.add(5, 10, 15));
        System.out.println("Sum of two doubles: " + calculator.add(5.5, 10.5));

        // Runtime Polymorphism (Method Overriding)
        Animal myDog = new Dog(); // Animal reference but Dog object
        myDog.makeSound(); // Calls the overridden method in Dog class
        Animal myCat = new Cat(); // Animal reference but Cat object
        myCat.makeSound(); // Calls the overridden method in Cat class
    }
}