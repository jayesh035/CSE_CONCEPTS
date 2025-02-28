


public class performanceCheck {


    static  void Add(int[][] arr) throws InterruptedException {



        for(int i=0;i< arr.length;i++)
        {
//            Thread.sleep(10);
            for(int j=0;j<arr[0].length;j++)
            {

                arr[i][j]++;
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {

        int[][] arr=new int[1000000][1000];

        long currentTime=System.currentTimeMillis();

        Add(arr);

        long afterTime=System.currentTimeMillis() - currentTime;

        System.out.println(afterTime);

//        int[][] arr1=new int[1000000][1000];
        Thread t1=new Thread(()->{
           for(int i=0;i<1000;i++)
           {
               for(int j=0;j<1000;j++)
               {
                   arr[i][j]++;
               }
           }
        });
        Thread t2=new Thread(()->{
            for(int i=1000;i<10000;i++)
            {
                for(int j=0;j<1000;j++)
                {
                    arr[i][j]++;
                }
            }
        });
        Thread t3=new Thread(()->{
            for(int i=10000;i<100000;i++)
            {
                for(int j=0;j<1000;j++)
                {
                    arr[i][j]++;
                }
            }
        });
        Thread t4=new Thread(()->{
            for(int i=100000;i<1000000;i++)
            {
                for(int j=0;j<1000;j++)
                {
                    arr[i][j]++;
                }
            }
        });


        currentTime=System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        while(!t1.getState().toString().equals("TERMINATED") ||
                !t2.getState().toString().equals("TERMINATED")||
                !t3.getState().toString().equals("TERMINATED")||
                !t4.getState().toString().equals("TERMINATED"))
        {

        }
        afterTime=System.currentTimeMillis();
        System.out.println(afterTime-currentTime);





    }
}

class Image{

//    float green;

}