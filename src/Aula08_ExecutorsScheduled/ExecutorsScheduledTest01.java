package Aula08_ExecutorsScheduled;

import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsScheduledTest01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // A classe scheduledExecutorService é utilizado para agendar a execução de tarefas

        // .newScheduledThreadPool = cria 3 threads que podem ser agendadas
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // .schedule = agenda a execução da tarefa conforme o delay passado
        Future<String> future = executor.schedule(new Task(), 2, TimeUnit.SECONDS);

        System.out.println(future.get());

        executor.shutdown();
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
