import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SetSoTimeOutExample  {



    static void startServer()
    {

        try {
            ServerSocket server=new ServerSocket(8080);


            System.out.println("server is listning...");
            Socket client=server.accept();
            client.setSoTimeout(500);


//            while (client.)
            BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
            String message;
            while ((message=reader.readLine())!=null)
            {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

   static void startClient()
    {

        int reciveSize=8*1024;
        int sendSize=8*1024;

        try {
            Socket socket=new Socket("localhost",8080);
            System.out.println("Connected to server");



            //set input buffer size and output buffer size

            socket.setSendBufferSize(reciveSize);
//            socket.setSendBufferSize(sendSize);
//            Thread.sleep(1000);
            System.out.println(socket.getSendBufferSize());

            PrintWriter writer=new PrintWriter(socket.getOutputStream(),true);
            Scanner sc=new Scanner(System.in);
            Thread.sleep(1000);
            while (true)
            {
                socket.setKeepAlive(true);
                System.out.print("Enter message or ('exit' fot quit):");
                String message=sc.nextLine();

                if(message.equalsIgnoreCase("exit"))
                {
                    break;
                }
                writer.println(message);
            }
        } catch (IOException e) {
            System.out.println("Error to connecting with server");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        socket.connect();


    }
    public static void main(String[] args) {


//        startServer();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        startClient();
    }
}
