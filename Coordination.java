class SharedResource {
    private int value;
    private boolean bChanged = false;
    
    // NO exceptions to be had
    public synchronized void set(int val) {
        this.value = val;
        this.bChanged = true;
        notify(); 
        System.out.println("Produced: " + val);
    }

    public synchronized int get() throws InterruptedException {
        // waitin...
        while (!bChanged) {
            wait();
        }
        bChanged = false;
        return value;
    }
}

public class Coordination {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // consumer
        new Thread(() -> {
            try {
                int val = resource.get();
                System.out.println("Consumed: " + val);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();

        // producer
        new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            resource.set(42);
        }).start();
    }
}