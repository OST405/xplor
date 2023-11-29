import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {
  private Queue<File> files;
    private int currentIndex;
    private Result[] results;
    private final String keyWord;
    public RoundRobin(File[] files, String keyWord) {
        this.keyWord = keyWord;
        this.currentIndex = 0;
        this.results = new Result[files.length];
        this.files = convertFilesToQueue(files);
    }

   public void addResult(Result result) {
        if(currentIndex >= results.length) {
            throw new IndexOutOfBoundsException("Results array is full");
        }
        results[currentIndex] = result;
        currentIndex++;
    }
    public Result[] getResults() {
        return results;
    }
    public String getKeyWord() {
        return keyWord;
    }
    private Queue<File> convertFilesToQueue(File[] files) {
        Queue<File> fileQueue = new LinkedList<>();
        for(File file : files) {
            fileQueue.offer(file);
        }
        return fileQueue;
    }

}
