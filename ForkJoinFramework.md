# ForkJoinFramework

## RecursiveTask<T>

This enables Parallel computation, any class extending RecursiveTask<T> will
have the ability for parallel computation. It has a return type of T.

## RecursiveAction

This class also enables Parallel computation but has no return type.

## ForkJoinPool


## fork()

Asynchronously execute the task in the pool
## join()

Returns the result when the computation is done.

## invoke()

execute the given task and return the result after execution

## invokeAll()


## Example of finding the max value from an array.

    package ForkJoin;
    
    import java.util.Random;
    import java.util.concurrent.ForkJoinPool;
    import java.util.concurrent.RecursiveTask;
    
    public class ParallelMaxFinding {
    
        public static int THRESHOLD = 0;
    
        public static void main(String[] args) {
            int nums[] = initalizeNums();
    
    
            THRESHOLD = nums.length / Runtime.getRuntime().availableProcessors();
            long start = System.currentTimeMillis();
            //ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()); //As per java 7
            ForkJoinPool pool = ForkJoinPool.commonPool(); //Java 8
            MaxFind maxFind = new MaxFind(nums, 0, nums.length);
            System.out.println("Parallel max is " + pool.invoke(maxFind));
            System.out.println("Time taken is " + (System.currentTimeMillis() - start + "ms"));
    
            start = System.currentTimeMillis();
            MaxSeqFind maxSeqFind = new MaxSeqFind(nums, nums.length);
            System.out.println("Sequential Max is " + maxSeqFind.getMax());
            System.out.println("Time taken " + (System.currentTimeMillis() - start) + "ms");
    
        }
    
        private static int[] initalizeNums() {
            Random random = new Random();
            int nums[] = new int[300000000];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = random.nextInt(1000);
            }
            return nums;
        }
    }
    
    
    class MaxFind extends RecursiveTask<Integer> {
        private int[] nums;
        private int low;
        private int high;
    
        public MaxFind(int[] nums, int low, int high) {
            this.nums = nums;
            this.low = low;
            this.high = high;
        }
    
    
        @Override
        protected Integer compute() {
            if (high - low < ParallelMaxFinding.THRESHOLD) {
                return parallelFind();
            } else {
    
                int middle = (low + high) / 2;
                MaxFind task1 = new MaxFind(nums, low, middle);
                MaxFind task2 = new MaxFind(nums, middle + 1, high);
                invokeAll(task1, task2);
                return Math.max(task1.join(), task2.join());
            }
        }
    
        public int parallelFind() {
            int max = nums[low];
            for (int i = low + 1; i < high; i++) {
                if (nums[i] > max) {
                    max = nums[i];
                }
            }
            return max;
        }
    }
    
    class MaxSeqFind {
        private int nums[];
        private int high;
    
        public MaxSeqFind(int[] nums, int high) {
            this.nums = nums;
            this.high = high;
        }
    
        public int getMax() {
            int max = nums[0];
            for (int i = 1; i < high; i++) {
                if (nums[i] > max) {
                    max = nums[i];
                }
            }
            return max;
        }
    }
