package Aula16_CompletableFuture;

/*
    O que é um CompletableFuture?
    Um CompletableFuture é uma classe em Java que representa uma tarefa assíncrona que pode ser completada
    com um valor ou uma exceção. É uma ferramenta poderosa para programação assíncrona e concorrente, permitindo
    que você execute tarefas em segundo plano e gerencie seus resultados de forma eficiente.

    Por que usar CompletableFuture?

    - Melhora a responsividade ao executar tarefas em segundo plano, você evita bloquear a thread principal,
      tornando sua aplicação mais responsiva.
    - Simplifica a programação assíncrona: O CompletableFuture oferece uma API intuitiva para criar, combinar
      e encadear tarefas assíncronas.
    - Gerenciar erros: permite tratar exceções que podem ocorrer durante a execução das tarefas.

    Como funciona?
    - Criação: Você cria um CompletableFuture usando métodos como supplyAsync ou runAsync.
    - Execução: A tarefa é executada em um thread separado.
    - Conclusão: Quando a tarefa termina, o CompletableFuture é marcado como completo, com um valor ou uma exceção.
    - Consumo: Você pode obter o resultado da tarefa usando métodos como get, thenApply, thenCompose e outros.
 */


import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest01 {

    public static void main(String[] args) {

        // Cria um CompletableFuture que representa uma tarefa assíncrona que retornará uma String
        CompletableFuture<String> completableFuture = processe();

        // Aplica uma função ao resultado do CompletableFuture, adicionando " CONCLUÍDO" ao resultado
        CompletableFuture<String> thenApply = completableFuture.thenApply(s -> s + " CONCLUÍDO");

        // Consome o resultado do thenApply e imprime na tela
        CompletableFuture<Void> thenAccept = thenApply.thenAccept(System.out::println);

        // Adiciona um pequeno delay para permitir que as tarefas assíncronas terminem e sejam impressas
        sleep();
        sleep();
    }

    private static CompletableFuture<String> processe() {
        // Cria uma tarefa assíncrona que retorna uma String após um pequeno delay
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return "Test";
        });
    }

    public static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
