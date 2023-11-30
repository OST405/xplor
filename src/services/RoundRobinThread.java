package services;

import services.RoundRobin;
import services.Search;
import utils.Result;

import java.io.File;

public class RoundRobinThread extends Search {

    public RoundRobinThread() {
        super();
    }


    public void run() {
        while (true) {
            File file = getNextFile();

            if (file == null) {
                break; // No more files to process
            }
            Result result = super.search(file, RoundRobin.getKeyWord());
            addResult(result);
        }
        threadIsDone();
    }

    private synchronized File getNextFile() {
        return RoundRobin.getFiles().poll();
    }

    private synchronized void addResult(Result result) {
        RoundRobin.addResult(result);
    }
}
