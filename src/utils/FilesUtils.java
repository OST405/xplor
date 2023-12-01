package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FilesUtils {
    public static List<String> listFilesRecursively(String directoryPath) throws InterruptedException {
        List<String> fileList = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(new String[]{"find", directoryPath, "-type", "f"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                fileList.add(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error executing find command. Exit code: " + exitCode);
            }

        } catch (IOException  | InterruptedException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public static Queue<String> convertFilesToQueue(String[] files) {
        Queue<String> fileQueue = new LinkedList<>();
        for (String file : files) {
            fileQueue.offer(file);
        }
        return fileQueue;
    }
}
