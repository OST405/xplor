import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server waiting for connections on port " + serverSocket.getLocalPort());

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connection established with " + clientSocket.getInetAddress());

                    RequestHandler responseHandler = new RequestHandler(clientSocket);
                    responseHandler.start();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
