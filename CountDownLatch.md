# CountDownLatch

It is kind of synchronizer which allows one or more threads to wait 
till few threads complete their processing.

there are 2 main methods

1. await() - The main thread waits till all the threads complete.
2. countdown() - to inform countdown latch to decrease the count.

once CountDownLatch is initialized, we cannot change the value. It can only be decremented using 
countdown(). we cannot reuse countdown latch.
    
    package ConceptOfCountDownLatch;
    
    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.CountDownLatch;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    
    /**
     * Requirement is there are few services in a hospital software
     * which should start before the patients can be registered.
     * Here we can use CountDownLatch to bring up the services
     */
    public class ConceptOfCountDownLatch {
        public static void main(String[] args) {
            ApplicationStartUpUtil.INSTANCE.checkServices();
            System.out.println("Other applications can start....");
        }
    }
    
    abstract class BaseHealthServices implements Runnable {
    
        private String service;
        private boolean serviceUp;
        private CountDownLatch countDownLatch;
    
        @Override
        public void run() {
            try {
                verifyService();
                serviceUp = true;
            } catch (Exception e) {
                serviceUp = false;
            } finally {
                countDownLatch.countDown();
            }
        }
    
        /**
         * @param service
         * @param countDownLatch
         */
        public BaseHealthServices(String service, CountDownLatch countDownLatch) {
            this.service = service;
            this.countDownLatch = countDownLatch;
            this.serviceUp = false;
        }
    
        public boolean isServiceUp() {
            return serviceUp;
        }
    
        protected abstract void verifyService();
    }
    
    
    class NetworkHealthService extends BaseHealthServices {
    
        public NetworkHealthService(CountDownLatch countDownLatch) {
            super("Network Health Services", countDownLatch);
        }
    
        @Override
        protected void verifyService() {
            System.out.println("Network Health Services is starting ....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Network Services are up...");
            }
        }
    }
    
    class CacheHealthService extends BaseHealthServices {
    
        public CacheHealthService(CountDownLatch countDownLatch) {
            super("Cache Health Services", countDownLatch);
        }
    
        @Override
        protected void verifyService() {
            System.out.println("Cache Health Services is starting ....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Cache Health are up...");
            }
        }
    }
    
    enum ApplicationStartUpUtil {
        INSTANCE;
    
        private List<BaseHealthServices> services;
        private CountDownLatch countDownLatch;
    
        public boolean checkServices() {
            countDownLatch = new CountDownLatch(2);
            services = new ArrayList<>();
            services.add(new CacheHealthService(countDownLatch));
            services.add(new NetworkHealthService(countDownLatch));
    
            ExecutorService executorService = Executors.newFixedThreadPool(services.size());
            services.forEach(service -> {
                executorService.submit(service);
            });
    
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            for (BaseHealthServices service : services) {
                if (!service.isServiceUp()) {
                    return false;
                }
            }
            return true;
        }
    }
