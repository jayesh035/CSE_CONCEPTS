

 class A {

//    static int i=0;
//     static void md()
//     {
//         System.out.println(i);
//     }

//  A()
// {

// }
    void method() throws ClassNotFoundException
    {

        // throw new ArithmeticException();


        // try {
        //     throw new ClassNotFoundException();
        // } catch ( Exception e) {
        //     // TODO: handle exception

        //     System.out.println(e);
        // }
        try {
            
            throw new ArithmeticException();
            
            // int k=9/0;
            // System.out.println("main");
          
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally{
            System.out.println("finally");
            // return ;
            // return ;

        }
       
        

    }
}

public class ExceptionCode {
    
// int i=0;
    public static void main(String[] args) throws ClassNotFoundException {
        
        A a=new A();
        a.method();


        // Object val=0;

        // System.out.println(i);
        // System.out.println((String)val);
    }
}
