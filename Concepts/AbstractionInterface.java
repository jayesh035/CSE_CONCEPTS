 interface InnerAbstractionInterface1 {



    // int k=10;
   
    abstract void name();

   default int getArray()
    {

        System.out.println("this is default method");
        return 0;
    }
    
}

interface InnerAbstractionInterface2 {



    // int k=10;
   
    abstract void name();

   default int getArray()
    {

        System.out.println("this is default method");
        return 0;
    }
    
}

class A implements InnerAbstractionInterface1, InnerAbstractionInterface2
{

    @Override
    public void name() {
        // TODO Auto-generated method stub
       System.out.println(); 
        System.out.println("My name is jayesh");
    }

    @Override
    public int getArray() {
        
        return 0;
    }
}

public class AbstractionInterface {


    public static void main(String[] args) {
    
        A i=new A();

        // i.getArray();
        i.name();
    }



    
}
