public class ThreadSafeEagerSingletonDemo {
    private static ThreadSafeEagerSingletonDemo instance = new ThreadSafeEagerSingletonDemo();

    private ThreadSafeEagerSingletonDemo() {
        System.out.println("Singleton Constructor Called!");
    }

    public static ThreadSafeEagerSingletonDemo getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        ThreadSafeEagerSingletonDemo s1 = ThreadSafeEagerSingletonDemo.getInstance();
        ThreadSafeEagerSingletonDemo s2 = ThreadSafeEagerSingletonDemo.getInstance();

        System.out.println(s1 == s2);
    }
}