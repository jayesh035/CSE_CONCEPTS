import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchduledExecuterService {


    public static void main(String[] args) {

     ScheduledExecutorService schduler  = Executors.newScheduledThreadPool(1);


//    schduler.schedule(()-> System.out.println("Task is executed after 1 sec"),
//            1,
//            TimeUnit.SECONDS);

//        schduler.scheduleAtFixedRate(()->{
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println("Task is executed after every 2 sec");
//
//                } ,
//                2,
//                2,
//                TimeUnit.SECONDS);

        schduler.scheduleWithFixedDelay(()->{
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Task is executed after every 2 sec");

                } ,2,
                2,
                TimeUnit.SECONDS);

        //                    System.out.println();
        schduler.schedule(schduler::shutdown,
                8,
                TimeUnit.SECONDS);
//    schduler.shutdown();
    }


}

//Atomic vs volatile
//types of pool
//count down latch

