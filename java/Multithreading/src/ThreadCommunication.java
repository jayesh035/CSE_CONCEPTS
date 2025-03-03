public class ThreadCommunication {
    static int data = 0;
    static final int CAPACITY = 500;

    synchronized void produce(int k) {
        while (data + k > CAPACITY) { // Ensure we do not exceed buffer size
            try {
                wait(); // Wait if adding would exceed capacity
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        data += k;
        System.out.println(Thread.currentThread().getName() + " produced " + k + " bytes, total: " + data);
        notifyAll(); // Notify consumers
    }

    synchronized void consume(int k) {
        while (data < k) { // Wait until there's enough data to consume
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        data -= k;
        System.out.println(Thread.currentThread().getName() + " consumed " + k + " bytes, remaining: " + data);
        notifyAll(); // Notify producers
    }

    public static void main(String[] args) {
        ThreadCommunication buffer = new ThreadCommunication();

        Thread producer = new Thread(() -> {
           int i=1;
            while (true){ // Reduced iterations for better synchronization
                buffer.produce(i);
                try {
                    Thread.sleep(500); // Simulate production time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer");

        Thread consumer1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                buffer.consume(i);
                try {
                    Thread.sleep(700); // Simulate consumption time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer1");

        Thread consumer2 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                buffer.consume(i);
                try {
                    Thread.sleep(900); // Simulate consumption time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer2");

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}
