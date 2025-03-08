import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonBlockingServer {


    public static void main(String[] args) {


        try {
            Selector selector=Selector.open();
            ServerSocketChannel serverChannel=  ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(8080));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Non-blocking server started on port 8080...");
            while (true)
            {
                selector.select();
                Iterator<SelectionKey> Keys=selector.selectedKeys().iterator();

                while (Keys.hasNext())
                {
                    SelectionKey key=Keys.next();
                    Keys.remove();
                    if(key.isAcceptable())
                    {
                        ServerSocketChannel server=(ServerSocketChannel) key.channel();
                        SocketChannel client=server.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_READ);

                        System.out.println("Client is connected: "+client.getRemoteAddress());


                    }
                    else if(key.isReadable())
                    {
                        SocketChannel client=(SocketChannel) key.channel();
                        ByteBuffer buffer=ByteBuffer.allocate(1024);

                        int bytesRead=client.read(buffer);
                        if(bytesRead==-1)
                        {
                            client.close();
                            System.out.println("Client disconnected");
                        }
                        else
                        {
                            buffer.flip();
                            String message = new String(buffer.array(), 0, bytesRead);
                            System.out.println("📩 Client says: " + message);

                            // Echo back the message
                            buffer.flip();
                            client.write(buffer);
                        }

                    }

                }
            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
