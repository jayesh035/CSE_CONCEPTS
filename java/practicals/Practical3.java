import java.util.InputMismatchException;
import java.util.Scanner;

public class Practical3 {

    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);


        try {
        
            
            System.out.print("Enter first number:");
               long firstNumber=sc.nextInt();
            System.out.print("Enter second number:");
                long secondNumber=sc.nextInt();
                System.out.println((firstNumber/secondNumber));
        } 
        catch(InputMismatchException e)
        {
            System.out.println(e);
        }
        catch(ArithmeticException e)
        {
            System.out.println(e);
        }
        

       
    }
}
