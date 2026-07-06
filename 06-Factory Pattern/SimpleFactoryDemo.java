//Burger Interface 
interface Burger {
    void prepare();
}
// Concrete class for Burger:StandardBurger,BasicBurger,PremiumBurger
class StandardBurger implements Burger {
    @Override
    public void prepare() {
        System.out.println("Preparing a standard burger.");
    }
}
class BasicBurger implements Burger {
    @Override
    public void prepare() {
        System.out.println("Preparing a basic burger.");
    }
}
class PremiumBurger implements Burger {
    @Override
    public void prepare() {
        System.out.println("Preparing a premium burger.");
    }
}
// Factory class to create different types of burgers
class BurgerFactory {
    public static Burger createBurger(String type) {
        switch (type.toLowerCase()) {
            case "standard":
                return new StandardBurger();
            case "basic":
                return new BasicBurger();
            case "premium":
                return new PremiumBurger();
            default:
                throw new IllegalArgumentException("Unknown burger type: " + type);
        }
    }
}
// Client code to demonstrate the Factory Pattern
public class SimpleFactoryDemo {
    public static void main(String[] args) {
        // Create a standard burger
        Burger standardBurger = BurgerFactory.createBurger("standard");
        standardBurger.prepare();

        // Create a basic burger
        Burger basicBurger = BurgerFactory.createBurger("basic");
        basicBurger.prepare();

        // Create a premium burger
        Burger premiumBurger = BurgerFactory.createBurger("premium");
        premiumBurger.prepare();
    }
}