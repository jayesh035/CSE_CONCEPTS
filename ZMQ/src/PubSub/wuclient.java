package PubSub;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.StringTokenizer;

public class wuclient {

   static void startClient(String[] args)
    {

        try (ZContext context = new ZContext()) {
            //  Socket to talk to server
            System.out.println("Collecting updates from weather server");
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5556");

            //  Subscribe to zipcode, default is NYC, 10001
            String filter = (args.length > 0) ? args[0] : "10001 ";
            subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));

            //  Process 100 updates
            int update_nbr;
            long total_temp = 0;
            for (update_nbr = 0; update_nbr < 2; update_nbr++) {
                //  Use trim to remove the tailing '0' character
                String string = subscriber.recvStr(0).trim();
                String title=subscriber.recvStr(0).trim();
                String msg=subscriber.recvStr(0).trim();
                System.out.println(string);
                System.out.println(title);
                System.out.println(msg);
                System.out.println();

//                StringTokenizer sscanf = new StringTokenizer(string, " ");
//                int zipcode = Integer.valueOf(sscanf.nextToken());
//                int temperature = Integer.valueOf(sscanf.nextToken());
//                int relhumidity = Integer.valueOf(sscanf.nextToken());
//
//                total_temp += temperature;
            }

            System.out.println(
                    String.format(
                            "Average temperature for zipcode '%s' was %d.",
                            filter,
                            (int)(total_temp / update_nbr)
                    )
            );
        }

    }
    public static void main(String[] args)
    {


       Thread t1=new Thread(()->{
           startClient(args);
       });

       Thread t2=new Thread(()->{
           startClient(args);
       });
        Thread t3=new Thread(()->{
            startClient(args);
        });
        Thread t4=new Thread(()->{
            startClient(args);
        });
        Thread t5=new Thread(()->{
            startClient(args);
        });


       t1.start();
//       t2.start();
//       t3.start();
//       t4.start();
//       t5.start();

    }
}
