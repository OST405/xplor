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
// commit comment
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
            writer.println("Choose a search algorithm (1 for Equal Distribution, 2 for Round Robin, 3 to Quit):");
            algorithmChoice = switch (Integer.parseInt(reader.readLine())) {
                case 1 -> AlgorithmChoice.EQUAL_DISTRIBUTION;
                case 2 -> AlgorithmChoice.ROUND_ROBIN;
                default -> algorithmChoice;
            };

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
                    threads[k] = new EqualDistributionSearch(files.toArray(new String[0]), i, j, keyword, results, resultsIndex,k);
                    i = j;
                    j += noOfFilesPerThread;
                }
                threads[noOfThreads - 1] = new EqualDistributionSearch(files.toArray(new String[0]), i, j + noOfFiles % noOfThreads, keyword, results, resultsIndex,noOfThreads-1);
            } else {
                Queue<String> filesQueue = FilesUtils.convertFilesToQueue(files.toArray(new String[0]));
                for (int i = 0; i < noOfThreads; i++) {
                    threads[i] = new RoundRobinSearch(filesQueue, resultsIndex, results, keyword,i);
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
                    ThreadsStats.append("\nThread ").append(search.getThreadId()).append(" run time is ").append(search.endTime - search.startTime).append(" ms\n");
                else if(counter > 1 && counter < threads.length)
                    ThreadsStats.append("Thread ").append(search.getThreadId()).append(" run time is ").append(search.endTime - search.startTime).append(" ms\n");
                else if(counter == threads.length)
                    ThreadsStats.append("Thread ").append(search.getThreadId()).append(" run time is ").append(search.endTime - search.startTime).append(" ms");
                totalRunTime+=search.endTime-search.startTime;
                counter++;
            }

            writer.println(results.length + " files searched.");
            int totalMatches = 0;
            for(Search search : threads){
                System.out.println();
                System.out.println("Results for thread "+search.getThreadId()+":");
                for (Result result : results) {
                    if (result != null && result.getThreadId() == search.getThreadId()) {
                        writer.println("Thread "+search.getThreadId()+": "+result.getKeyWordCount() + " matches found in file " + result.getFileName());
                        totalMatches += result.getKeyWordCount();
                    }
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
