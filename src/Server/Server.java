package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 9090;
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        while (true) {
            System.out.println("Waiting for a connection");
            Socket client = listener.accept();
            System.out.println("Connected to a server");
            ClientHandler ch = new ClientHandler(client, new ModelInterface());
            pool.execute(ch);
        }
    }
}
