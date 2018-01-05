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

## Example of Reentrant Lock along with Condition

    import java.util.concurrent.locks.Condition;
    import java.util.concurrent.locks.Lock;
    import java.util.concurrent.locks.ReentrantLock;
    
    public class ProducerAndConsumerUsingReentrantLock {
    
        public static void main(String[] args) {
            ProducerAndConsumerUsingReentrantLock worker = new ProducerAndConsumerUsingReentrantLock();
            Thread t1 = new Thread(() -> {
                worker.producer();
            });
    
            Thread t2 = new Thread(() -> {
                worker.consumer();
            });
    
            t1.start();
            t2.start();
    
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        /**
         * Its a concrete implementation of Lock Implementation
         * Benefits of Reentrant Lock are
         * 1. Ability of Lock Interruptibly
         * 2. Ability to timeout while waiting for a lock
         * 3. Power to create a fair lock.
         * 4. API to get the list of waiting thread for lock
         * 5. Flexibility to try for lock without waiting
         */
        private Lock lock = new ReentrantLock();
        /**
         * Condition object suspends the execution of thread until the condition is true
         * basically it is used by thread to notify about a condition.
         */
        private Condition condition = lock.newCondition();
    
        public void producer() {
    
            try {
                lock.lock();
                System.out.println("Locking Producer");
                try {
                    /**
                     * It suspends the thread till it gets a signal to start on the same lock
                     * Its similar to wait method of Object class
                     */
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    
                System.out.println("Producer gets the Lock after wait when it gets a signal");
            }finally {
                lock.unlock();
            }
        }
    
        public void consumer() {
    
            try {
                lock.lock();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Inside the Consumer Method");
                /**
                 * Its more like notify method from object class
                 */
                condition.signal();
                System.out.println("Signal raised for the Consumer");
            }finally {
                lock.unlock();
            }
        }
    }
