package Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConceptOfNewCachedThreadPool {

    public static void main(String[] args) {
        /**
         * There is no limit to number of threads in the pool. If the number of request
         * is 10, 10 threads will start and say if all the threads are busy and 2 more requests
         * are submitted, new threads will be created from the pool. So there is no wait.
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(ConceptOfNewCachedThreadPool::run);
        executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 2 " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 3 " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread 1 " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
