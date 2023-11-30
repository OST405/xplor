package utils;

public class Result {
    private int keyWordCount;
    private String fileName;
    private String filePath;
    public Result( String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.keyWordCount = 0;
    }

    public int getKeyWordCount() {
        return keyWordCount;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setKeyWordCount(int keyWordCount) {
        this.keyWordCount = keyWordCount;
    }
}
