package Aula15_RaceCondition;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JAVA MULTITHREAD - Produtor-Consumidor
 * 2) Região crítica e Exclusão mútua.
 *
 * This code implements a producer-consumer pattern using threads, but with a lock to prevent deadlocks.
 *
 * Deadlock occurs when two or more threads are blocked indefinitely, each waiting for a resource held by another thread.
 * In this example, the producer and consumer threads compete for access to the shared `FILA` (a blocking queue).
 *
 * The `ReentrantLock` is used to ensure mutual exclusion, meaning only one thread can access the critical section (the code that modifies the `FILA`) at a time.
 * This prevents the deadlock scenario where the producer and consumer are both waiting for each other to release the `FILA`.
 */

public class ProducerAndConsumerTest02 {

    private static final BlockingQueue<Integer> FILA = new LinkedBlockingQueue<>(5); // Shared blocking queue for producer and consumer
    private static boolean produzindo = true; // Flag to indicate if the producer is active
    private static boolean consumindo = true; // Flag to indicate if the consumer is active
    private static Lock LOCK = new ReentrantLock(); // Lock to ensure mutual exclusion for accessing the shared queue

    public static void main(String[] args) {

        Thread produtor = new Thread(() -> {
            while (true) {
                try {
                    simularProcessamento(); // Simulating a time-consuming process (random delay)

                    if (produzindo) { // Producer is active
                        LOCK.lock(); // Acquire the lock before accessing the shared queue

                        System.out.println("Produzindo");
                        int numero = new Random().nextInt(1000); // Generate a random number
                        FILA.add(numero); // Add the number to the shared queue

                        if (FILA.size() == 5) { // If the queue is full
                            System.out.println("Pausando produtor");
                            produzindo = false; // Pause the producer
                        }

                        if (FILA.size() == 1) { // If the queue has one element
                            System.out.println("Iniciando consumidor");
                            consumindo = true; // Start the consumer
                        }

                        LOCK.unlock(); // Release the lock after accessing the shared queue
                    } else {
                        System.out.println("---PRODUTOR DORMINDO---"); // Producer is inactive
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                ;
            }
        });


        Thread consumidor = new Thread(() -> {
            while (true) {
                try {
                    simularProcessamento(); // Simulating a time-consuming process (random delay)

                    if (consumindo) { // Consumer is active

                        LOCK.lock(); // Acquire the lock before accessing the shared queue
                        System.out.println("Consumindo");
                        Optional<Integer> numero = FILA.stream().findFirst(); // Get the first element from the queue
                        numero.ifPresent(FILA::remove); // Remove the element from the queue

                        if (FILA.size() == 0) { // If the queue is empty
                            System.out.println("Pausando consumidor");
                            consumindo = false; // Pause the consumer
                        }

                        if (FILA.size() == 4) { // If the queue has 4 elements
                            System.out.println("Iniciando produtor");
                            produzindo = true; // Start the producer
                        }
                        LOCK.unlock(); // Release the lock after accessing the shared queue

                    } else {
                        System.out.println("---CONSUMIDOR DORMINDO---"); // Consumer is inactive
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Janelas.monitore(() -> String.valueOf(FILA.size())); // Monitor the size of the shared queue

        produtor.start(); // Start the producer thread
        consumidor.start(); // Start the consumer thread
    }

    public static void simularProcessamento() {
        int tempo = new Random().nextInt(40);
        try {
            Thread.sleep(tempo); // Simulate random delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}