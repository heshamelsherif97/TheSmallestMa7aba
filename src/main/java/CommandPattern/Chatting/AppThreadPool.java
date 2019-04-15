package CommandPattern.Chatting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppThreadPool {

    private static int numberOfThreads = 10;
    private static ExecutorService executor = Executors.newFixedThreadPool( numberOfThreads);

    public static ExecutorService getInstance(){
        return executor;
    }


}