// Subsystems
class PowerSupply{
    public void powerOn(){
        System.out.println("Power supply is on");
    }
}
class CPU{
    public void start(){
        System.out.println("CPU is starting");
    }
}
class Memory{
    public void load(){
        System.out.println("Memory is loading");
    }
}
class HardDrive{
    public void read(){
        System.out.println("Hard drive is reading");
    }
}
class GraphicsCard{
    public void render(){
        System.out.println("Graphics card is rendering");
    }
}
class CoolingSystem{
    public void cool(){
        System.out.println("Cooling system is cooling");
    }
}
class BIOS{
    public void initialize(){
        System.out.println("BIOS is initializing");
    }
}
class OperatingSystem{
    public void boot(){
        System.out.println("Operating system is booting");
    }
}
// Facade
class ComputerFacade{
    private PowerSupply powerSupply;
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
    private GraphicsCard graphicsCard;
    private CoolingSystem coolingSystem;
    private BIOS bios;
    private OperatingSystem operatingSystem;

    public ComputerFacade() {
        this.powerSupply = new PowerSupply();
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
        this.graphicsCard = new GraphicsCard();
        this.coolingSystem = new CoolingSystem();
        this.bios = new BIOS();
        this.operatingSystem = new OperatingSystem();
    }

    public void startComputer(){
        System.out.println("Starting computer...");
        powerSupply.powerOn();
        bios.initialize();
        cpu.start();
        memory.load();
        hardDrive.read();
        graphicsCard.render();
        coolingSystem.cool();
        operatingSystem.boot();
        System.out.println("Computer started successfully!");
    }
}
// Client/Main Code
public class FacadePatternDemo {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.startComputer();
    }
}