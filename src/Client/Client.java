package Client;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Client extends JPanel {
    private static final String IP = "localhost";
    private static final int SERVER_PORT = 9090;

    private JTextField firstString = new JTextField (10);
    private JLabel addLabel = new JLabel ("+");
    private JTextField secondString = new JTextField (10);
    private JButton concatButton = new JButton ("concatenate");
    private JButton toUpperButton = new JButton ("to upper");
    private JButton toLowerButton = new JButton ("to lower");
    private JTextField theSolution = new JTextField (10);
    private JFrame myFrame;

    public Client () {
        myFrame = new JFrame("String Operator");
        JPanel calcPanel = new JPanel();
        calcPanel.add(firstString);
        calcPanel.add(addLabel);
        calcPanel.add(secondString);
        calcPanel.add(concatButton);
        calcPanel.add(theSolution);
        calcPanel.add(toLowerButton);
        calcPanel.add(toUpperButton);
        myFrame.add(calcPanel);
        myFrame.setSize (400,400);
        myFrame.setVisible(true);
        myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(myFrame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
    public String getFirstString () {
        return firstString.getText();
    }
    public String getSecString () {
        return secondString.getText();
    }
    public void setResult (String solution) {
        theSolution.setText(solution);
    }
    public void addActionListenerConcat (ActionListener listenForConcatButton) {
        concatButton.addActionListener(listenForConcatButton);
    }

    public void addActionListenerUpper (ActionListener listenForUpperButton) {
        toUpperButton.addActionListener(listenForUpperButton);
    }

    public void addActionListenerLower (ActionListener listenForLowerButton) {
        toLowerButton.addActionListener(listenForLowerButton);
    }

    public void displayErrorMessage (String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Socket socket = new Socket(IP, SERVER_PORT);
        ServerHandler serverHandler = new ServerHandler(socket, client);
        new Thread(serverHandler).start();
    }
}
