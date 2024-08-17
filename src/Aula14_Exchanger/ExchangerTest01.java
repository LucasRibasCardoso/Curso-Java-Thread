package Aula14_Exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest01 {

    /*
     O Exchanger é útil para cenários específicos de troca de dados entre duas threads.

     Para cenários mais complexos com múltiplas threads produtoras e consumidoras, outras estruturas como
     BlockingQueue podem ser mais adequadas.

     É importante garantir que apenas duas threads utilizem o mesmo objeto Exchanger para evitar erros de sincronização.
     */

    private static final Exchanger<String> EXCHANGER = new Exchanger<>(); // Cria um objeto Exchanger para troca de Strings

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool(); // Cria um ExecutorService para gerenciar as threads

        // Tarefa 1 (envia mensagem)
        Runnable task1 = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " take this -> ");
            String messageSended = "Take this!!!"; // Mensagem a ser enviada

            // Troca a mensagem usando o Exchanger e armazena a mensagem recebida
            String messageReturned = exchange(messageSended);

            System.out.println(name + " -> " + messageReturned); // Exibe a mensagem recebida
        };

        // Tarefa 2 (recebe mensagem)
        Runnable task2 = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " thanks");
            String messageSended = "Thanks!!!"; // Mensagem a ser enviada

            // Troca a mensagem usando o Exchanger e armazena a mensagem recebida
            String messageReturned = exchange(messageSended);

            System.out.println(name + " -> " + messageReturned); // Exibe a mensagem recebida
        };

        executor.execute(task1); // Executa a tarefa 1
        executor.execute(task2); // Executa a tarefa 2

        executor.shutdown(); // Encerra o ExecutorService
    }

    public static String exchange(String message) {
        try {
            return EXCHANGER.exchange(message); // Troca a mensagem usando o Exchanger
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // Trata a exceção InterruptedException
        }
    }
}