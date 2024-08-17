package Aula10_CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountDownLatchMultiThreadTest02 {

    private static volatile int i = 0;

    /*
    A CountDownLatch é inicializada com um contador de 3. Isso significa que a contagem precisa ser
    diminuída três vezes antes que as threads aguardando na latch possam prosseguir.
     */
    private static CountDownLatch latch = new CountDownLatch(3);


    public static void main(String[] args) {

        // Um executor é criado com um pool de 4 threads para agendar e executar tarefas de forma concorrente.
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(4);

        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i * j;

            System.out.println(i + " X " + j + " = " + x );
            latch.countDown();
            /*
            Chama latch.countDown(); Cada vez que isso acontece, a latch se aproxima de liberar as threads que estão
            aguardando. Como o contador inicial era 3, a latch será liberada quando countDown() for chamado três vezes.
             */
        };

        Runnable r2 = () -> {
            await(); // faz a thread esperar até que o contador da latch atinja zero.

            i = new Random().nextInt(1000);
            /*
             Isso ocorre depois que todas as três execuções da tarefa r1 foram concluídas garantindo que i seja
             atualizado somente após a conclusão das multiplicações anteriores.
            */

        };

        Runnable r3 = () -> {
            await(); // faz a thread esperar até que o contador da latch atinja zero.
            latch = new CountDownLatch(3);
            /*
            Reinicializa a CountDownLatch com um novo contador de 3, preparando-se para o próximo ciclo de
            execução das três tarefas.
             */
        };

        Runnable r4 = () -> {
            await(); // faz a thread esperar até que o contador da latch atinja zero.
            System.out.println("Processes finished by: " + Thread.currentThread().getName());
            // exibe a mensagem que o processo foi finalizada e mostra o nome da thread que executou
        };

        // A tarefa r1 é agendada para ser executada a cada 1 segundo, começando imediatamente
        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        // Agendadas para serem executada com um atraso fixo de 1 segundo entre uma execução e outra

        // Após a latch atingir zero, ela gera um novo valor para i.
        executor.scheduleWithFixedDelay(r2, 0, 1, TimeUnit.SECONDS);

        // Responsável por reinicializar a CountDownLatch.
        executor.scheduleWithFixedDelay(r3, 0, 1, TimeUnit.SECONDS);

        // Imprime uma mensagem quando todas as operações forem concluídas.
        executor.scheduleWithFixedDelay(r4, 0, 1, TimeUnit.SECONDS);

    }


    public static void await() {
        /*
         await();: O método await() é um encapsulamento da chamada latch.await(), que faz com que a thread corrente
         espere até que o contador da CountDownLatch chegue a zero. Se a thread for interrompida enquanto espera,
         uma RuntimeException é lançada.
         */
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
