import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.net.URI;
import java.util.concurrent.Executors;

@ClientEndpoint
public class MyWebSocketClient {

    @OnOpen
    public void onOpen(Session session) {

//        Executors.newFixedThreadPool()
//        Executors.newSingleThreadExecutor()
//        Executors.new
        System.out.println("Connected to server, session id: " + session.getId());
        try {
            // Send an initial message to the server
            session.getBasicRemote().sendText("Hello, Server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received from server: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error in session " + session.getId() + ": " + throwable.getMessage());
    }

    public static void main(String[] args) {
        // Obtain the default WebSocket container
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        // Replace with the URI of your WebSocket server endpoint
        String uri = "ws://localhost:8080/my-websocket-app/websocket";
        System.out.println("Connecting to " + uri);
        try {
            // Connect to the server and obtain a session
            Session session = container.connectToServer(MyWebSocketClient.class, URI.create(uri));
            // Keep the client running for 60 seconds to allow communication
//            Thread.sleep(60000);
            // Close the connection gracefully
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
