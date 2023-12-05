import utils.AlgorithmChoice;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

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

            boolean invalidChoice = true;
            while (invalidChoice) {
                int choicePrompt = Integer.parseInt(scanner1.nextLine());
                if (choicePrompt == 1 || choicePrompt == 2) {
                    out.println(choicePrompt);
                    invalidChoice = false;
                } else if (choicePrompt == 3) {
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please try again: ");
                }

            }

            // Receive and print the results from the server
            while (true) {
                String result = in.readLine();
                if (result.equals("END")) {
                    break;
                }
                System.out.println(result);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
