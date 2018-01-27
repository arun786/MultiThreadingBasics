# Semaphore

Semaphores are objects that can be atomically incremented or decremented to get access to shared resources.

These are of two types.

1. Counting Semaphore
2. Binary Semaphore.

Suppose for example, we have a printer which can be shared among multiple
users, but the printer can serve only one user at a time. How do we ensure that 
the printing is done one at a time and others have to wait in a queue.


    package SempahoreExplained;
    
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    import java.util.concurrent.Semaphore;
    
    public class PrinterSemaphore {
    
        public static void main(String[] args) {
    
            ExecutorService executorService = Executors.newCachedThreadPool();
    
            executorService.submit(() -> {
                System.out.println("Printing started");
                for (int i = 0; i < 12; i++) {
                    Printer.INSTANCE.print(i);
                }
            });
        }
    }
    
    enum Printer {
    
        INSTANCE;
        private Semaphore semaphore = new Semaphore(1);
        public void print(int user) {
            try {
                semaphore.acquire();
                printForAUser(user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Printing completed for user " + user);
                semaphore.release();
            }
        }
    
        private void printForAUser(int user) {
            System.out.println("Printing for a user " + user);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }