package services;

import utils.Result;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public abstract class Search extends Thread {
    private long startTime;
    private long endTime;
    private File[] searchedFiles;
    private File[] matchedFiles;

    public Search() {
        startTime = System.currentTimeMillis();
    }
    protected Result search(File file, String keyword) {
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();

        try {
            // Build the grep command
            String[] command = {"grep", "-c", keyword, filePath};

            // Start a new process
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Read the output of the process
            InputStream inputStream = process.getInputStream();
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
            int resultCount = scanner.nextInt(); // Assuming the result is an integer

            // Wait for the process to finish
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                // Successfully executed
                Result result = new Result(fileName, filePath);
                result.setKeyWordCount(resultCount);
                return result;
            } else {
                // Handle the case when the grep command fails
                System.err.println("Error executing grep command. Exit code: " + exitCode);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            threadIsDone();
        }

        // Return a utils.Result indicating an error
        return null;
    }

    public void threadIsDone() {
        endTime = System.currentTimeMillis();
    }
}
