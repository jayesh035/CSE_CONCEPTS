import java.util.concurrent.*;

public class CyclicBarrierCode {


    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        int numberOfServices=3;
        ExecutorService service= Executors.newFixedThreadPool(numberOfServices);
        java.util.concurrent.CyclicBarrier barrier=
                new java.util.concurrent.CyclicBarrier(numberOfServices);
        Future<String> res1= service.submit(new DependentServices(barrier));
        Future<String>res2=  service.submit(new DependentServices(barrier));
        Future<String>res3=  service.submit(new DependentServices(barrier));

        barrier.await();
//        Executors.newWorkStealingPool()
//
//        for(int i=0;i<numberOfServices;i++){
//            new Thread(new ManualThread(latch)).start();
//
//        }
//        barrier.await(5, TimeUnit.SECONDS);


        System.out.println("All services are completed");
//        service.shutdownNow();
        service.shutdown();
    }
}

class DependentServices implements Callable<String> {

    private final java.util.concurrent.CyclicBarrier barrier;

    DependentServices(CyclicBarrier barrier){
        this.barrier=barrier;
    }

    @Override
    public String call() throws Exception {



            System.out.println(Thread.currentThread().getName()+" is running");
    Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+" is waiting for all bariiers");
        Thread.sleep(2000);
            barrier.await();


        return "ok";
    }
}
