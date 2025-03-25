import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.Random;

public class PUSH_PULL_server {

    public static void main(String[] args) {

        try(ZContext context=new ZContext()) {


            ZMQ.Socket sender=context.createSocket(SocketType.PUSH);

            sender.bind("tcp://*:5557");

            ZMQ.Socket sink=context.createSocket(SocketType.PUSH);

            sink.connect("tcp://localhost:5558");



            System.out.println("Press Enter when the workers are ready: ");
            System.in.read();
            System.out.println("Sending tasks to workers\n");


            sink.send("0",0);

            Random srandom = new Random(System.currentTimeMillis());

            //  Send 100 tasks
            int task_nbr;
            int total_msec = 0; //  Total expected cost in msecs
            for (task_nbr = 0; task_nbr < 10; task_nbr++) {
                int workload;
                //  Random workload from 1 to 100msecs
                workload = srandom.nextInt(100) + 1;
                total_msec += workload;
                System.out.print(workload + ".");
                String string = String.format("%d", workload);
                sender.send(string, 0);
            }
            System.out.println("Total expected cost: " + total_msec + " msec");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
