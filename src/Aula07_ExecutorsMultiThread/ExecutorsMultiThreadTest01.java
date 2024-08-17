package Aula07_ExecutorsMultiThread;

import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsMultiThreadTest01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = null;

        try {
            /*
             Cria um executor com 4 threads disponíveis, se pedirmos para executar três tarefas,
             somente três thread serão utilizadas, pode ser util em casos que você preciso definir exatamente
             quantas threads utilizar
             */
            executor = Executors.newFixedThreadPool(4);

            Future<String> f1 = executor.submit(new Task());
            Future<String> f2 = executor.submit(new Task());
            Future<String> f3 = executor.submit(new Task());

            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());

            executor.shutdown();

        } catch (Exception e) {
            throw e;
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }

    }

    public static class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            int randomInt = new Random().nextInt(100);
            return name + " | Teste | " + randomInt;
        }
    }


}
