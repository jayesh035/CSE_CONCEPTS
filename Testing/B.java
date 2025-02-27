
// import Coading.*;

import java.io.FileNotFoundException;
import java.io.IOException;

class cs{


    cs()
    {

    }
   protected int i=0;
    int b=0;

     void c()
     {

     }
    
    }

public class B extends cs{

    B() throws Exception
    {

    }

    public void c() throws RuntimeException 
    {
        // throw new FileNotFoundException();
        System.out.println("B");
    }

    static void mehtod (int df,cs c)
    {
        // c.i=5;
        //c=null;
        c.i=5;


    }
    public static void main(String[] args) throws Exception {
        

        cs c=new B();

        c.c();
        //     byte b=127;

         


        // // code c=new code();
        //  cs cd=new cs();
        //  System.out.println(cd.i);
        //  mehtod(0,cd);
        //  System.out.println(cd.i);

        
        if(false)
        {
         System.out.println("true");   
        }

        
    
    }
}
