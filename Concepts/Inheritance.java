
class A{

String name;
int a=10;
A()
{
    // // name="A";
    System.out.println(a);
    a=30;




}

void geta()
{
    System.out.println("A");
    
}
// A(int a,int b)
// {

// }

// public void printsomething()
// {
//     System.out.println(name);
// }


// class innerClass
// {


//     void method()
//     {

//     }
// }




}

class B extends A
{

    B()
    { 
        System.out.println(a);
    //   super(6);
    //     System.out.println("B");
    //     // name="B";
    // a=20;

    }
    void geta()
    {
    System.out.println("B");    
    }

// public void printsomething()
// {
//     super.printsomething();
// }

}

class C extends B
{

int a=20;

    C()
    {
        System.out.println(super.a);
        // super(a);
        // System.out.println("C");
        //  name="C";
        //  a = 40;
    }


}
class D extends C
{


    D(){
        System.out.println(super.a);


    }
    // void geta()
    // {
    //     System.out.println("D");
    // }

}



public class Inheritance {
    

  public static void main(String[] args) {
        
        // C a=new C();
        // C a1=new C();

        // System.out.println(a.name);
        // System.out.println(a.a);
        // a.printsomething();

        char arr[]=new char [6];
        // var i=10;
        // for(var af: arr)
        // {
        //     System.out.println(af);

        // }
// System.out.println(arr.toString());

// char arr1[]=new char[6];


// System.out.println(a1.equals(a));




// int i=0;
// switch (i) {
//     case 0:
        
//         break;

//     default:
//         break;
// }


A a=new D();
a.geta();

    }
}
