//  Hello World client in Java
//  Connects REQ socket to tcp://localhost:5555
//  Sends "Hello" to server, expects "World" back

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class HWclient
{
    public static void main(String[] args)
    {





                try (ZContext context = new ZContext())
                {
                    System.out.println("Connecting to hello world server");

                    //  Socket to talk to server
                    ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                    socket.connect("tcp://localhost:5555");


//                    for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
                        String request = "Hello"+System.currentTimeMillis();
                        System.out.println("Sending Hello by client:" + Thread.currentThread().getName());
                        long time=System.currentTimeMillis();
                        socket.send(request.getBytes(ZMQ.CHARSET), 0);

                        byte[] reply = socket.recv(0);

                        System.out.println(
                                "Received " + new String(reply, ZMQ.CHARSET) + " " +
                                       "client:"+ Thread.currentThread().getName()+" time:"+(System.currentTimeMillis()-time)
                        );

                    }


    }





}
