import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Server {

    private static List<File> listFilesRecursively(String directoryPath) {
        List<File> fileList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    fileList.add(file);

                    if (file.isDirectory()) {
                        // Recursive call for subdirectories
                        fileList.addAll(listFilesRecursively(file.getAbsolutePath()));
                    }
                }
            }
        }
        return fileList;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server waiting for connections on port " + serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established with " + clientSocket.getInetAddress());

                // Create BufferedReader for reading client input
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Create PrintWriter for sending responses to the client
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                // ask the client for the keyword and the path
                writer.println("Enter the keyword:");
                String keyword = reader.readLine();
                writer.println("Enter the path:");
                String path = reader.readLine();
                // Prompt the client for the search algorithm choice
                writer.println("Choose a search algorithm (1 for Equal Distribution, 2 for Round Robin):");
                int algorithmChoice = Integer.parseInt(reader.readLine());
                if (algorithmChoice == 1) {
                    // Perform equal distribution logic
                    // (You can replace this with your specific implementation)
                    writer.println("Equal Distribution Algorithm Selected");
                } else if (algorithmChoice == 2) {
                    // Perform round-robin logic
                    // (You can replace this with your specific implementation)
                    writer.println("Round Robin Algorithm Selected");
                } else {
                    writer.println("Invalid choice. Please choose 1 or 2.");
                }
                // we get list of files using a searching function yet to be implemented
                // File[] files = getFiles(path);
                // comput the number of threads based on the cores of the machine
                int noOfThreads = Runtime.getRuntime().availableProcessors();
                // if the choice is 1 then we use the equal distribution algorithm
                // yet to be done
                // if the choice is 2 then we use the round robin algorithm
                //services.RoundRobin.init(keyword, files, noOfThreads);

                // ...

                // Close the connection with the client
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
