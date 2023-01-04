package normal_udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CustomThread implements Runnable {
    private final DatagramSocket socket;
    private final DatagramPacket packetReceived;

    public CustomThread(DatagramSocket socket, DatagramPacket packetReceived) {
        this.socket = socket;
        this.packetReceived = packetReceived;
    }

    @Override
    public void run() {
        try {
            // receiving request from client
            socket.receive(packetReceived);
            // processing packet
            DatagramPacket packetSent = processPacketReceived(packetReceived);
            // sending response to client
            socket.send(packetSent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DatagramPacket processPacketReceived(DatagramPacket packetReceived) {
        String content = new String(packetReceived.getData()).toUpperCase();
        return new DatagramPacket(content.getBytes(),
                content.getBytes().length,
                packetReceived.getAddress(),
                packetReceived.getPort());
    }
}
