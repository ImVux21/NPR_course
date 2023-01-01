package udp_multicast.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {
    public static void main(String[] args) {
        try {
            // Join the multicast group
            InetAddress group = InetAddress.getByName("230.0.0.0");
            MulticastSocket socket = new MulticastSocket();
            socket.joinGroup(group);

            // Send messages to the multicast group
            String message = "Hello, world!";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, group, 8888);
            while (true) {
                socket.send(packet);
                System.out.println("Sent message: " + message);
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
