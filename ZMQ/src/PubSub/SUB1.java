package PubSub;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Arrays;

public class SUB1 {

    public static void main(String[] args) {


        try(ZContext context=new ZContext())
        {

            ZMQ.Socket socket=context.createSocket(SocketType.SUB);


            socket.connect("tcp://localhost:5056");


            socket.subscribe("");
            while (true) {  // Keep listening for messages
                var response = socket.recv(0);
                System.out.println("Received: " + new String(response));
            }


        }
    }
}
