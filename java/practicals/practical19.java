import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public  class practical19 {


    public static void main(String[] args) {


        List<String> str= Arrays.asList("jayes","Anasa","Asdfs","sadfsdf");


    List<String>upd_str=    str.stream().filter(
                (String s)->{ if(s.isEmpty()){return false;}  return s.charAt(0)=='A';}
        ).map( String::toUpperCase).collect(Collectors.toList());

    for(var ele:upd_str)
    {

        System.out.println(ele);
    }


    }
}