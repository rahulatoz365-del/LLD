/*  Abstraction Definition -> The process of hiding the implementation details 
                            and showing only the essential features of an object.
                            Abstraction is achieved through abstract classes and interfaces.
    Abstraction Examples ->  Take the example of Animal class and its subclasses like Dog, Cat, etc. 
                            Now  Animals eat, sleep, and make sounds.
                            So the abstraction or interface only states the methods.
                            The method is defined or the behaviour of methods present in 
                            abstract class is defined by the subclasses inheriting the abstract class.
                            So abstractration only mean naming the methods in the abstract class or interface.
*/

interface Animal {
    void eat();
    void sleep();
    void makeSound();
}
// Dog class implements the Animal interface and provides the implementation for the methods defined in the interface.
class Dog implements Animal {
    @Override // Always use the override annotation when implementing methods from an interface or abstract class. It helps to avoid mistakes and makes the code more readable.
    public void eat() {
        System.out.println("Dog is eating");
    }

    @Override
    public void sleep() {
        System.out.println("Dog is sleeping");
    }

    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}
// Cat class implements the Animal interface and provides the implementation for the methods defined in the interface.
class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("Cat is eating");
    }

    @Override
    public void sleep() {
        System.out.println("Cat is sleeping");
    }

    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

//Main Method
public class Abstraction {
    public static void main(String[] args) {
        // Creating an object of Dog class and calling the methods defined in the Animal interface.
        Animal dog = new Dog();
        dog.eat();
        dog.sleep();
        dog.makeSound();
        Animal cat = new Cat();
        cat.eat();
        cat.sleep();
        cat.makeSound();
    }
}