package Aula10_CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountDownLatchSingleThreadTest01 {

    /*
     volatile = garante que as leituras e gravações da variável sejam feitas diretamente na memória principal
     e não em caches de thread individuais. Dessa forma sempre utiliza o dado mais recente atribuído a essa variável
     */
    private static volatile int i = 0;

    /*
     CountDownLatch = permite que uma ou mais threads aguardem até que um conjunto de operações realizadas
     por outras threads seja concluído. O número 3 indica quantas vezes countDown() deve ser chamado antes
     que await() permita que as threads bloqueadas prossigam.é usado para coordenar a conclusão de múltiplas threads
     antes de permitir que a thread principal ou outras threads continuem.
     */
    private static CountDownLatch latch = new CountDownLatch(3);


    public static void main(String[] args) {

        // cria 3 threads que podem ser agendadas
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(3);

        /*
         Esse código cria uma tarefa Runnable que realiza uma operação simples de multiplicação aleatória e,
         em seguida, sinaliza a conclusão da tarefa, decrementando uma CountDownLatch
         */
        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i * j;

            System.out.println(i + " X " + j + " = " + x );
            latch.countDown();
        };

        // executa a tarefa com intervalos de tempo fixos entre o início de cada execução.
        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        while (true) {
            await();
            i = new Random().nextInt(100);
            latch = new CountDownLatch(3); // recria o objeto latch, pois não pode ser usar o mesmo várias vezes
        }
    }


    public static void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
