import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;

public class SecureServer {


    public static void main(String[] args) {



        try
        {

            char[] keyStorePassword="password".toCharArray();
            KeyStore keyStore=KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("server.keystore"),keyStorePassword);


            KeyManagerFactory KMF=KeyManagerFactory.getInstance("SunX509");
            KMF.init(keyStore,keyStorePassword);


            SSLContext sslContext=SSLContext.getInstance("TLS");
            sslContext.init(KMF.getKeyManagers(),null,null);

    final int PORT=8876;
            ServerSocketFactory serverSocketFactory=sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket=
                    (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);
            System.out.println("secure server started on port: "+PORT);


            SSLSocket client=(SSLSocket) sslServerSocket.accept();
            System.out.println("Client connected :"+ client.getInetAddress());

            BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out=new PrintWriter(client.getOutputStream(),true);

            String messageFromClient= in.readLine();
            System.out.println("Client Says:"+ messageFromClient);


            out.println("Hello from secure server");




        } catch (Exception e) {
            System.out.println("Some exception occured:"+ e);
        }
    }
}
