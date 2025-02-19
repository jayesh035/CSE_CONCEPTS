class GenericClass<T>{


    static <T> void  swap(T[] array,Integer i,int j)
    {
        try {
            T temp=array[i];
            array[i]=array[j];
            array[j]=temp;
        }
        catch (RuntimeException e){

            System.out.println(e.getMessage());
        }

    }
}

public  class practical11  {




    public static void main(String[] args) {



        Integer[] arr={1,2,3,4,5};


        GenericClass.swap(arr,3,8);
        for(var a:arr)
        {
            System.out.print(a+" ");
        }

    }


}