package normal_udp.client;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    private final int PORT = 23456;
    private final String ADDRESS = "127.0.0.1";
    private final DatagramSocket socket = new DatagramSocket();

    public UDPClient() throws SocketException {
    }

    public void sendRequest(String request) {
        try {
            DatagramPacket packetToSend = new DatagramPacket(
                    request.getBytes(),
                    request.getBytes().length,
                    InetAddress.getByName(ADDRESS),
                    PORT
            );
            socket.send(packetToSend);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receiveResponse() {
        DatagramPacket packetReceived = new DatagramPacket(new byte[1024], 1024);
        try {
            socket.receive(packetReceived);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return new String(packetReceived.getData());
    }
}
