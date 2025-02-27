package Collection;


import java.util.PriorityQueue;

class parent{


    Number method()
    {
        return 5;
    }
}
public class priorityQueue  extends  parent{

    @Override
    Integer method() {
        return 6;
    }

    public static void main(String[] args) {

        PriorityQueue<Integer>pq=new PriorityQueue<>();

        pq.add(-5);
        pq.add(-6);
        pq.add(-7);

        System.out.println(pq.peek());


    }
}
