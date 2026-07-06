public class ThreadSafeLockingSingletonDemo {
    private static ThreadSafeLockingSingletonDemo instance = null;

    private ThreadSafeLockingSingletonDemo() {
        System.out.println("Singleton Constructor Called!");
    }

    public static ThreadSafeLockingSingletonDemo getInstance() {
        synchronized (ThreadSafeLockingSingletonDemo.class) { // Lock for thread safety
            if (instance == null) {
                instance = new ThreadSafeLockingSingletonDemo();
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        ThreadSafeLockingSingletonDemo s1 = ThreadSafeLockingSingletonDemo.getInstance();
        ThreadSafeLockingSingletonDemo s2 = ThreadSafeLockingSingletonDemo.getInstance();

        System.out.println(s1 == s2);
    }
}