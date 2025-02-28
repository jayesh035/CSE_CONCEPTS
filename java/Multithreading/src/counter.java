import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class counter {


//   static int lock;
     int count;
final static Lock lock=new ReentrantLock();

   synchronized   void increment() throws InterruptedException {
//        Thread.sleep(10);
//            while(lock==1){}
//            Thread.sleep(1);
//            lock=1;
         for (int i=0;i<1000;i++)
         {
             Thread.sleep(2);
             count++;
         }

//            lock=0;


    }



    void increment(int a) throws InterruptedException {


        System.out.println(Thread.currentThread().getName()+" wants to increment"+ a);
        if(lock.tryLock(1000, TimeUnit.MILLISECONDS))
        {

            System.out.println(Thread.currentThread().getName()+" incrementing "+ a);

            count++;

            try {

                Thread.sleep(4000);
                System.out.println(Thread.currentThread().getName()+" incremented "+ a);
            } catch (Exception e) {

            }finally {
                lock.unlock();
            }
        }
        else
        {
            System.out.println(Thread.currentThread().getName()+" could not aquire lock");
        }


    }


    void  display(){

        System.out.println(count);
    }
}
