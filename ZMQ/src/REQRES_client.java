import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Arrays;

public class REQRES_client {


    public static void main(String[] args) {



        try(ZContext context=new ZContext())
        {
            ZMQ.Socket socket=context.createSocket(SocketType.REQ);

            socket.connect("tcp://localhost:5056");

            var response=socket.recv(0);

            System.out.println(Arrays.toString(response));
        }
    }
}
