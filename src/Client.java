import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Receive and print the algorithm choice prompt from the server
            String prompt = in.readLine();
            System.out.println(prompt);
            Scanner scanner1 = new Scanner(System.in);
            String keyword = scanner1.nextLine();
            out.println(keyword);
            prompt = in.readLine();
            System.out.println(prompt);
            String path = scanner1.nextLine();
            out.println(path);
            prompt = in.readLine();
            System.out.println(prompt);
            int algorithmChoice = scanner1.nextInt();
            // Further processing based on the server's response
            // ...

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
