package Aula15_RaceCondition;

import java.util.Random;
import java.util.concurrent.*;

/*
 * JAVA MULTI THREAD - Produtor-Consumidor
 *
 * A BlockingQueue é uma interface que representa uma fila que pode ser bloqueada.
 * Isso significa que as operações de inserção (put) e remoção (take) podem ser bloqueadas se a fila estiver cheia ou vazia,
 * respectivamente.
 *
 * A BlockingQueue é uma solução ideal para o problema do produtor-consumidor, pois garante que o produtor não
 * tente inserir elementos em uma fila cheia e o consumidor não tente remover elementos de uma fila vazia.
 *
 * O código cria um produtor e um consumidor que compartilham uma BlockingQueue. O produtor adiciona elementos à fila
 * e o consumidor remove elementos da fila.
 *
 * O produtor e o consumidor são executados em threads separadas, e a BlockingQueue garante que as threads sejam
 * sincronizadas corretamente.
 *
 */



public class ProducerAndConsumerTest03 {

    // Fila compartilhada com capacidade de 5 elementos, garantindo que o produtor espere se a fila estiver cheia
    // e o consumidor espere se a fila estiver vazia.
    private static final BlockingQueue<Integer> FILA = new LinkedBlockingQueue<>(5);

    public static void main(String[] args) {
        // Cria um executor de tarefas programadas para gerenciar as threads do produtor e consumidor.
        // O bloco try-with-resources garante que o executor seja fechado automaticamente.

        Runnable produtor = () -> {
            // Enquanto a thread não for interrompida, continue produzindo.
            while (!Thread.currentThread().isInterrupted()) {
                // Simula um processamento antes de produzir um novo item.
                simularProcessamento();
                try {
                    System.out.println("Produzindo");
                    // Adiciona um número aleatório à fila. A operação put() é bloqueada se a fila estiver cheia.
                    FILA.put(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    // Se a thread for interrompida, interrompe a execução do produtor.
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        Runnable consumidor = () -> {
            // Enquanto a thread não for interrompida, continue consumindo.
            while (!Thread.currentThread().isInterrupted()) {
                // Simula um processamento antes de consumir um item.
                simularProcessamento();
                try {
                    System.out.println("Consumindo");
                    // Remove e consome um elemento da fila. A operação take() é bloqueada se a fila estiver vazia.
                    FILA.take();
                } catch (InterruptedException e) {
                    // Se a thread for interrompida, interrompe a execução do consumidor.
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        Janelas.monitore(() -> String.valueOf(FILA.size()));

        // Agenda a execução do produtor e consumidor com intervalos fixos.
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        executor.scheduleWithFixedDelay(produtor, 0, 10, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(consumidor, 0, 10, TimeUnit.MILLISECONDS);

    }

    // Simula um processamento aleatório, representando o tempo de execução de uma tarefa.
    public static void simularProcessamento() {
        int tempo = new Random().nextInt(40);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}