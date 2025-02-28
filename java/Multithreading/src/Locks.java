public class Locks {


    public static void main(String[] args) {




        counter c=new counter();

        Runnable run=new Runnable() {
            @Override
            public void run() {

                try{
                    c.increment(56);
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }

            }
        };
        Thread t1=new Thread(run,"Thread1");
        Thread t2=new Thread(run,"Thread2");


        t1.start();
        t2.start();
    }
}
