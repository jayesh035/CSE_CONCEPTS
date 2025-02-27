import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class collectors {


    public static void main(String[] args) {


        List<String> names= Arrays.asList("jayesh","kevin","sandip","mayur","jayesh");
        //collect in list
        List<String>oneName=names.stream().map(String::toUpperCase).collect(Collectors.toList());
        //collect in set

        Set<String> uniqueName=oneName.stream().collect(Collectors.toSet());


        System.out.println(uniqueName);


        //concat elements

        String concate=oneName.stream().collect(Collectors.joining(", "));
        System.out.println(concate);


        System.out.println(oneName.stream().
                collect(
                        Collectors.groupingBy(String::length,Collectors.joining(", "))));


        System.out.println(oneName.parallelStream().
                collect(Collectors.partitioningBy(s -> s.length()>5)));


        System.out.println(

                oneName.parallelStream().collect(Collectors.mapping(x -> x.toUpperCase(), Collectors.toList()))

        );



        //Example collection name by length
int len=6;
        System.out.println(

                names.parallelStream().filter(name -> name.length()>=len).collect(Collectors.toList())
        );


        //counting word occurance

        System.out.println(

                names.stream().collect(Collectors.groupingBy(name -> name,Collectors.counting()))

        );


        List<Integer>lst= Stream.iterate(0, x -> x+1).limit(10).collect(Collectors.toList());

        System.out.println(lst.stream().collect(Collectors.partitioningBy(x -> x%2 == 0)));




        //create map using collectors


        System.out.println(

                names.stream().collect(Collectors.toMap(k -> k, v -> 1, (x,y)-> x+y))
        );



    }
}
