class RunnableThread implements  Runnable {

    @Override
    public void run() {


        System.out.println(Thread.currentThread().getName());
    }



}

class ThreadExtended extends  Thread{

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName());

    }
}