package Aula07_ExecutorsMultiThread;

import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsMultiThreadTest02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = null;

        try {
            /*
               O newCachedThreadPool cria e destrói as threads dinamicamente conforme for necessitando, é util,
               pois não precisa definir um valor fixo, porém tome cuidado porque pode criar muitas threads caso
               haja muitas tarefas, então utilize esse método somente quando forem poucas tarefas.

               Nesse exemplo podemos notar que a maioria das vezes somente duas threads são criadas para executar as
               três tarefas, isso porque quando ele vai executar a terceira tarefa, a primeira ja foi terminada, então
               invés de criar a terceira thread, ele utiliza a primeira thread para executar a terceira tarefa
             */
            executor = Executors.newCachedThreadPool();

            Future<String> f1 = executor.submit(new Task());
            System.out.println(f1.get());

            Future<String> f2 = executor.submit(new Task());
            Future<String> f3 = executor.submit(new Task());

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
