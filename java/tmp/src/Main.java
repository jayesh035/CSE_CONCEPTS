import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {


    static  void client()
    {


        try {
            Socket socket=new Socket("localhost",6666);
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("Hello server");
            dout.flush();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static  void server()
    {
        try {
            ServerSocket ss=new ServerSocket(6666);
            Socket s=ss.accept();
            DataInputStream dis=new DataInputStream(s.getInputStream());
            String str=(String)dis.readUTF();
            System.out.println(str);
            ss.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Main.server();
        Main.client();
    }
}