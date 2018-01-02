package MethodsInThreads.WaitAndNotify;

public class WaitAndNotify {

    public static void main(String[] args) {

        final WaitAndNotify wn = new WaitAndNotify();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    wn.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    wn.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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

    public void producer() throws InterruptedException {
        synchronized (this) {
            System.out.println("In Producer Code");
            wait(2000);
            System.out.println("Back to Producer Code");
        }
    }

    public void consumer() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("In Consumer code");
            notify();
            System.out.println("Still in Consumer code");
        }
    }
}


        /*
        op will be
        In Producer Code
        In Consumer code
        Still in Consumer code
        Back to Producer Code*/
