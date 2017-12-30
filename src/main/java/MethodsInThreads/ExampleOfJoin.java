package MethodsInThreads;

public class ExampleOfJoin {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());

        Thread t2 = new Thread(new Runner2());
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All the threads completed and then this is called");
    }


}

class Runner1 implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Runner 1 " + i);
        }
    }
}

class Runner2 implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Runner 2 " + i);
        }
    }
}
