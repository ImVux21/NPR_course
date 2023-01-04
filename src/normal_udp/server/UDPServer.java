package normal_udp.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        final int PORT = 23456;
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                DatagramPacket packetReceived = new DatagramPacket(new byte[1024], 1024);
                CustomThread thread = new CustomThread(socket, packetReceived);
                thread.run();
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
