import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class primitiveStreams {


    public static void main(String[] args) {


        int [] num={1,2,3,4,6};

        IntStream stream = Arrays.stream(num);


        DoubleStream doubleStream = stream.asDoubleStream();


        System.out.println(doubleStream.boxed().collect(Collectors.toSet()));

    }
}
