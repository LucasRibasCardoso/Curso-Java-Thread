package Aula09_CyclicBarrier;

import java.util.concurrent.*;

public class CyclicBarrierTest02 {

    private static BlockingQueue<Double> results = new LinkedBlockingQueue<>();
    private static ExecutorService executor;
    private static Runnable r1;
    private static Runnable r2;
    private static Runnable r3;


    public static void main(String[] args) {

        /*
         Esse código faz o mesmo que o exemplo Test01, porém agora a execução é cíclica, ou seja, quando a
         finalização é executada, o método restart é chamado, dessa forma a aplicação recomeça novamente.
         */




        Runnable finalization = () -> {
            double finalResult =  0;

            finalResult += results.poll();
            finalResult += results.poll();
            finalResult += results.poll();

            System.out.println("Finished processing! " +
                    "Final Result: " + finalResult);

            System.out.println("------------RESTART------------");
            restart();
        };


        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,finalization);

        executor = Executors.newFixedThreadPool(3);

        r1 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(432d * 3d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };

        r2 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };

        r3 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(45d * 127d / 12d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };

        restart();
    }

    public static void await (CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    public static void restart() {
        try {
            Thread.sleep(1000); // thread espera 1 segundo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.submit(r1); // executa a thread 1
        executor.submit(r2); // executa a thread 2
        executor.submit(r3); // executa a thread 3
    }
}
