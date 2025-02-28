


public class DeamonTread extends Thread {

    DeamonTread(String name){
        super(name);
    }

    @Override
    public void run() {
        while(true)
        {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {

        Thread t1=new DeamonTread("DeamonThread");
//        t1.setDaemon(true);
        Thread t2=new DeamonTread("normalThread");

        t1.start();


        System.out.println("Main Thread has ended");
    }
}
