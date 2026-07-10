// Command Interface
interface Command {
    void execute();
    void undo();
}
// Receiver Class
class Light {
    public void turnOn() {
        System.out.println("The light is on");
    }
    public void turnOff() {
        System.out.println("The light is off");
    }
}
class Fan {
    public void turnOn() {
        System.out.println("The fan is on");
    }
    public void turnOff() {
        System.out.println("The fan is off");
    }
}
// Concrete Command Classes
class LightCommand implements Command {
    private Light light;
    public LightCommand(Light light) {
        this.light = light;
    }
    public void execute() {
        light.turnOn();
    }
    public void undo() {
        light.turnOff();
    }
}
class FanCommand implements Command {
    private Fan fan;
    public FanCommand(Fan fan) {
        this.fan = fan;
    }
    public void execute() {
        fan.turnOn();
    }
    public void undo() {
        fan.turnOff();
    }
}
// Invoker Class
class RemoteController {
    private static final int numButtons = 4;
    private Command[] buttons;
    private boolean[] buttonPressed;

    public RemoteController() {
        buttons = new Command[numButtons];
        buttonPressed = new boolean[numButtons];
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = null;
            buttonPressed[i] = false;  // false = off, true = on
        }
    }

    public void setCommand(int idx, Command cmd) {
        if (idx >= 0 && idx < numButtons) {
            buttons[idx] = cmd;
            buttonPressed[idx] = false;
        }
    }

    public void pressButton(int idx) {
        if (idx >= 0 && idx < numButtons && buttons[idx] != null) {
            if (!buttonPressed[idx]) {
                buttons[idx].execute();
            } else {
                buttons[idx].undo();
            }
            buttonPressed[idx] = !buttonPressed[idx];
        } else {
            System.out.println("No command assigned at button " + idx);
        }
    }
}
// Main / Client Class
public class CommandPatternDemo {
    public static void main(String[] args) {
        // Create receiver objects
        Light light = new Light();
        Fan fan = new Fan();

        // Create command objects
        Command lightCommand = new LightCommand(light);
        Command fanCommand = new FanCommand(fan);

        // Create invoker object
        RemoteController remote = new RemoteController();

        // Assign commands to buttons
        remote.setCommand(0, lightCommand);
        remote.setCommand(1, fanCommand);

        // Simulate button presses
        System.out.println("Pressing button 0 (Light):");
        remote.pressButton(0);  // Turn on the light
        System.out.println("Pressing button 0 again (Light):");
        remote.pressButton(0);  // Turn off the light

        System.out.println("Pressing button 1 (Fan):");
        remote.pressButton(1);  // Turn on the fan
        System.out.println("Pressing button 1 again (Fan):");
        remote.pressButton(1);  // Turn off the fan

        System.out.println("Pressing button 2 (No command assigned):");
        remote.pressButton(2);  // No command assigned
    }
}