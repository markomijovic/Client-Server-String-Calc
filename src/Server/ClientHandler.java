package Server;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private String result = "N/A";
    private ModelInterface model;

    public ClientHandler(Socket clientS, ModelInterface mi) {
        model = mi;
        try {
            clientSocket = clientS;
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void operate(String a, String b, String op, ModelInterface model) {
        if (model.invalidInput(a, b)) {
            result = "\0One of the inputs is empty. Please enter text and try again.";
        } else {
            switch (op) {
                case "concat":
                    model.concatenate(a, b);
                    break;
                case "upper":
                    if (model.getMyString() == null) {
                        result = "\0Tried to convert to upper before concatenating";
                        return;
                    }
                    model.toUpper();
                    break;
                case "lower":
                    if (model.getMyString() == null) {
                        result = "\0Tried to convert to lower before concatenating";
                        return;
                    }
                    model.toLower();
                    break;
            }
            result = model.getMyString();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String clientRequest = socketIn.readLine();
                String[] parts = clientRequest.split(",");
                if (parts.length == 3) {
                    operate(parts[0], parts[1], parts[2], model);
                    socketOut.println(result);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        finally {
            socketOut.close();
            try {
                socketIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
