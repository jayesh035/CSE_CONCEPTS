import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonBlockingClient  {

    SocketChannel client;

    NonBlockingClient()
    {
        try
        {
             this.client=SocketChannel.
                    open(new InetSocketAddress("localhost",8080));


            client.configureBlocking(false);
            System.out.println("Connected to server!");
        } catch (IOException e) {
            System.out.println("Opps! server is not found");
        }
    }


    void sendMessage(String message)
    {
        ByteBuffer buffer=ByteBuffer.allocate(1024);

//        Scanner sc=new Scanner(System.in);
//        while(true)
//        {
//
//
//            System.out.print("Enter message (or 'exit' to quit): ");
//            String message=sc.nextLine();

        try {

            if(!client.isConnected())
            {
                System.out.println("Opps! Connection is closed ");
                return;
            }
            if(message.equalsIgnoreCase("exit"))
            {
                System.out.println("Client closing connection");

                client.close();
                return;
            }

            buffer.clear();
            buffer.put(message.getBytes());
            buffer.flip();
            client.write(buffer);
            buffer.clear();

            int byteRead=client.read(buffer);

            if(byteRead>0)
            {
                buffer.flip();
                String serverResponse=new String(buffer.array(),0,byteRead);
                System.out.println();
                System.out.println("Server response:"+ serverResponse);
            }
        } catch (Exception e) {
            System.out.println("Exception occured :"+e);
        }





//        }
    }
    public static void main(String[] args) throws InterruptedException {


//        List<NonBlockingClient>clients=new ArrayList<>();

        for(int i=0;i<4;i++)
        {
//            clients.add(new NonBlockingClient());

            new NonBlockingClient().sendMessage("message from client "+(i+1));
//            Thread.sleep(1000);

        }


//        List<String>messages=new ArrayList<>();
//        for(int i=0;i<4;i++)
//        {
//            messages.add("message from client "+(i+1));
//
//        }





    }
}
