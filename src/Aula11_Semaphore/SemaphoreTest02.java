package Aula11_Semaphore;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest02 {

    /*
      Um Semaphore é um contador que gerencia permissões. Ele pode ser usado para controlar o acesso a um
      número limitado de recursos. A ideia básica é permitir que um número fixo de threads acesse um recurso
      simultaneamente, enquanto outras threads devem esperar até que uma permissão seja liberada.
     */

    // Cria um Semaphore com 3 permissões, permitindo que no máximo 3 threads acessem o recurso simultaneamente
    private static Semaphore SEMAPHORE = new Semaphore(3);


    private static AtomicInteger quantityOfTasksWaiting = new AtomicInteger(0);

    public static void main(String[] args) {

        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(501);

        // Define uma tarefa que será executada por várias threads
        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();

            int user = new Random().nextInt(10000);

            // Tenta adquirir uma permissão do Semaphore
            boolean isReleased = false;

            // caso não consiga, incrementa 1 na quantidade de task em espera
            quantityOfTasksWaiting.incrementAndGet();

            // enquanto não consegue uma vaga, ele fica tentando
            while (!isReleased) {
                isReleased = tryAcquire();
            }

            // caso consiga, decrementa 1 na quantidade de task em espera
            quantityOfTasksWaiting.decrementAndGet();

            System.out.println("User: " + user + " | Test | " + name);

            // Simula algum trabalho com uma pausa
            sleep();

            // Libera a permissão de volta para o Semaphore
            SEMAPHORE.release();
        };


        Runnable r2 = () -> {
            // Exibe a quantidade de usuários (tarefas) que estão esperando permissão do semáforo
            System.out.println("User waiting: " + quantityOfTasksWaiting.get());
        };


        // Submete 500 tarefas para o executor, cada uma executando a tarefa definida em r1
        for (int i = 0; i < 500; i++) {
            executor.execute(r1);
        }

        executor.scheduleWithFixedDelay(r2, 0, 1000, TimeUnit.MILLISECONDS);

    }

    // Método para simular um atraso (trabalho) de 3 segundos
    public static void sleep() {
        try {
            // Pausa a execução da thread por 3 segundos
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Lança uma RuntimeException se a thread for interrompida enquanto dorme
            throw new RuntimeException(e);
        }
    }

    public static boolean tryAcquire() {
        try {
            return SEMAPHORE.tryAcquire(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
