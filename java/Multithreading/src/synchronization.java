public class synchronization {

  static   int lock=0;
    public static void main(String[] args) throws InterruptedException {


counter c=new counter();

        Thread t1=new Thread(
                ()->{
//                    for(int i=0;i<1000;i++)
//                    {
                        try {

                            c.increment();

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
//                    }
                });

        Thread t2=new Thread(
                ()->{
//                    for(int i=0;i<1000;i++)
//                    {
                        try {

                            c.increment();
//                            lock=0;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
//                    }
                }

        );
        t1.start();
        t2.start();


        t1.join();
        t2.join();

        System.out.println(c.count);




    }
}
