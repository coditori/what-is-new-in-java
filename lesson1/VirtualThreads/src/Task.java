public class Task {
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {
        Thread.startVirtualThread(() -> {
            synchronized (LOCK) {
                System.out.println("running in synchronized block");
            }
        }).join();
    }
} 