# Parallel Sum 

    package ParallelAlgorithm;
    
    import java.util.Random;
    
    public class ParallelSum {
    
        public static void main(String[] args) {
    
            Random random = new Random();
            int num[] = new int[100000000];
            for (int i = 0; i < num.length; i++) {
                num[i] = random.nextInt(100);
            }
    
            long start = System.currentTimeMillis();
            int result = sum(num);
            long end = System.currentTimeMillis();
    
            System.out.println("Result " + result + " time " + (end - start) + " ms");
    
    
            int numberOfThreads = Runtime.getRuntime().availableProcessors();
            System.out.println("Number of Processors .... "+numberOfThreads);
            start = System.currentTimeMillis();
            result = new WorkerSum(numberOfThreads).sum(num);
            end = System.currentTimeMillis();
    
            System.out.println("Result " + result + " time " + (end - start) + " ms");
    
        }
    
        /**
         * @param num
         * @return Sequential Sum
         */
        public static int sum(int num[]) {
            int total = 0;
            for (int i = 0; i < num.length; i++) {
                total = total + num[i];
            }
            return total;
        }
    
    }
    
    class WorkerSum {
        private ParallelWorker[] parallelWorkers;
        private int numberOfThreads;
    
        public WorkerSum(int numberOfThreads) {
            this.numberOfThreads = numberOfThreads;
            parallelWorkers = new ParallelWorker[numberOfThreads];
        }
    
        public int sum(int num[]) {
    
            int steps = (int) Math.ceil(num.length * 1.0 / numberOfThreads);
    
            for (int i = 0; i < numberOfThreads; i++) {
                parallelWorkers[i] = new ParallelWorker(num, i * steps, (i + 1) * steps);
                parallelWorkers[i].start();
            }
    
            for (ParallelWorker worker : parallelWorkers) {
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            int total = 0;
            for (ParallelWorker worker : parallelWorkers) {
                total = total + worker.getPartialSum();
            }
    
            return total;
        }
    }
    
    class ParallelWorker extends Thread {
    
        private int num[];
        private int low;
        private int high;
        private int partialSum;
    
        public ParallelWorker(int[] num, int low, int high) {
            this.num = num;
            this.low = low;
            this.high = high;
        }
    
        @Override
        public void run() {
            partialSum = 0;
            for (int i = low; i < high; i++) {
                partialSum = partialSum + num[i];
            }
        }
    
        public int getPartialSum() {
            return partialSum;
        }
    }
