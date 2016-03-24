package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleThreadPool {

    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExecutorService executor = Executors.newWorkStealingPool();
        for (int i = 0; i < 20; i++) {
            int temp = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " - " + temp);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
//            Runnable worker = new WorkerThread("" + i);
//            executor.execute(worker);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finished all threads");
    }

}
