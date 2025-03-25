import java.util.ArrayList;
import java.util.List;

public class PerformanceTest {
    private  final List<byte[]> memoryeak = new ArrayList<>();
    private static volatile boolean running = true;

  public void startTest() {
        // Simulate memory leak
        new Thread(() -> {
            while (running) {
                memoryeak.add(new byte[1024 * 1024]); // 1MB per second
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }).start();

        // Simulate high CPU usage
        new Thread(() -> {
            while (running) {
                // Infinite loop consuming CPU
            }
        }).start();

        // Simulate thread contention
        Object lock = new Object();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    while (running) {
                        try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    }
                }
            }).start();
        }

        // Keep the app running
        try { Thread.sleep(Long.MAX_VALUE); } catch (InterruptedException e) {}
        running = false;
    }
}