// Interface for 2D shapes
interface Shape2D {
    double calculateArea();
}
// Interface for 3D shapes
interface Shape3D {
    double calculateVolume();
    double calculateSurfaceArea();
}
// Class for Circle implementing Shape2D
class Circle implements Shape2D {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}
// Class for Square implementing Shape2D
class Square implements Shape2D {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }
}
// Class for Sphere implementing Shape3D
class Sphere implements Shape3D {
    private double radius;

    public Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    @Override
    public double calculateSurfaceArea() {
        return 4 * Math.PI * radius * radius;
    }
}
// Class for Cube implementing Shape3D
class Cube implements Shape3D {
    private double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double calculateVolume() {
        return Math.pow(side, 3);
    }

    @Override
    public double calculateSurfaceArea() {
        return 6 * side * side;
    }
}
// Maain class to demonstrate the functionality
public class CorrectCode {
    public static void main(String[] args) {
        Shape2D circle = new Circle(5);
        System.out.println("Circle Area: " + circle.calculateArea());

        Shape2D square = new Square(4);
        System.out.println("Square Area: " + square.calculateArea());

        Shape3D sphere = new Sphere(3);
        System.out.println("Sphere Volume: " + sphere.calculateVolume());
        System.out.println("Sphere Surface Area: " + sphere.calculateSurfaceArea());

        Shape3D cube = new Cube(2);
        System.out.println("Cube Volume: " + cube.calculateVolume());
        System.out.println("Cube Surface Area: " + cube.calculateSurfaceArea());
    }
}