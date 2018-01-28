# Executors and its methods


## ExecutorService 
ExecutorService is an Interface which represents an asynchronous execution 
mechanism of executing tasks in the background.

## Executors 
These are factory class which creates an Executor Service.

## Methods of Executors are as below.

    1. NewFixedThreadPool. 
    
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
                     * executorService.execute can take Runnable or Callable as parameter
                     * but submit takes only runnable as parameter
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
            }
        }
        
    2. newCachedThreadPool.
    
             /*
             * There is no limit to number of threads in the pool. If the number of request
             * is 10, 10 threads will start and say if all the threads are busy and 2 more requests
             * are submitted, new threads will be created from the pool. So there is no wait.
             */
            ExecutorService executorService = Executors.newCachedThreadPool();
      
    3. newSingleThreadExecutor
    
                /**
                 * this is a special case where Thread Pool has got only 1 thread.
                 * Here the tasks executes sequentially so if there is a shared resource
                 * we don't need to use synchronization.
                 */
                ExecutorService executorService = Executors.newSingleThreadExecutor();  
