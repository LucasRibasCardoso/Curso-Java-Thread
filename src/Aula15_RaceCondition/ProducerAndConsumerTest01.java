package Aula15_RaceCondition;

/**
 * JAVA MULTI THREAD - Produtor-Consumidor
 * Condição de corrida e Deadlock.

 * Deadlock em threads ocorre quando duas ou mais threads ficam esperando indefinidamente uma pela outra para
 * liberar um recurso. Cada thread está bloqueada, aguardando que a outra libere o recurso que ela precisa,
 * mas como nenhuma das duas pode avançar, o sistema fica travado.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ProducerAndConsumerTest01 {

    private static final List<Integer> LISTA = new ArrayList<>(5); // Shared list for producer and consumer
    private static boolean produzindo = true; // Flag to indicate if the producer is active
    private static boolean consumindo = true; // Flag to indicate if the consumer is active

    public static void main(String[] args) {

        Thread produtor = new Thread(() -> {
            while (true) {
                try {
                    simularProcessamento(); // Simulating a time-consuming process (Thread Sleep (3s))

                    if (produzindo) { // Producer is active
                        System.out.println("Produzindo");
                        int numero = new Random().nextInt(1000); // Generate a random number
                        LISTA.add(numero); // Add the number to the shared list

                        if (LISTA.size() == 5) { // If the list is full
                            System.out.println("Pausando produtor");
                            produzindo = false; // Pause the producer
                        }

                        if (LISTA.size() == 1) { // If the list has one element
                            System.out.println("Iniciando consumidor");
                            consumindo = true; // Start the consumer
                        }
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
                    simularProcessamento(); // Simulating a time-consuming process (Thread Sleep (3s))

                    if (consumindo) { // Consumer is active
                        System.out.println("Consumindo");
                        Optional<Integer> numero = LISTA.stream().findFirst(); // Get the first element from the list
                        numero.ifPresent(LISTA::remove); // Remove the element from the list

                        if (LISTA.size() == 0) { // If the list is empty
                            System.out.println("Pausando consumidor");
                            consumindo = false; // Pause the consumer
                        }

                        if (LISTA.size() == 4) { // If the list has 4 elements
                            System.out.println("Iniciando produtor");
                            produzindo = true; // Start the producer
                        }
                    } else {
                        System.out.println("---CONSUMIDOR DORMINDO---"); // Consumer is inactive
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Janelas.monitore(() -> String.valueOf(LISTA.size()));

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