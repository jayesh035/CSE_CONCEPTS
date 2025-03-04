import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CompitableFuture {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<String>CF=CompletableFuture.supplyAsync(()->{

            try{


                Thread.sleep(2000);
                System.out.println("Hello");
            } catch (Exception e) {
                System.out.println(e);
            }
            return "ok";
        });

//        FairLock l1=new FairLock();
//        Fa
      Lock l1=new ReentrantLock();
//      l1.tryLock()
//        Lock l1=new


    Thread.sleep(2001);
      String s=  CF.getNow("Noo");
        System.out.println(s);
        System.out.println("Main");
    }
}
