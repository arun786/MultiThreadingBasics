package BlockingQueueConcepts;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Blocking Queue follows the rule of FIFO. it is Synchronized and
 * when the Queue is full, it blocks till values are taken out of the queue.
 */
public class BlockingQueueConcepts {

    public static void main(String[] args) {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(blockingQueue)).start();
        new Thread(new Consumer(blockingQueue)).start();
    }
}

class Producer implements Runnable {
    BlockingQueue<String> blockingQueue;

    public Producer(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try {
                System.out.println("Putting Values to the Queue..");
                blockingQueue.put("count = " + (++count));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {

    BlockingQueue<String> blockingQueue;

    public Consumer(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                String value = blockingQueue.take();
                System.out.println("Taking out from blockingQueue" + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
