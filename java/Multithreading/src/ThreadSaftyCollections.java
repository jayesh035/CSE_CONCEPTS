import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSaftyCollections {

   static Lock lc=new ReentrantLock();
    static
    {
        System.out.println(Thread.currentThread().getName());
        lc.lock();
    }


    void  method1()
    {

    }

   static void  method2()
    {
        System.out.println("lock");
    }

    public static void main(String[] args) throws InterruptedException {


        System.out.println("main");

        Thread t1 = new Thread(()->{
         method2();
        });
        t1.start();
//        ConcurrentHashMap<Integer,Integer> hs=new ConcurrentHashMap<>();
//
//        AtomicInteger counter=new AtomicInteger();
//        Thread t1=new Thread(()->{
//
//            for(int i=0;i<1000;i++)
//            {
//                //            synchronized(ThreadSaftyCollections.class)
////            {
//                hs.put(counter.addAndGet(1),0);
//
////            }
//            }
//        });
//        Thread t2=new Thread(()->{
//
//            for(int i=0;i<1000;i++)
//            {
////                synchronized(ThreadSaftyCollections.class)
////                {
//                    hs.put(counter.addAndGet(1),0);
//
////                }
//            }
//        });
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//
//
//        System.out.println(hs.size());









    }
}
