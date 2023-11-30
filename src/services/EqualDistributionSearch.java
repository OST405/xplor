package services;

import utils.Result;

import java.io.File;

public class EqualDistributionSearch extends Search {
    private File[] files;
    private int i;
    private int j;
    private String keyWord;
    private Result[] results;

    public EqualDistributionSearch(File[] files, int i, int j, String keyWord, Result[] results) {
        super();
        this.files = files;
        this.i = i;
        this.j = j;
        this.keyWord = keyWord;
        this.results = results;
    }

    public void run() {
        for (int k = i; k < j; k++) {
            Result result = search(files[k], keyWord);
            if (result != null) {
                results[k] = result;
            }
        }
    }
}
