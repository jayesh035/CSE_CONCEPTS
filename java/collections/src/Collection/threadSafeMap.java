package Collection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class threadSafeMap {


    public static void main(String[] args) {




        // thread safe hash map

        ConcurrentHashMap<Integer,String> CHM=new ConcurrentHashMap<>();


        Thread t1=new Thread(()->{
           for(int i=0;i<1000;i++)
           {
               CHM.put(i,"thread1");
           }
        });

        Thread t2=new Thread(()->{
            for(int i=1000;i<2000;i++)
            {
                CHM.put(i,"thread2");
            }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        System.out.println(CHM.size());// 2000


        //thread safe treemap

        ConcurrentSkipListMap<Integer,String> CSLM=new ConcurrentSkipListMap<>();


        Thread t3=new Thread(()->{
            for(int i=0;i<1000;i++)
            {
                CSLM.put(i,"thread1");
            }
        });

        Thread t4=new Thread(()->{
            for(int i=1000;i<2000;i++)
            {
                CSLM.put(i,"thread2");
            }
        });

        t3.start();
        t4.start();

        try{
            t3.join();
            t4.join();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        System.out.println(CSLM);
    }
}
