import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class practical24 {


    public static void main(String[] args) {



        Class<?> classz = Library.class;

        //get methods of practicals class
        Method[] methods  = classz.getDeclaredMethods();



        for(var method: methods)
        {


            System.out.println("Method Name:"+method.getName());

            System.out.println("return type:"+method.getReturnType());
            System.out.println("parameter type:"+method.getParameterTypes());

        }


//        List<Integer> list=new ArrayList<>(10);







    }
}