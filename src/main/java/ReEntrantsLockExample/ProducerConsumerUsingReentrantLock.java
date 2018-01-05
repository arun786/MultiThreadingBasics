package ReEntrantsLockExample;

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
