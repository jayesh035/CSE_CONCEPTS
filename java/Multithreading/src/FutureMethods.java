import java.util.concurrent.*;

public class FutureMethods {


    public static void main(String[] args) {


        ExecutorService service= Executors.newSingleThreadExecutor();

        Future<Integer> future= service.submit(() ->
        {
            Thread.sleep(4000);
            return 42;
        });

//        try {
//
//            Integer i=future.get(2, TimeUnit.SECONDS);
//            System.out.println(i);
//            System.out.println(future.isDone());
//        } catch (InterruptedException | ExecutionException e) {
//            System.out.println("Exception occured"+ e);
//        } catch (TimeoutException e) {
//            System.out.println("Exception occured"+ e);
//        }

        future.cancel(true);
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());

        service.shutdown();

    }
}
