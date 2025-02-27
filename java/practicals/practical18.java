import java.util.Arrays;
import java.util.List;


public  class practical18 {


    public static void main(String[] args) {


        List<Integer> list= Arrays.asList(1,2,4,5,6,7,100,9);


    int sum= list.stream().filter(integer -> integer % 2 ==0).reduce(0,(a,b)->{return a+b; });

        System.out.println(sum);
    }
}