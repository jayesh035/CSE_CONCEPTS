import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collector;

public class ExecutersExample {


    static  void displayNfactorial(int n) throws InterruptedException {


        //create n threads by array
//        Thread[] threads=new Thread[n];
//        for(int i=1;i<=n;i++)
//        {
//            final int   FinalI=i;
//            threads[i-1]=new Thread(()->{
//                System.out.println(ExecutersExample.factorial(FinalI));
//            });
//            threads[i-1].start();
//        }


        //using executors

        ExecutorService service= Executors.newFixedThreadPool(3);
        for(int i=1;i<=n;i++)
        {
            final  int FinalI=i;
            service.submit(()->{
                System.out.println(ExecutersExample.factorial(FinalI));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            });
        }
        service.shutdown();

//        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    static  int factorial(int ele)
    {
        int res=1;

        for(int i=1;i<=ele;i++)
        {
            res*=i;
        }
        return  res;
    }
    public static void main(String[] args)  {


        try {
            displayNfactorial(9);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


//        ExecutorService service=Executors.newFixedThreadPool(2);

//        Future<Integer>submit=service.submit(()-> 1+2);
//
//        Integer sum= submit.get();
//        System.out.println("sum is :"+ sum);
//        service.shutdown();
//        Thread.sleep(1);
//        System.out.println(service.isTerminated());


        //invock all method


//        ExecutorService service1=Executors.newFixedThreadPool(1);
//
//        Callable<Integer> c1=()->{
//            Thread.sleep(999);
//            System.out.println("From callable 1");
//
//            return 1;
//        };
//        Callable<Integer> c2=()->{
//            Thread.sleep(1000);
//            System.out.println("From callable 2");
//
//            return 2;
//        };
//        Callable<Integer> c3=()->{
//            Thread.sleep(1000);
//            System.out.println("From callable 3");
//
//            return 3;
//        };
//
//        List<Callable<Integer>> list= Arrays.asList(c1,c2,c3);
//        List<Future<Integer>> result= null;
//        try {
////            result = service1.invokeAll(list,1, TimeUnit.SECONDS);
//            try {
//                Integer i=service1.invokeAny(list,1,TimeUnit.SECONDS);
//                System.out.println(i);
//            } catch (ExecutionException | TimeoutException ignored) {
//
//            }
//        } catch (InterruptedException ignored) {
//
//
//        }

//        System.out.println(result);

//        assert false;
//        for(Future<Integer>f: result)
//        {
//            try {
//                System.out.println(f.get());
//            } catch (CancellationException| InterruptedException | ExecutionException e) {
////service.shutdown();
//            }
//        }
//        service1.shutdown();









    }
}
