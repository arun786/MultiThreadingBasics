# MultiThreadingBasics

## Sequential Programming

        public class SequentialRunning {
            public static void main(String[] args) {
                Runner1 runner1 = new Runner1();
                Runner2 runner2 = new Runner2();
        
                runner1.startRunning();
                runner2.startRunning();
            }
        }
        
        class Runner1 {
            public void startRunning() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Runner1.class + " running " + i);
                }
            }
        }
        
        class Runner2 {
            public void startRunning() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Runner2.class + " running " + i);
                }
            }
        }

# Basic Concept implements Runnable

        public class ImplementsRunnable {
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

## Extends Thread

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
        
## Example of Join

Join basically denotes that once the thread on which the join is called dies
then the main thread or the other thread ends

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
