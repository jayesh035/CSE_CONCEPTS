package Collection;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class queue {


    public static void main(String[] args) {


        Queue<Integer> q=new LinkedList<>();


        q.add(6);

        q.remove();// can throw an exception
        q.poll();


        q.element();//can throw an exception
        q.peek();


        Queue<Integer> ABQ=new ArrayBlockingQueue<>(2);

        ABQ.add(3);
        ABQ.offer(4);

//        System.out.println(ABQ.add(5));// can throw an exception if full
        System.out.println(ABQ.offer(6));


    }
}
