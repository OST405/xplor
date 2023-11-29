import java.io.File;

public class RoundRobinThread extends searchingThread{

    public RoundRobinThread(){
        super();
    }
    public void run(){
    }
    private  synchronized File getNextFile(){
        return RoundRobin.getFiles().poll();
    }
    private  synchronized void addResult(Result result){
        RoundRobin.addResult(result);
    }
}
