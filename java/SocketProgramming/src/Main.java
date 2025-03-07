import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private final Lock lock = new ReentrantLock();

    void lockAndUseResource() {
        lock.lock(); // Locking here
        try {
            System.out.println(Thread.currentThread().getName() + " acquired the lock");
            System.out.println(Thread.currentThread().getName() + " is using the resource...");
            Thread.sleep(2000); // Simulating work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            unlockResource(); // Unlocking here (within the same thread)
        }
    }

    void unlockResource() {
        lock.unlock();
        System.out.println(Thread.currentThread().getName() + " released the lock");
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(resource::lockAndUseResource, "Thread-1");
        Thread t2 = new Thread(resource::lockAndUseResource, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
