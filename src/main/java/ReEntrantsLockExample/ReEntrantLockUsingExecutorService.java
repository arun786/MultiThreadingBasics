package ReEntrantsLockExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntrantLockUsingExecutorService {
    public static void main(String[] args) {
        reentrantLockWithExecutorService();
    }

    public static void reentrantLockWithExecutorService() {
        /**
         * This will create 2 threads in a pool. It will be fixed.
         */
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        executorService.submit(() -> {
            lock.lock();
            try {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        });

        executorService.submit(() -> {
            System.out.println("Is Locked " + lock.isLocked());
            System.out.println("Is held by thread " + lock.isHeldByCurrentThread());
            System.out.println("Fairness of thread " + lock.isFair());

            boolean locked = lock.tryLock();
            System.out.println("Lock acquired " + locked);
        });

        executorService.shutdown();


    }
}
