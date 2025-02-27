

class Box<T>{

    private T value;


    void setValue(T value){
        this.value=value;
    }

    T getValue()
    {
        return this.value;
    }
}


public class Practical6{


    public static void main(String[] args) {



        //generic class for Integer
        Box<Integer> b=new Box<>();

        Integer i= b.getValue();

        System.out.println(i);
        b.setValue(5);
        System.out.println(b.getValue());


        //generic class for String
        Box<String>bs=new Box<>();

        System.out.println(bs.getValue());

        bs.setValue("jayesh");

        System.out.println(bs.getValue());





    }



}