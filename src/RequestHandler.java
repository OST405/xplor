import services.EqualDistributionSearch;
import services.RoundRobinSearch;
import services.Search;
import utils.AlgorithmChoice;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Queue;

import utils.FilesUtils;
import utils.Result;

public class RequestHandler extends Thread {

    private Socket client;

    public RequestHandler(Socket client) {
        super();
        this.client = client;
    }

    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        AlgorithmChoice algorithmChoice = null;

        try {
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            writer.println("Enter the keyword:");
            String keyword = reader.readLine();
            writer.println("Enter the path:");
            String path = reader.readLine();
            // Prompt the client for the search algorithm choice
            writer.println("Choose a search algorithm (1 for Equal Distribution, 2 for Round Robin):");
            boolean invalidChoice = true;
            while (invalidChoice) {
                switch (Integer.parseInt(reader.readLine())) {
                    case 1:
                        algorithmChoice = AlgorithmChoice.EQUAL_DISTRIBUTION;
                        invalidChoice = false;
                        break;
                    case 2:
                        algorithmChoice = AlgorithmChoice.ROUND_ROBIN;
                        invalidChoice = false;
                        break;
                    default:
                        writer.println("Invalid choice. Please try again: ");
                        break;
                }
            }

            int noOfThreads = Runtime.getRuntime().availableProcessors();

            List<String> files = FilesUtils.listFilesRecursively(path);
            Result[] results = new Result[files.size()];
            int[] resultsIndex = {0};

            Search[] threads = new Search[noOfThreads];

            if (algorithmChoice == AlgorithmChoice.EQUAL_DISTRIBUTION) {
                int noOfFiles = files.size();
                int noOfFilesPerThread = noOfFiles / noOfThreads;
                int i = 0;
                int j = noOfFilesPerThread;
                for (int k = 0; k < noOfThreads - 1; k++) {
                    threads[k] = new EqualDistributionSearch(files.toArray(new String[0]), i, j, keyword, results, resultsIndex);
                    i = j;
                    j += noOfFilesPerThread;
                }
                threads[noOfThreads - 1] = new EqualDistributionSearch(files.toArray(new String[0]), i, j + noOfFiles % noOfThreads, keyword, results, resultsIndex);
            } else {
                Queue<String> filesQueue = FilesUtils.convertFilesToQueue(files.toArray(new String[0]));
                for (int i = 0; i < noOfThreads; i++) {
                    threads[i] = new RoundRobinSearch(filesQueue, resultsIndex, results, keyword);
                }
            }

            for (Thread search : threads) {
                search.start();
            }
            int counter=1;
            long totalRunTime=0;
            StringBuilder ThreadsStats= new StringBuilder();
            // add /n to the start of the first line and remove it from the last line
            for (Search search : threads) {
                search.join();
                if(counter==1)
                    ThreadsStats.append("\nThread ").append(counter).append(" run time is ").append(search.endTime - search.startTime).append(" ms\n");
                else if(counter > 1 && counter < threads.length)
                    ThreadsStats.append("Thread ").append(counter).append(" run time is ").append(search.endTime - search.startTime).append(" ms\n");
                else if(counter == threads.length)
                    ThreadsStats.append("Thread ").append(counter).append(" run time is ").append(search.endTime - search.startTime).append(" ms");
                totalRunTime+=search.endTime-search.startTime;
                counter++;
            }

            writer.println(results.length + " files searched.");
            int totalMatches = 0;
            for (Result result : results) {
                if (result != null) {
                    writer.println(result.getKeyWordCount() + " matches found in file " + result.getFileName());
                    totalMatches += result.getKeyWordCount();
                }
            }
            writer.println(ThreadsStats);
            writer.println("Total run time is "+totalRunTime+" ms");
            writer.println(totalMatches + " matches found in total.");
            writer.println("END");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (writer != null)
                    writer.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
