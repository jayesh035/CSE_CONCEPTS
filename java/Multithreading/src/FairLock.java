import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLock {


  static final   Lock lock=new ReentrantLock(true);


 static void sharedMethod()
  {

      lock.lock();
      try{

          System.out.println(Thread.currentThread().getName()+ "is entered");
      }catch (Exception e)
      {
          System.out.println(e);
      }
      finally {
          lock.unlock();
      }
  }
  public static void main(String[] args) throws InterruptedException {


        Thread t1=new Thread(FairLock::sharedMethod,"Thread1");
      Thread t2=new Thread(FairLock::sharedMethod,"Thread2");
      Thread t3=new Thread(FairLock::sharedMethod,"Thread3");


      t1.start();
//      Thread.sleep(2);
      t2.start();
//      Thread.sleep(2);
      t3.start();

//      ConcurrentHaSet
//SynchronousS



    }
}
