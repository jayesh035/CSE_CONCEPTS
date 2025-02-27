import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStream {


    public static void main(String[] args) {

        List<Integer> str=Stream.iterate(0, x -> x+1).limit(10).collect(Collectors.toList());


        System.out.println( str.parallelStream().filter(x -> x%2 == 0).collect(Collectors.toList()));


//        str.stream().filter()


        //Streams can not be used after terminal operation
    }
}
