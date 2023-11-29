public class Result {
    private int kewWordCount;
    private String fileName;
    private String filePath;
    public Result( String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.kewWordCount = 0;
    }

    public int getKewWordCount() {
        return kewWordCount;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setKewWordCount(int kewWordCount) {
        this.kewWordCount = kewWordCount;
    }
}
