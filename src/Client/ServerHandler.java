package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket server;
    private BufferedReader read_from_server;
    private PrintWriter out_to_server;
    protected Client gui;
    private final String CONCATCOMMAND = "concat";
    private final String UPPERCOMMAND = "upper";
    private final String LOWERCOMMAND = "lower";
    protected final String LIMITER = ",";
    public ServerHandler(Socket socket, Client gui) throws IOException {
        server = socket;
        read_from_server = new BufferedReader((new InputStreamReader(server.getInputStream())));
        out_to_server = new PrintWriter(server.getOutputStream(),true);
        this.gui = gui;
        addListenForConcat();
        addListenForUpper();
        addListenForLower();
    }

    protected void addActionForButton(String command) {
        try{
            String to_send="";
            String a = gui.getFirstString();
            String b = gui.getSecString();
            to_send = a+LIMITER+b+LIMITER+command;
            out_to_server.println(to_send);
            String serverResponse = read_from_server.readLine();
            if (serverResponse.contains("\0")) {
                serverResponse = serverResponse.replace("\0", "");
                gui.displayErrorMessage(serverResponse);
            } else {
                gui.setResult(serverResponse);
            }
        }catch(IOException err){
            err.printStackTrace();
        }
    }

    private void addListenForConcat() {
        this.gui.addActionListenerConcat(e -> addActionForButton(CONCATCOMMAND));
    }

    private void addListenForUpper() {
        this.gui.addActionListenerUpper(e -> addActionForButton(UPPERCOMMAND));
    }

    private void addListenForLower() {
        this.gui.addActionListenerLower(e -> addActionForButton(LOWERCOMMAND));
    }

    @Override
    public void run() {
    }
}

