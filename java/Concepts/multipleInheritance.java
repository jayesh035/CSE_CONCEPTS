
interface A {
    void methodA(); // Abstract method
    default void methodC()
    {
        System.out.println("this method is from A");
    }

    default void methodC(int a)
    {

    }
}

interface B {
    void methodB(); // Abstract method

    default void methodC()
    {
        System.out.println("this method is from A");
    }
    default void methodC(int a)
    {

    }

}

// Class implementing both interfaces
class C implements A, B {
    public void methodA() {
        System.out.println("Method A from Interface A");
    }

    public void methodB() {

        // A.super.methodC(7);
        System.out.println("Method B from Interface B");
    }
    @Override
    public void methodC() {
        // TODO Auto-generated method stub
        B.super.methodC();
    }
    @Override
    public void methodC(int a) {
        // TODO Auto-generated method stub
        A.super.methodC(a);
    }
}

 class Main {
    public static void main(String[] args) {
        C obj = new C();
        obj.methodA();  // Output: Method A from Interface A
        obj.methodB();  // Output: Method B from Interface B
    }
}


public class multipleInheritance {
    
}
