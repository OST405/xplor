package services;

import utils.Result;

import java.io.File;
import java.util.Queue;

public class RoundRobinSearch extends Search {

    private final Queue<String> files;
    private int[] resultsIndex;
    private final Result[] results;
    private final String keyWord;


    public RoundRobinSearch(Queue<String> files, int[] resultsIndex, Result[] results, String keyWord,int threadId) {
        super(threadId);
        this.files = files;
        this.resultsIndex = resultsIndex;
        this.results = results;
        this.keyWord = keyWord;
    }

    public void run() {
        while (true) {
            String file = getNextFile();
            if (file == null) {
                break; // No more files to process
            }
            Result result = super.search(file, keyWord);
            if (result != null) {
                addResult(result);
            }
        }
        threadIsDone();
    }

    private synchronized String getNextFile() {
        return files.poll();
    }

    private synchronized void addResult(Result result) {
        results[resultsIndex[0]] = result;
        resultsIndex[0]++;
    }
}
