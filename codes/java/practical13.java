import java.util.Arrays;
import java.util.List;

public  class practical13{


 static void printList(List<?> list)
 {

     for(var ele : list)
     {

         System.out.print(ele+" ");

     }
 }
    public static void main(String[] args) {
     List<Integer>lst= Arrays.asList(1,2,3,4,6,4,5);
     printList(lst);
 }
}