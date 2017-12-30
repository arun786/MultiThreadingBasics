package ThreadRunning;

public class ThreadRunningExtendsThread {
    public static void main(String[] args) {
        Thread t1 = new Runner3();
        t1.start();

        Thread t2 = new Runner4();
        t2.start();
    }
}

class Runner3 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("runner3 " + i);
        }
    }
}

class Runner4 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("runner4 " + i);
        }
    }
}