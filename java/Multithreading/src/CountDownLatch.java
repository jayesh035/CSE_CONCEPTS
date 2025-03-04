import java.util.concurrent.*;

public class CountDownLatch {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int numberOfServices=3;
        ExecutorService service=Executors.newFixedThreadPool(numberOfServices);
        java.util.concurrent.CountDownLatch latch=
                new java.util.concurrent.CountDownLatch(numberOfServices);
      Future<String>res1= service.submit(new DependentService(latch));
      Future<String>res2=  service.submit(new DependentService(latch));
      Future<String>res3=  service.submit(new DependentService(latch));

//
//        for(int i=0;i<numberOfServices;i++){
//            new Thread(new ManualThread(latch)).start();
//
//        }
   latch.await(5,TimeUnit.SECONDS);


        System.out.println("All services are completed");
//        service.shutdownNow();
service.shutdown();
    }



}


class DependentService implements Callable<String>{

    private final java.util.concurrent.CountDownLatch latch;

    DependentService(java.util.concurrent.CountDownLatch latch){
        this.latch=latch;
    }

    @Override
    public String call() throws Exception {

try {
    Thread.sleep(6000);
    System.out.println(Thread.currentThread().getName()+" is running");

}finally {
    latch.countDown();
}

        return "ok";
    }
}


class ManualThread implements Runnable{

    private  final java.util.concurrent.CountDownLatch latch;
    ManualThread(java.util.concurrent.CountDownLatch latch)
    {
        this.latch=latch;
    }
    @Override
    public void run() {

        try {

            try {
                Thread.sleep(6000);
                System.out.println(Thread.currentThread().getName()+" is running");
            } catch (InterruptedException ignored) {

            }
        }finally {
            latch.countDown();
        }

    }
}