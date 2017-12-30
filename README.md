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
