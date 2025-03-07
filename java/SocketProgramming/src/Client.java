import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) throws IOException {


        Socket socket=new Socket("10.20.41.76",8080);
        PrintWriter writer=new PrintWriter(socket.getOutputStream(),true);

        while (true)
        {
            System.out.println("Enter message if not then exit");
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
            writer.println(str);
        }
//        client.connect(8080);
//        socket.close();


    }
}
