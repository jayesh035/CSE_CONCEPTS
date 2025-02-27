package Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnArraylist {


    public static void main(String[] args) {



        List<String> array=new ArrayList<>();

        List<String>copyArray=new CopyOnWriteArrayList<>();


//
//
//        array.add("ja");
//        array.add("asd");
//        array.add("sdfsdihg");
//        copyArray.add("sjfd");
//        copyArray.add("asd");
//        copyArray.add("sdfsdihg");
//
//
//        for(String st:copyArray)
//        {
//
//            if(st.equals("asd"))
//            {
//
//                copyArray.add("sjdf");
//
//            }
//
//        }
//        System.out.println(copyArray);
//
//
//
        Thread  t1=new Thread(()->{
           for(int i=0;i<1000;i++)
           {
               copyArray.add("i");
           }
        });

        Thread t2=new Thread(()->{
           for(int i=0;i<1000;i++)
           {
               copyArray.add("i");
           }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();

        } catch (Exception e) {
            System.out.println(e);
        }


        System.out.println(copyArray.size());



    }
}
