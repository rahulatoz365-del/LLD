// Strategy Interface for Walkable Robots 
interface Walkable {
    void walk();
}
// Concrete Strategy for Walking on Two Legs
class TwoLeggedWalk implements Walkable {
    @Override
    public void walk() {
        System.out.println("Walking on two legs.");
    }
}
// concrete Strategy for No Walking
class NoWalk implements Walkable {
    @Override
    public void walk() {
        System.out.println("Cannot walk.");
    }
}
// Strategy Interface for flying Robots
interface Flyable {
    void fly();
}
// Concrete Strategy for Flying with Wings
class WingedFlight implements Flyable {
    @Override
    public void fly() {
        System.out.println("Flying with wings.");
    }
}
// Concrete Strategy for Flying with Jetpack
class JetpackFlight implements Flyable {    
    @Override
    public void fly() {
        System.out.println("Flying with a jetpack.");
    }
}
// Base Robot class that uses the Strategy Pattern
abstract class Robot {
    protected Walkable walkBehavior;
    protected Flyable flyBehavior;

    public Robot(Walkable walkBehavior, Flyable flyBehavior) {
        this.walkBehavior = walkBehavior;
        this.flyBehavior = flyBehavior;
    }

    public void performWalk() {
        walkBehavior.walk();
    }

    public void performFly() {
        flyBehavior.fly();
    }
    public abstract void projection();
}
// Concrete Robot Types
class HumanoidRobot extends Robot {
    public HumanoidRobot() {
        super(new TwoLeggedWalk(), new WingedFlight());
    }

    @Override
    public void projection() {
        System.out.println("I am a humanoid robot.");
    }
}
class DroneRobot extends Robot {
    public DroneRobot() {
        super(new NoWalk(), new JetpackFlight());
    }

    @Override
    public void projection() {
        System.out.println("I am a drone robot.");
    }
}
// Main class to demonstrate the Strategy Pattern
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Robot humanoid = new HumanoidRobot();
        humanoid.projection();
        humanoid.performWalk();
        humanoid.performFly();

        System.out.println();

        Robot drone = new DroneRobot();
        drone.projection();
        drone.performWalk();
        drone.performFly();
    }
}