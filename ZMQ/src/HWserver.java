import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;
public class HWserver {
    public static void main(String[] args) {



        String version=ZMQ.getVersionString();
        int fullversion=ZMQ.getFullVersion();

        System.out.println(version);
        System.out.println(fullversion);

        try (ZContext context = new ZContext())
        {
            //  Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");

            while (!Thread.currentThread().isInterrupted()) {
                byte[] reply = socket.recv(0);
                System.out.println(
                        "Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]"
                );
                String response = "world";
                try {
                    Thread.sleep(1000); //  Do some 'work'
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                socket.send(response.getBytes(ZMQ.CHARSET), 0);

            }
        }
    }
}