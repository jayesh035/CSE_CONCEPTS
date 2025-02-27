package Collection;

import java.util.ArrayList;
import java.util.Vector;

class vector{


    public static void main(String[] args) {


        Vector<Integer>v1=new Vector<>();//vector declaration

        ArrayList<Integer>a1=new ArrayList<>();
//        Vector<Integer>v2=new Vector<>(10);//vector declaration with capicity
//
//        Vector<Integer>v3=new Vector<>(10,5);//vector declaration with capicity and increment
//
////        v1.get(0);
//
//


 Thread t1=new Thread(()->{

     for (int i=0;i<1000;i++){

         a1.add(i);
//         v1.add(i);
     }
 });
 Thread t2=new Thread(()->{
     for(int i=0;i<1000;i++)
     {
         a1.add(i);
//         v1.add(i);
     }
 });

 t1.start();
 t2.start();
try {
    t1.join();
    t2.join();


} catch (Exception e) {
    System.out.println(e);
}
//        System.out.println("Capicity:"+ v1.size());//2000
        System.out.println("Capicity:"+ a1.size());//some random number beacuse arraylist is not thread safe


    }


}