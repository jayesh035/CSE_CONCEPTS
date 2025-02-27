interface  StringProcessor{

    String Process(String str);
}


public  class practical21 {


    public static void main(String[] args) {



        StringProcessor reverse=(str)->{
            StringBuilder st=new StringBuilder();

            for(int i=str.length()-1;i>=0;i--)
            {
            st.append(str.charAt(i))    ;
            }

            return  st.toString();
        };




        String name="jayesh";


        System.out.println("Actual String :"+ name);
        String reversedString=reverse.Process(name);
        System.out.println("Reversed String:"+reversedString);
    }

}