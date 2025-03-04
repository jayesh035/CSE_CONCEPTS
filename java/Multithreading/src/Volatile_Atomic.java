class SharedResource{


    private    boolean  flag=false;

       void setBoolean()
   {

       System.out.println(Thread.currentThread().getName()+" has set the value ");
       flag=true;
//       notifyAll();
   }


   void getBoolean()
   {
       while (!flag)
       {
           try {
//               System.out.println(Thread.currentThread().getName()+"is waiting...");
//               wait();
//               Thread.sleep(2000);
           } catch (Exception e) {
               System.out.println(e.toString());
           }
       }
       System.out.println(Thread.currentThread().getName()+" is Runnig");
       System.out.println("Boolean value is true");
   }

}


public class Volatile_Atomic {


    static volatile   int counter=0;
     static  void increament(){
        counter++;
    }
    public static void main(String[] args) throws InterruptedException {

//        SharedResource sr=new SharedResource();
//        Thread t1=new Thread(sr::getBoolean);
//
//        Thread t2=new Thread(()->{
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
//            sr.setBoolean();
//        });
//
//        t1.start();
//        t2.start();
//        int counter;

        Thread t3=new Thread(()->{
            for(int i=0;i<1000;i++){
                increament();
            }
        });
        Thread t4=new Thread(()->{
            for(int i=0;i<1000;i++){
                increament();
            }
        });

        t3.start();
        t4.start();

        t3.join();
        t4.join();
        System.out.println(counter);
    }
}
