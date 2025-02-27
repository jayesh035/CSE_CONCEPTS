import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public  class practical20 {


    public static void main(String[] args) {


        List<String>empName= Arrays.asList("jayesh","jay","kartikey","purvik","purvik");


        AtomicInteger counter=new AtomicInteger(0);

//        Map<Integer,String>=
// for(var name:empName)
// {
//     System.out.println(map.get(name));
// }

      Map<Integer,String> map= empName.stream().collect(Collectors.toMap(name -> counter.addAndGet(1), name-> name));


      map.forEach((key,value)->{
          System.out.println(key+" "+value);
      });




    }
}