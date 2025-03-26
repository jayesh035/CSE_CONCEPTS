import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Arrays;

public class REQRES_server {


    public static void main(String[] args) {


        try(ZContext context =new ZContext()){


            ZMQ.Socket socket=context.createSocket(SocketType.REP);


            socket.bind("tcp://*:5056");

            System.out.println("sending to client");
            String message="from server";
            socket.send(message.getBytes(),0);

            var response=socket.recv(0);

            System.out.println(Arrays.toString(response));



        }
    }
}
