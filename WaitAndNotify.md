# Producer Consumer using Wait and Notify

    import java.util.ArrayList;
    import java.util.List;

    public class ProducerAndConsumer {
    
    
        private static int TOP = 5;
        private static int BOTTOM = 0;
        private static List<Integer> lst = new ArrayList();
        private static int value = 0;
        private static Object lock = new Object();
    
        public static void main(String[] args) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    try {
                        producer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    
            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    try {
                        consumer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    
            t1.start();
            t2.start();
        }
    
        private static void producer() throws InterruptedException {
    
            synchronized (lock) {
                while (true) {
                    if (lst.size() == TOP) {
                        System.out.println("Waiting for items to be removed from List");
                        lock.wait();
                    } else {
                        System.out.println("Adding " + value + " to the List");
                        lst.add(value++);
                        lock.notify();
                    }
    
                    Thread.sleep(100);
                }
            }
        }
    
    
        private static void consumer() throws InterruptedException {
    
            synchronized (lock) {
                while (true) {
                    if (lst.size() == BOTTOM) {
                        System.out.println("Waiting for the item to be added to the list");
                        lock.wait();
                    } else {
                        System.out.println("Removing : " + lst.remove(--value));
                        lock.notify();
                    }
                    Thread.sleep(100);
                }
            }
        }
    
    }
    
