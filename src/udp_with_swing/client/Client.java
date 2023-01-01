package udp_with_swing.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.*;

public class Client extends JFrame {

    private final JTextField enterField;
    private final JTextArea displayArea;

    // create datagram socket
    private DatagramSocket socket;

    public Client() {
        super("Client");

        enterField = new JTextField("Enter text here");
        enterField.addActionListener(event -> {
            try {
                // create and send packet
                byte[] data = event.getActionCommand().getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 5000);
                socket.send(packet);

                // display packet contents
                display("\nSent: " + event.getActionCommand());
                enterField.setText("");
            } catch (IOException e) {
                display("\nError sending packet");
            }
        });

        add(enterField, "North");

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), "Center");

        setSize(400, 300);
        setVisible(true);

        try {
            socket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForPackets() {
        while (true) {
            try {
                // receive packet
                byte[] data = new byte[100];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);

                // display packet contents
                display("\nReceived: " + new String(packet.getData()));
            } catch (IOException e) {
                display("\nError receiving packet");
            }
        }
    }

    private void display(final String messageToDisplay) {
        SwingUtilities.invokeLater(() -> displayArea.append(messageToDisplay));
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.waitForPackets();
    }
}

