# Re-Entrant Lock
Re entrant lock is enhanced version of Synchronized. It is mutually exclusive lock
example of Reentrant lock is as below

    import java.util.concurrent.locks.Lock;
    import java.util.concurrent.locks.ReentrantLock;
    
    public class ReEntrantsLockExplained {
        private static int counter;
        private static Lock lock = new ReentrantLock();
    
        private static int increment() {
            lock.lock();
            try {
                for (int i = 0; i < 1000; i++) {
                    counter++;
                }
            } finally {
                lock.unlock();
            }
            return counter;
        }
    
        public static void main(String[] args) throws InterruptedException {
            Runnable r = () -> increment();
    
            Thread t1 = new Thread(r);
            Thread t2 = new Thread(r);
    
            t1.start();
            t2.start();
    
            t1.join();
            t2.join();
    
            System.out.println("counter " + counter);
        }
    }
