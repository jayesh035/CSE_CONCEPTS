public class DeadLock {


    boolean setLock=true;
    boolean getLock=true;
    void set() throws InterruptedException {

        while(setLock!=true)
        {
            System.out.println(Thread.currentThread().getName()+" is waiting to set");
        }
        setLock=false;
//        Thread.yield();
        get();
        setLock=true;
    }

    void get() throws InterruptedException {

        while (getLock!=true)
        {
            System.out.println(Thread.currentThread().getName()+" is waiting to get");
        }
//        Thread.yield();
        Thread.sleep(1);
        getLock=false;
        set();
        getLock=true;

    }
    public static void main(String[] args) {

        DeadLock d1=new DeadLock();
        Thread t1=new Thread(() -> {
            try {
                d1.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });




        Thread t2=new Thread(() -> {
            try {
                d1.set();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        t1.start();
        t2.start();




    }
}
