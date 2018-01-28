package Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConceptOfNewSingleThreadExecutor {
    public static void main(String[] args) {
        /**
         * this is a special case where Thread Pool has got only 1 thread.
         * Here the tasks executes sequentially so if there is a shared resource
         * we don't need to use synchronization.
         */
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 1 " + i);
            }
        });

        executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 2 " + i);
            }
        });

        executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 3 " + i);
            }
        });

        executorService.shutdown();
    }
}
