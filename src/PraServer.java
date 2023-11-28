import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PraServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server waiting for connections on port " + serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established with " + clientSocket.getInetAddress());

                int numOfCores = Runtime.getRuntime().availableProcessors();
                ExecutorService executorService = Executors.newFixedThreadPool(numOfCores);

                IntStream.range(0, numOfCores).forEach(i -> {
                    executorService.execute(() -> {
                        try {
                            String os = System.getProperty("os.name");

                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println("Detected OS: " + os);
                            out.println("Thread running on CPU core: " + Thread.currentThread().getId());

                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            out.println("Enter the path to search in:");
                            String searchPath = in.readLine();

                            out.println("Searching in path: " + searchPath);

                            Thread.sleep(2000);

                            clientSocket.close();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });

                executorService.shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

