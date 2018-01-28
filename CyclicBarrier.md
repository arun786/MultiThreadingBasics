# Cyclic Barrier - 

Cyclic Barrier is similar to CountDownLatch but only difference 
is Cyclic Barrier can be reused even when the count reached zero. 

    package CyclicBarrier;
    
    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.BrokenBarrierException;
    import java.util.concurrent.CyclicBarrier;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    
    public class CyclicBarrierConcept {
        public static void main(String[] args) {
            ApplicationStartUpUtil.INSTANCE.startUp();
            System.out.println("All the services are up...");
            ApplicationStartUpUtil.INSTANCE.shutDownCacheServices();
            ApplicationStartUpUtil.INSTANCE.shutdownNetworkService();
            System.out.println("All the services are down...");
            //ApplicationStartUpUtil.INSTANCE.resetCyclicBarrierToStartTheApplicationAgain();
            ApplicationStartUpUtil.INSTANCE.startUp();
        }
    }
    
    abstract class BaseHealthCheck implements Runnable {
    
        private CyclicBarrier cyclicBarrier;
        private String service;
        private boolean servicesUp;
    
        public BaseHealthCheck(CyclicBarrier cyclicBarrier, String service) {
            this.cyclicBarrier = cyclicBarrier;
            this.service = service;
            this.servicesUp = false;
        }
    
        @Override
        public void run() {
            verifyService();
            servicesUp = true;
        }
    
        protected void snooze() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        abstract void verifyService();
    
        abstract boolean bringDownServices();
    }
    
    class NetworkHealthService extends BaseHealthCheck {
    
        CyclicBarrier cyclicBarrier;
    
        public NetworkHealthService(CyclicBarrier cyclicBarrier) {
            super(cyclicBarrier, "Network Health Service");
            this.cyclicBarrier = cyclicBarrier;
        }
    
        @Override
        void verifyService() {
            System.out.println("Network Health Services are starting...");
            snooze();
            System.out.println("Network Health Services are up....");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    
        @Override
        boolean bringDownServices() {
            System.out.println("Network services are down ...");
            return false;
        }
    }
    
    class CacheHealthService extends BaseHealthCheck {
    
        CyclicBarrier cyclicBarrier;
    
        public CacheHealthService(CyclicBarrier cyclicBarrier) {
            super(cyclicBarrier, "Cache Health Service");
            this.cyclicBarrier = cyclicBarrier;
        }
    
        @Override
        void verifyService() {
            System.out.println("Cache Health Services are starting...");
            snooze();
            System.out.println("Cache Health Services are up....");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    
        @Override
        boolean bringDownServices() {
            System.out.println("Cache services are down...");
            return false;
        }
    }
    
    enum ApplicationStartUpUtil {
    
        INSTANCE;
    
        private CyclicBarrier cyclicBarrier;
        private List<BaseHealthCheck> services;
        BaseHealthCheck networkHealthService;
        BaseHealthCheck cacheHealthService;
    
        public void startUp() {
            cyclicBarrier = new CyclicBarrier(2);
            services = new ArrayList<>();
            networkHealthService = new NetworkHealthService(cyclicBarrier);
            cacheHealthService = new CacheHealthService(cyclicBarrier);
            services.add(networkHealthService);
            services.add(cacheHealthService);
    
            ExecutorService executorService = Executors.newFixedThreadPool(services.size());
            services.forEach(service -> {
                executorService.execute(service);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
    
            executorService.shutdown();
        }
    
        public void shutdownNetworkService() {
            networkHealthService.bringDownServices();
        }
    
        public void shutDownCacheServices() {
            cacheHealthService.bringDownServices();
        }
    
        public void resetCyclicBarrierToStartTheApplicationAgain() {
            cyclicBarrier.reset();
        }
    }
    /**
        output will be
    
            Network Health Services are starting...
            Network Health Services are up....
            Cache Health Services are starting...
            Cache Health Services are up....
            All the services are up...
            Cache services are down...
            Network services are down ...
            All the services are down...
            Network Health Services are starting...
            Network Health Services are up....
            Cache Health Services are starting...
            Cache Health Services are up....
    **/