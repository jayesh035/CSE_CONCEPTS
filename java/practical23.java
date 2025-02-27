interface  Calculator
{

    double values(double a, double b);

}


public class practical23
{


    public static void main(String[] args) {



        Calculator add=(a,b)-> a+b ;
        Calculator mul=(a,b)-> a*b;
        Calculator div=(a,b)-> {
           try{
              return a/b; } catch (Exception e) {
               throw new RuntimeException(e);
           }};


        Calculator sub=(a,b)-> a-b;


        System.out.println(mul.values(5,6));
    }
}