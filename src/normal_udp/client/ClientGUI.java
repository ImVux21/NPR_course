package normal_udp.client;

import javax.swing.*;
import java.awt.*;
import java.net.SocketException;

public class ClientGUI extends JFrame {
    private final JTextField enterField;
    private final JTextArea displayArea;
    private UDPClient client;
    public ClientGUI() {
        super("Client");

        JLabel label = new JLabel("Enter text here:");
        enterField = new JTextField(12);

        JButton b = new JButton("Send");
        b.addActionListener(e -> {
            String request = enterField.getText();
            client.sendRequest(request);
            enterField.setText("");
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(label);
        panel.add(enterField);
        panel.add(b);
        add(panel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setPreferredSize(new Dimension(300, 80));
        add(displayArea, BorderLayout.CENTER);

        setSize(300, 200);
        setVisible(true);

        try {
            client = new UDPClient();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitForPackets() {
        while (true) {
            displayArea.setText("");
            String response = client.receiveResponse();
            display("Response from server: " + response);
        }
    }

    private void display(String messageToDisplay) {
        SwingUtilities.invokeLater(() -> displayArea.append(messageToDisplay));
    }

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientGUI.waitForPackets();
    }
}
