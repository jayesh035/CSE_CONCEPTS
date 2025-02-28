import static java.lang.Thread.sleep;

public class Main  {

//    @Override
//    public void run() {
//        for(int i=0;i<10;i++)
//        {
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(i);
//
//        }
//    }


    private  int i1=0;
    private   int Volatile=0;
    private  int i2=0;
    void read() throws InterruptedException {


    Thread.yield();

//        Thread.sleep(1);
//        System.out.println(Volatile);
        Volatile=1;

        i2=1;
        i1=1;



    }

    void write() throws InterruptedException {
//        Thread.sleep(1);


        System.out.println(writer.getState());
        while(Volatile != 1)
        {

            System.out.println("infinite");
        }


        System.out.println(i1+" "+i2);


//        System.out.println(Volatile);
//        Volatile+=10;
    }
   static Thread writer;
    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hello, World!");




//        Thread t1=new Main();
//        t1.start();



//        System.out.println(Thread.currentThread().getState());
//         t1.join();

//        for(int i=0;i<10;i++)
//        {
//            Thread.sleep(2000);
//            System.out.println(t1.getState());
//        }

        Main m=new Main();
//        Main m1=new Main();

        writer=new Thread(()->{

//            System.out.println("writer");
            try {
                m.read();
            } catch (InterruptedException e) {

                 throw new RuntimeException(e);

            }

        });


        Thread reader=new Thread(
                ()->{
//                    System.out.println("reader");
                    try {
                        m.write();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );


                writer.start();
                reader.start();


        System.out.println(m.Volatile);








    }
}