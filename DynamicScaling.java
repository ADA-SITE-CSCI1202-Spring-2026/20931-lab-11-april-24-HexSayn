public class DynamicScaling {
    public static void main(String[] args) throws InterruptedException {
        //getting my core count there might be a better way for this
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Cores available: " + cores);

        // run measurement for 1 thread and then ALOT!! of threads
        runTest(1);
        runTest(cores);
    }

    private static void runTest(int threadCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {

            threads[i] = new Thread(() -> {
                double sum = 0;
                for (int j = 0; j < 10_000_000; j++) {
                    sum += Math.pow(j, 3) + j;
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
        
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + threadCount + " threads: " + (end - start) + "ms");
    }
}