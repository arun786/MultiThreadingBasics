package Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExplained {
    public static void main(String[] args) {

        /**
         * this Creates a new thread pool with the specified number of threads. if more requests are
         * submitted when all the threads are active, the request will wait in a queue, until the thread returns
         * to the pool.
         */
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int ii = 0; ii < 10; ii++) {
            /**
             * executorService.submit can take Runnable or Callable as parameter
             * but execute takes only runnable as parameter
             */
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Executing " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();

    }
}

