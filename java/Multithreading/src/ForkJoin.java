import java.util.concurrent.*;

public class ForkJoin {

   static void findNFactorial(int n) throws ExecutionException, InterruptedException {

//        ExecutorService service= Executors.newWorkStealingPool(5);

//        ForkJoinPool service2=new ForkJoinPool(5);

       ExecutorService service=Executors.newFixedThreadPool(5);
        long time=System.currentTimeMillis();
        for(int i=1;i<=n;i++)
        {
           final int k=i;
        Future<?>res =   service.submit(()->{return findFactorial(k);});
            System.out.println(res.get());
//            Thread.sleep(1000);
        }

       System.out.println();

        System.out.println(System.currentTimeMillis()-time);
        service.shutdown();

    }

    static Integer findFactorial(int n){

        int res=1;
        for(int i=1;i<=n;i++){
            res*=i;
        }

        return res;
    }




    public static void main(String[] args) throws ExecutionException, InterruptedException {



       findNFactorial(10);



    }
}
