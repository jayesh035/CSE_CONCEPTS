import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public  class practical22 {




    public static void main(String[] args) {



        Predicate<Integer> greatedThan10= value -> value > 10 ;


        List<Integer>list= Arrays.asList(1,2,4,52,6,7,44,3,6,87,5);

        List<Integer> upDated=  list.stream().filter(num->  greatedThan10.test(num) ).collect(Collectors.toList());
        for(var v: upDated)
        {
            System.out.println(v);
        }

//      str -> str.



    }


}