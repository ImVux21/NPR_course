package udp_secure_socket.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

public class SecureUDPClient {
    public static void main(String[] args) {
        try {
            // Load the keystore and create the SSL context
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(SecureUDPClient.class.getResourceAsStream("/keystore.jks"), "password".toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keystore, "password".toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Create a secure socket and connect to the server
            SSLSocket socket = (SSLSocket) sslContext.getSocketFactory().createSocket("localhost", 8888);

            // Send a message to the server
            String message = "Hello, world!";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.send(packet);

            // Receive a response from the server
            packet = new DatagramPacket(new byte[1024], 1024);
            socket.receive(packet);
            String response = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

