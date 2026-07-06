public class NoSingletonDemo {
    public NoSingletonDemo() {
        System.out.println("Singleton Constructor called. New Object created.");
    }

    public static void main(String[] args) {
        NoSingletonDemo s1 = new NoSingletonDemo();
        NoSingletonDemo s2 = new NoSingletonDemo();

        System.out.println(s1 == s2);
    }
}