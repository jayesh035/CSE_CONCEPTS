import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

  class ReadWriteLocks {


    final  static  ReadWriteLock lock=new ReentrantReadWriteLock();
    final  static Lock readLock=lock.readLock();
    final  static  Lock writeLock=lock.writeLock();


    static int counter=0;


    static  void addCount()
    {
        writeLock.lock();
        try {
            for (int i=0;i<5;i++)
            {
                counter++;
                System.out.println(Thread.currentThread().getName()+" is currently incremented: "+ counter);
                Thread.sleep(50);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            writeLock.unlock();
        }
    }


   static void  getCount()
    {

        readLock.lock();
        try{

            System.out.println(Thread.currentThread().getName() + " is reading:"+ counter);
//            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {



        Thread reader1=new Thread(()->{
            for(int i=0;i<5;i++)
            {
                getCount();
            }
        },"reader1");
        Thread writer =new Thread(()->{

                addCount();

        },"writer");
        Thread reader2=new Thread(()->{

            for(int i=0;i<5;i++)
            {
                getCount();
            }
        },"reader2");

        reader1.start();
        reader2.start();

        writer.start();



    }
}
