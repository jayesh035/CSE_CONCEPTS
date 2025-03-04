public class Test extends  Thread {

    private  int counter;
    @Override
    public void run() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.currentThread());


    }
}
