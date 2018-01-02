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

## Synchronized

For the below example, the value of the counter should be 2000,
but the results are not always consistent, so to get a consistent 
result we need to use synchronized key word.

First example is without synchronized, this will gives results such as 
2000, 1534 etc.

    public class SynchronizedExplained {
    
        private static int counter = 0;
    
        public static void main(String[] args) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; i++)
                        ++counter;
                }
            });
    
    
            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; i++)
                        ++counter;
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
    
            System.out.println("Value of Counter is " + counter);
        }
    }
    
  The same example with synchronized keyword.
  
    public class SynchronizedExplained {
  
      private static int counter = 0;
  
      public static void main(String[] args) {
          Thread t1 = new Thread(new Runnable() {
              public void run() {
                  for (int i = 0; i < 1000; i++)
                      increment();
              }
          });
  
  
          Thread t2 = new Thread(new Runnable() {
              public void run() {
                  for (int i = 0; i < 1000; i++)
                      increment();
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
  
          System.out.println("Value of Counter is " + counter);
      }
  
      private static synchronized void increment() {
          ++counter;
      }
    }
    
## Synchronized Block

for the below example, we are using synchronized key word on method,
it has got one disadvantage, if there two methods say m1 and m2 which 
are synchronized as in the below example, and if two threads t1 and t2 
are trying to access say t1 accessing m1 and t2 accessing m2. Both the threads
cannot access the methods at the same time since the lock is intrinsic lock
which means on the class.

        public class SynchronizedBlock {
        
            private static int count1 = 0;
            private static int count2 = 0;
        
            public static synchronized void add() {
                ++count1;
            }
        
            public static synchronized void addAgain() {
                ++count2;
            }
        
            public static void main(final String[] args) {
                Thread t1 = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 1000; i++) {
                            add();
                            addAgain();
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        
                Thread t2 = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 1000; i++) {
                            add();
                            addAgain();
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        
                long startTime = System.currentTimeMillis();
                t1.start();
                t2.start();
        
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        
                long endTime = System.currentTimeMillis();
                System.out.println("time taken for " + (endTime - startTime) + " count 1  is " + count1 + " count 2 is " + count2);
        
            }
        }

 o/p will be which can vary
 time taken for 1376 count 1  is 2000 count 2 is 2000
 
 
 We will make few changes in the above problem where we will not use intrinsic
 lock but will use lock on object, so that both the methods can be accessed
 by two different threads at the same time.
 
         public class SynchronizedBlock {
         
             private static int count1 = 0;
             private static int count2 = 0;
         
             private static Object lock1 = new Object();
             private static Object lock2 = new Object();
         
             public static void add() {
                 synchronized (lock1) {
                     ++count1;
                 }
             }
         
             public static void addAgain() {
                 synchronized (lock2) {
                     ++count2;
                 }
             }
         
             public static void main(final String[] args) {
                 Thread t1 = new Thread(new Runnable() {
                     public void run() {
                         for (int i = 0; i < 1000; i++) {
                             add();
                             addAgain();
                             try {
                                 Thread.sleep(1);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                 });
         
                 Thread t2 = new Thread(new Runnable() {
                     public void run() {
                         for (int i = 0; i < 1000; i++) {
                             addAgain();
                             add();
                             try {
                                 Thread.sleep(1);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                 });
         
                 long startTime = System.currentTimeMillis();
                 t1.start();
                 t2.start();
         
                 try {
                     t1.join();
                     t2.join();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
         
                 long endTime = System.currentTimeMillis();
                 System.out.println("time taken is " + (endTime - startTime) + " count 1  is " + count1 + " count 2 is " + count2);
         
             }
         }

o/p will be 
time taken is 1317 count 1  is 2000 count 2 is 2000


## Wait and Notify


when wait is called on a Thread, it stops executing the thread
and starts executing when some other thread calls notify on the same 
lock, which is intrinsic lock

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

 

