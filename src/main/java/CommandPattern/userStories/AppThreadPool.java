package CommandPattern.userStories;

import java.util.concurrent.*;


public class AppThreadPool {
    private final ExecutorService executor;

    public AppThreadPool(int numberOfThreads){
        executor =  new ThreadPoolExecutor(numberOfThreads, numberOfThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }


    public ExecutorService getInstance() {
        return executor;
    }

}
