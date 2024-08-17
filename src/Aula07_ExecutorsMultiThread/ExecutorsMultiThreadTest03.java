package Aula07_ExecutorsMultiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsMultiThreadTest03 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = null;

        try {

            executor = Executors.newCachedThreadPool();

            // Cria uma lista de tasks
            List<Task> listTasks = new ArrayList<>();

            // Adiciona 100 tarefas a lista de tasks
            for (int i = 0; i < 100; i++ ) {
                listTasks.add(new Task());
            }

            // invokeAll é utiliza para executar uma lista de tarefas a serem feitas por diversas threads
            List<Future<String>> listFutureTasks = executor.invokeAll(listTasks);

            // Imprime o resultado de cada thread, note que nunca é criada a mesma quantidade de threads e tarefas
            for (Future<String> future : listFutureTasks) {
                System.out.println(future.get());
            }

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
