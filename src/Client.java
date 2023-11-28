import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            Scanner scanner = new Scanner(System.in);
            System.out.print(in.readLine());
            String searchPath = scanner.nextLine();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(searchPath);

            System.out.println(in.readLine());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

