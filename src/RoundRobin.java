import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {
  private static Queue<File> files;
  private static int currentIndex;
  private static Result[] results;
  private static String keyWord;
  private static RoundRobinThread[] threads;
  public static void init(String keyWord, File[] files , int noOfThreads){
      RoundRobin.keyWord = keyWord;
      RoundRobin.files = convertFilesToQueue(files);
      RoundRobin.currentIndex = 0;
      RoundRobin.results = new Result[files.length];
      RoundRobin.threads = new RoundRobinThread[noOfThreads];
        for(int i = 0; i < noOfThreads; i++) {
            RoundRobinThread thread = new RoundRobinThread();
            thread.start();
      }
    }

    public static Queue<File> getFiles() {
        return files;
    }

    public static void setFiles(Queue<File> files) {
        RoundRobin.files = files;
    }
    public static void setResults(Result[] results) {
        RoundRobin.results = results;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public static void setCurrentIndex(int currentIndex) {
        RoundRobin.currentIndex = currentIndex;
    }

    public static void addResult(Result result) {
        if(currentIndex >= results.length) {
            throw new IndexOutOfBoundsException("Results array is full");
        }
        results[currentIndex] = result;
        currentIndex++;
    }
    public static Result[] getResults() {
        return results;
    }
    public static String getKeyWord() {
        return keyWord;
    }
    private static Queue<File> convertFilesToQueue(File[] files) {
        Queue<File> fileQueue = new LinkedList<>();
        for (File file : files) {
            fileQueue.offer(file);
        }
        return fileQueue;
    }

}
