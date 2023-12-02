package services;

import utils.Result;

import java.io.File;
import java.util.List;

public class EqualDistributionSearch extends Search {
    private final String[] files;
    private final int i;
    private final int j;
    private final String keyWord;
    private final Result[] results;
    private int[] resultsIndex;

    public EqualDistributionSearch(String[] files, int i, int j, String keyWord, Result[] results, int[] resultsIndex,int threadId) {
        super(threadId);
        this.files = files;
        this.i = i;
        this.j = j;
        this.keyWord = keyWord;
        this.results = results;
        this.resultsIndex = resultsIndex;
    }

    public void run() {
        for (int k = i; k < j; k++) {
            Result result = search(files[k], keyWord);
            if (result != null) {
                results[resultsIndex[0]] = result;
                resultsIndex[0]++;
            }
        }
        threadIsDone();
    }
}
