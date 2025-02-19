

/*
 * 5. Question

Given a list of integers, use Java Streams to: - Filter out all even numbers.
 - Square the remaining numbers. 
 - Calculate the sum of the squared numbers.

Print the final result.
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Practical5 {
public static void main(String[] args) {
    

    // int arr[]={1,2,3,4,5,6,7,8,9,10};
    // int res=arr.stream

    List<Integer> list=Arrays.asList(1,2,3,4,5,6,7,8,9,10);

   int res=list.stream().filter(n -> n % 2 == 0 ).map(n -> n = n * n).reduce(0 , Integer::sum);

   System.out.println(res);

   int k;


  

  

    


}    
}
