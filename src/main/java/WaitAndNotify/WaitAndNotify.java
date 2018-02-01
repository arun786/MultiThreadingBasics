package WaitAndNotify;

import java.util.Scanner;

public class WaitAndNotify {

    public static void main(String[] args) throws InterruptedException {

        /**
         * if the objects are different notify cannot wake up the thread from waiting state.
         * wait and notify should be called on the same object
         */
        Processor processor = new Processor();
        Runnable r1 = () -> {
            try {
                processor.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable r2 = () -> {
            try {
                processor.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}


class Processor {

    public void producer() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running....");
            wait(); //wait can be called only in synchronized block
            System.out.println("Resumed....");
        }
    }

    public void consumer() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed...");
            notify(); //notify can be called only from synchronized block
            System.out.println("Notify called...");
        }
    }
}