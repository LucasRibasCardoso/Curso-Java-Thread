package Aula13_SynchronousQueue;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest01 {
    //                                            Fila
    private static final SynchronousQueue<String> QUEUE = new SynchronousQueue<>();
    
    public static void main(String[] args) {    

        ExecutorService executor = Executors.newCachedThreadPool();
        
        Runnable r1 = () -> {
            put();
            System.out.println("writing in queue");
        };

        Runnable r2 = () -> {
            String msg = take();
            System.out.println("reading in queue -> " + msg);
        };

        executor.execute(r1);
        executor.execute(r2);

        executor.shutdown();
        
    }
    
    public static String take () {
        try {
            return QUEUE.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void put () {
        try {
            QUEUE.put("Test");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
