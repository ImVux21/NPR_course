package udp_multicast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastClient {
    public static void main(String[] args) {
        try {
            // Join the multicast group
            InetAddress group = InetAddress.getByName("230.0.0.0");
            MulticastSocket socket = new MulticastSocket(8888);
            socket.joinGroup(group);

            // Receive messages from the multicast group
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received message: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

