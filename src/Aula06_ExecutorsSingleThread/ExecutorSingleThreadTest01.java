package Aula06_ExecutorsSingleThread;

import java.util.concurrent.*;

public class ExecutorSingleThreadTest01 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = null;

        try {
            // Cria um ExecutorService com um único thread de execução
            executor = Executors.newSingleThreadExecutor();

            // O ExecutorService executa a primeira tarefa em uma nova thread
            executor.execute(new Task()); // Executa a tarefa na thread criada pelo executor

            // O ExecutorService executa a segunda tarefa na mesma thread, pois ele foi configurado com um único thread
            executor.execute(new Task()); // Executa outra tarefa na mesma thread

            // O ExecutorService executa a terceira tarefa na mesma thread
            executor.execute(new Task()); // Executa mais uma tarefa na mesma thread

            // O método submit() envia uma nova tarefa para execução e retorna um Future que representa a tarefa em andamento
            Future<?> future = executor.submit(new Task());

            // Verifica se a tarefa associada ao Future foi concluída. Imprime "false" porque a tarefa provavelmente ainda está em execução
            System.out.println(future.isDone());

            // Encerra o ExecutorService, não aceita novas tarefas, mas permite que as tarefas já enviadas sejam concluídas
            executor.shutdown();

            // Aguarda até 1 segundo para que todas as tarefas sejam concluídas. O método awaitTermination retorna verdadeiro se o executor for encerrado dentro desse tempo
            executor.awaitTermination(1, TimeUnit.SECONDS);

            // Verifica novamente se a tarefa associada ao Future foi concluída. Agora pode imprimir "true" se a tarefa tiver sido concluída dentro do tempo de espera
            System.out.println(future.isDone());

        } catch (Exception e) {
            throw e;
        }
        finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    public static class Task implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();

            System.out.println("Executing task by thread : " + name);
        }
    }
}
