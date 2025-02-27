import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;

class Singleton{

   static   Singleton obj;
   private Singleton(){}


    public   static  void initilize(){
       if(obj == null)
       {

           obj=new Singleton();


       }




    }

    void addLog(String logMessage){

       try {


           FileWriter f=new FileWriter("./log.txt",true);


           for(int i=0;i<logMessage.length();i++)
           {
           f.append(logMessage.charAt(i));
           }
           f.append('\n');
           f.close();




       }
       catch (Exception e)
       {
           System.out.println(e);
       }



    }

}

public  class practical10{


    public static void main(String[] args) {


        Singleton.initilize();



        //to add log in a file
        Singleton.obj.addLog("this is my first log");
        Singleton.obj.addLog("this is my second log");


    }

}