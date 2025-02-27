import java.util.Scanner;

class Vehical
{
    String make;
    String model;
    int year;
    void displayDetails()
    {
        
    }
}

class Cars extends Vehical
{
    int numDoors;

    Cars(String make,String model,int year,int numDoors)
    {


        this.make=new String(make);
        this.model=new String(model);
        this.year=year;
        this.numDoors=numDoors;

    }
    @Override
    void displayDetails()
    {
        System.out.println();
        System.out.println("make:"+make);
        System.out.println("model:"+model);
        System.out.println("year:"+ year);
        System.out.println("numDoors"+numDoors);
        System.out.println();
        System.out.println();
    }
}
class Motorcycle extends Vehical
{
    boolean hasSideCar;


    Motorcycle(String make,String model,int year,boolean hasSideCar)
    {


        this.make=new String(make);
        this.model=new String(model);
        this.year=year;
        this.hasSideCar=hasSideCar;

    }

    @Override
    void displayDetails() {
        
        System.out.println();
        System.out.println("make:"+make);
        System.out.println("model:"+model);
        System.out.println("year:"+ year);
        System.out.println("hasSideCar:"+hasSideCar);
        System.out.println();
        System.out.println();
        
    }

}


public class Practical2 {
    public static void main(String[] args) {
    


        String make="BMW";
        String model="X7";
        int year=2025;
        int numDoors=4;



        Cars car=new Cars(make, model, year, numDoors);

        //get details of car
        car.displayDetails();




         make="Bajaj";
         model="ns200";
         year=2025;
         boolean hasSideCar=false;

        Motorcycle bike = new Motorcycle(make, model, year, hasSideCar);

        //get details of car
        bike.displayDetails();

        // int [] arr=new int[0];

        //jagged arrays

        // int [][]arr=new int[5][];
        // arr[0]=new int[0];
        // System.out.println(arr[0][0]);
        // Scanner

        // final int [] arr=new int[4];
        // arr[0]=90;
        // arr[0]=10;
        // double d=239587349543987598436753468756435438d;
        // int k=876453456;
        // double d=k;
        // System.out.println(k);


        // char [] arr={'1','2','3','4'};
        // byte [] bye={99,100,101};
        // String s=new String(bye);
        // System.out.println(s);
        int a=0;
        // if(a==3)
        // {

        // }
        

    }    
}