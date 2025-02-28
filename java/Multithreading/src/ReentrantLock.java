import java.util.concurrent.locks.*;

 class reentrantLock {



//  private   final  static  Lock lock=new java.util.concurrent.locks.ReentrantLock();
  static final Lock l1=new ReentrantLock();
   static void innerMethod() throws InterruptedException {
//        l1.lock();
        l1.lockInterruptibly();
        try{
            while (true){
                if(Thread.currentThread().isInterrupted()){break;}
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName()+ " innerMethod");

        }
        catch (Exception e){

            Thread.currentThread().interrupt();


        }finally {
            l1.unlock();
        }

    }


   static void outerMethod() throws InterruptedException {


//        l1.lock();
       l1.lockInterruptibly();
        try {
            System.out.println(Thread.currentThread().getName()+" outer method");
            innerMethod();

        }catch (Exception e)
        {
            Thread.currentThread().interrupt();
        }
        finally {
            l1.unlock();
        }

    }

     public static void main(String[] args) throws InterruptedException {

//       outerMethod();
         Thread t1=new Thread(()->{
             try {
                 outerMethod();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         },"t1");
t1.setDaemon(true);
         Thread t2=new Thread(()->{
             try {
                 outerMethod();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         },"t2");
         t1.start();
         t2.start();
         t2.interrupt();
     }
}
