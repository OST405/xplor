package services;

import utils.Result;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public abstract class Search extends Thread {
     public long startTime;
     public long endTime;
    private File[] searchedFiles;
    private File[] matchedFiles;

    public Search() {
        startTime = System.currentTimeMillis();
    }
    protected Result search(String file, String keyword) {
        String fileName = getFileNameFromPath(file);
        String filePath = file;
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
                Result result = new Result(fileName, filePath,Thread.currentThread().getId());
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

    public String getFileNameFromPath(String path){
        String[] pathParts = path.split("/");
        return pathParts[pathParts.length - 1];
    }

    public void threadIsDone() {
        endTime = System.currentTimeMillis();
    }
}
