
class MathOperation<T extends  Number>{



    static  <T extends  Number>  Double addition(T  number1 , T number2){


//        return (T)(number1+number2);
   return number1.doubleValue()+number2.doubleValue() ;
    }


    static  <T extends  Number>  Double substraction(T  number1 , T number2){

        return number1.doubleValue()-number2.doubleValue() ;

    }

    static  <T extends  Number>  Double multiplication(T  number1 , T number2){

        return number1.doubleValue()*number2.doubleValue() ;

    }



    static  <T extends  Number>  Double Division(T  number1 , T number2){

        try{
            return number1.doubleValue()/number2.doubleValue() ;
        } catch (Exception e) {
            System.out.println(e);
//            return -;
        }
        return  null;



    }

}



public  class practical12 {

    public static void main(String[] args) {


        System.out.println(MathOperation.Division(1,5));


    }
}