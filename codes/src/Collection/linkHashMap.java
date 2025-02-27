package Collection;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class linkHashMap {


    public static void main(String[] args) {


        LinkedHashMap<String ,Integer> lmap=new LinkedHashMap<>(20,0.35f,true);
        HashMap<String,Integer> map=new HashMap<>();

        lmap.put("orag",1);
        lmap.put("app",2);
        lmap.put("guava",3);

        lmap.get("orag");
        map.put("orag",1);
        map.put("app",2);
        map.put("guava",3);
        System.out.println(lmap);
        System.out.println(map);
    }
}
