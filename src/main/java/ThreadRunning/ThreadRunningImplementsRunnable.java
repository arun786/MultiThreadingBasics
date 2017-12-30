package ThreadRunning;

public class ThreadRunning {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());
        Thread t2 = new Thread(new Runner2());

        t1.start();
        t2.start();
    }
}

class Runner1 implements Runnable {

    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("runner 1" + " running " + i);
        }
    }
}

class Runner2 implements Runnable {

    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("runner 2" + " running " + i);
        }
    }
}
