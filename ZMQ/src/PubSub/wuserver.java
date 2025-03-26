package PubSub;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public class wuserver {


    public static void main(String[] args) {


        try(ZContext context = new ZContext())
        {
            ZMQ.Socket publisher=context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5556");

            publisher.bind("ipc://weather");

            Random srandom=new Random(System.currentTimeMillis());
            while (!Thread.currentThread().isInterrupted())
            {

                int zipcode, temperature, relhumidity;
                zipcode = 10000 + srandom.nextInt(10000);
                temperature = srandom.nextInt(215) - 80 + 1;
                relhumidity = srandom.nextInt(50) + 10 + 1;

                //  Send message to all subscribers
                String update = String.format(
                        "%05d %d %d", zipcode, temperature, relhumidity
                );


                publisher.send(update, 0);
                publisher.sendMore("10001 ");

//                publisher.recv(0);

                publisher.send("hiiii",0);
            }

        }
    }


}
