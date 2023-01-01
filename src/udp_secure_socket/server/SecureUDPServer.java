package udp_secure_socket.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class SecureUDPServer {
    public static void main(String[] args) {
        try {
            // Load the keystore and create the SSL context
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(SecureUDPServer.class.getResourceAsStream("/keystore.jks"), "password".toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keystore, "password".toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Create a secure server socket and start listening for incoming connections
            SSLServerSocket serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(8888);
            serverSocket.setNeedClientAuth(true);
            while (true) {
                // Accept a client connection and delegate the processing to a worker thread
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                WorkerThread workerThread = new WorkerThread(socket);
                workerThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class WorkerThread extends Thread {
    private SSLSocket socket;

    public WorkerThread(SSLSocket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Read a message from the client
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            socket.receive(packet); socket.
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + message);

            // Send a response to the client
            byte[] data = "ACK".getBytes();
            DatagramPacket response = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
            socket.send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

