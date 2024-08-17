package Aula08_ExecutorsScheduled;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsScheduledTest02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // A classe scheduledExecutorService é utilizado para agendar a execução de tarefas

        // .newScheduledThreadPool = cria 3 threads que podem ser agendadas
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        /*
          .scheduleAtFixedRate = Agende uma tarefa para ser executada inicialmente após um atraso (initialDelay) e,
          em seguida, repetidamente com um intervalo fixo (period).

          Caso o tempo de execução da thread seja maior que o (period), o (period) será ignorado, dessa forma
          a próxima thread será executada assim que a anterior for terminada. Nesse exemplo cada execução acontecerá
          a cada 3 segundos.
         */
        executor.scheduleAtFixedRate(new Task(), 0, 2, TimeUnit.SECONDS);

        /*
            .scheduleWithFixedDelay = O intervalo entre uma execução e outra sempre é respeitado, independente do
            tempo de execução da thread, nesse exemplo cada execução acontecerá a cada 5 segundos.
         */
        //executor.scheduleWithFixedDelay(new Task(), 1, 2, TimeUnit.SECONDS);
    }

    public static class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            LocalDateTime localDateTime = LocalDateTime.now();
            String name = Thread.currentThread().getName();
            System.out.println(name + " | Executed at | " + localDateTime);
        }
    }
}
