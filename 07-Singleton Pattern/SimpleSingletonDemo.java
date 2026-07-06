public class SimpleSingletonDemo {
    private static SimpleSingletonDemo instance = null;

    private SimpleSingletonDemo() {
        System.out.println("Singleton Constructor called");
    }

    public static SimpleSingletonDemo getInstance() {
        if (instance == null) {
            instance = new SimpleSingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        SimpleSingletonDemo s1 = SimpleSingletonDemo.getInstance();
        SimpleSingletonDemo s2 = SimpleSingletonDemo.getInstance();

        System.out.println(s1 == s2);
    }
}