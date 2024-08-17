package Aula11_Semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest01 {

    /*
      Um Semaphore é um contador que gerencia permissões. Ele pode ser usado para controlar o acesso a um
      número limitado de recursos. A ideia básica é permitir que um número fixo de threads acesse um recurso
      simultaneamente, enquanto outras threads devem esperar até que uma permissão seja liberada.
     */

    // Cria um Semaphore com 3 permissões, permitindo que no máximo 3 threads acessem o recurso simultaneamente
    private static Semaphore SEMAPHORE = new Semaphore(3);

    public static void main(String[] args) {

        // Cria um ExecutorService com um pool de threads sob demanda
        ExecutorService executor = Executors.newCachedThreadPool();

        // Define uma tarefa que será executada por várias threads
        Runnable r1 = () -> {
            // Obtém o nome da thread corrente
            String name = Thread.currentThread().getName();
            // Gera um número aleatório para simular um identificador de usuário
            int user = new Random().nextInt(10000);

            // Tenta adquirir uma permissão do Semaphore
            acquire();
            // Imprime o número do usuário, uma mensagem de teste e o nome da thread corrente
            System.out.println("User: " + user + " | Test | " + name);
            // Simula algum trabalho com uma pausa
            sleep();
            // Libera a permissão de volta para o Semaphore
            SEMAPHORE.release();
        };

        // Submete 500 tarefas para o executor, cada uma executando a tarefa definida em r1
        for (int i = 0; i < 500; i++) {
            executor.execute(r1);
        }

        // Encerrar o ExecutorService após a conclusão das tarefas
        executor.shutdown();
    }

    // Método para adquirir uma permissão do Semaphore
    public static void acquire() {
        try {
            // Bloqueia a thread até que uma permissão esteja disponível
            SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            // Lança uma RuntimeException se a thread for interrompida enquanto aguarda
            throw new RuntimeException(e);
        }
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
}
