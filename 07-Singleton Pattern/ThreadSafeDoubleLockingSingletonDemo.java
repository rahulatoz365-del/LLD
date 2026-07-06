public class ThreadSafeDoubleLockingSingletonDemo {
    private static ThreadSafeDoubleLockingSingletonDemo instance = null;

    private ThreadSafeDoubleLockingSingletonDemo() {
        System.out.println("Singleton Constructor Called!");
    }

    // Double check locking..
    public static ThreadSafeDoubleLockingSingletonDemo getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (ThreadSafeDoubleLockingSingletonDemo.class) { // Lock only if needed
                if (instance == null) { // Second check (after acquiring lock)
                    instance = new ThreadSafeDoubleLockingSingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        ThreadSafeDoubleLockingSingletonDemo s1 = ThreadSafeDoubleLockingSingletonDemo.getInstance();
        ThreadSafeDoubleLockingSingletonDemo s2 = ThreadSafeDoubleLockingSingletonDemo.getInstance();

        System.out.println(s1 == s2);
    }
}