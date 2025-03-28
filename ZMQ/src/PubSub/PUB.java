package PubSub;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class PUB {


    public static void main(String[] args) {


        try(ZContext context=new ZContext()) {

            ZMQ.Socket socket = context.createSocket(SocketType.PUB);


            socket.bind("tcp://*:5056");


            Thread.sleep(10);
            int i = 0;
            while (i<3) {
                socket.send("hello" + i);
//               Thread.sleep(2000);
                i++;
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
