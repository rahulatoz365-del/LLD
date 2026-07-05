// Interface for both 2D and 3D shapes
interface Shape {
    double calculateArea();
    double calculateVolume();
}
// Class for Circle implementing Shape
class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculateVolume() {
        throw new UnsupportedOperationException("Volume calculation is not supported for Circle shapes.");
    }
}
// Class for Square implementing Shape
class Square implements Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public double calculateVolume() {
        throw new UnsupportedOperationException("Volume calculation is not supported for Square shapes.");
    }
}
// Class for Sphere implementing Shape
class Sphere implements Shape {
    private double radius;

    public Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return 4 * Math.PI * radius * radius;
    }

    @Override
    public double calculateVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }
}
// Class for Cube implementing Shape
class Cube implements Shape {
    private double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return 6 * side * side;
    }

    @Override
    public double calculateVolume() {
        return Math.pow(side, 3);
    }
}
// Main class to demonstrate the usage of shapes
public class WrongCode {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        System.out.println("Circle Area: " + circle.calculateArea());
        try {
            System.out.println("Circle Volume: " + circle.calculateVolume());
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }

        Shape square = new Square(4);
        System.out.println("Square Area: " + square.calculateArea());
        try {
            System.out.println("Square Volume: " + square.calculateVolume());
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }

        Shape sphere = new Sphere(3);
        try {
            System.out.println("Sphere Area: " + sphere.calculateArea());
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Sphere Volume: " + sphere.calculateVolume());

        Shape cube = new Cube(2);
        try {
            System.out.println("Cube Area: " + cube.calculateArea());
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Cube Volume: " + cube.calculateVolume());
    }
}