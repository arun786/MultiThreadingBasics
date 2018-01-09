# Re-Entrant Lock
Re entrant lock is enhanced version of Synchronized. It is mutually exclusive lock
example of Reentrant lock is as below.

Features of Reentrant Lock, which were not present in synchronized

1. fairness, it means providing lock to the longest waiting thread.
   (Lock lock = new Reentrantlock(true))
   true means the longest waiting thread will acquire the thread.
   else if it is false, it will not guarantee access order. 
2. It provides interruptibly, it doesnot block infinitely.
 

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


## Producer Consumer adding to a list

    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.locks.Condition;
    import java.util.concurrent.locks.Lock;
    import java.util.concurrent.locks.ReentrantLock;
    
    public class ProducerConsumerUsingReentrantLock {
    
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        private int TOP = 5;
        private int BOTTOM = 0;
        private List<Integer> lst = new ArrayList<>();
        private int value = 0;
    
        public static void main(String[] args) {
            ProducerConsumerUsingReentrantLock worker = new ProducerConsumerUsingReentrantLock();
            Thread producer = new Thread(() -> {
                try {
                    worker.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread consumer = new Thread(() -> {
                try {
                    worker.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            producer.start();
            consumer.start();
        }
    
        /**
         * This method will add the values to the list
         */
        private void producer() throws InterruptedException {
            try {
                lock.lock();
                while (true) {
                    if (lst.size() == TOP) {
                        System.out.println("List size reached the Required Limit");
                        condition.await();
                    } else {
                        System.out.println("adding value to List");
                        lst.add(value++);
                        condition.signal();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    
        /**
         * @throws InterruptedException This method removes item from the list
         */
        private void consumer() throws InterruptedException {
            try {
                lock.lock();
                while (true) {
                    Thread.sleep(100);
                    if (lst.size() == BOTTOM) {
                        System.out.println("List is Empty");
                        condition.await();
                    } else {
                        System.out.println("Removed item from the list..");
                        lst.remove(--value);
                        condition.signal();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
