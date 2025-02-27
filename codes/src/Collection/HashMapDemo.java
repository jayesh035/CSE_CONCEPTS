package Collection;

import java.util.HashMap;
import java.util.Objects;

public class HashMapDemo {


    public static void main(String[] args) {


//        HashMap<Integer,String>map=new HashMap<>();
//
//
//        map.put(10,"jayesh");
//
//        map.put(12,"kevin");
//
//        System.out.println(map.get(10));
//
//
//        for(Integer it: map.keySet())
//        {
//            System.out.println(it+" "+map.get(it));
//        }


        HashMap<person,String>map=new HashMap<>();

        person p1=new person(1,"jayesh");
        person p2=new person(6,"jayesh");

        map.put(p1,"kevin");
        map.put(p2,"kevin");

        System.out.println(map.size());
    }
}


class person implements  Comparable{


    int id;
    String name;

    public person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        person person = (person) o;
        return id == person.id && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
