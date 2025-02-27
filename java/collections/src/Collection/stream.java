package Collection;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class stream {

  static   int i=0;
  static   int addget(int i)
    {
        stream.i+=i;
    return stream.i;
    }
    public static void main(String[] args) {


        List<Integer>list= Arrays.asList(1,2,3,4,5);

//        AtomicInteger i=new AtomicInteger();
        List<Integer>lst=Stream.generate(()->stream.addget(1)).limit(10).collect(Collectors.toList());
        System.out.println(lst);

  List<Integer>str=  Stream.iterate(0,(x)->{ return x+1;}).limit(10).collect(Collectors.toList());
//        Paral


//        list.stream().filter(x -> x>3).s




    }
}
