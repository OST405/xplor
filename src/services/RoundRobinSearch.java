package services;

import utils.Result;

import java.io.File;
import java.util.Queue;

public class RoundRobinSearch extends Search {

    private final Queue<File> files;
    private int resultsIndex;
    private final Result[] results;
    private final String keyWord;


    public RoundRobinSearch(Queue<File> files, int resultsIndex, Result[] results, String keyWord) {
        super();
        this.files = files;
        this.resultsIndex = resultsIndex;
        this.results = results;
        this.keyWord = keyWord;
    }

    public void run() {
        while (true) {
            File file = getNextFile();
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

    private synchronized File getNextFile() {
        return files.poll();
    }

    private synchronized void addResult(Result result) {
        results[resultsIndex] = result;
        resultsIndex++;
    }
}
