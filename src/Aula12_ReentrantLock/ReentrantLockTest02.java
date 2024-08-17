package Aula12_ReentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockTest02 {

    // Variável compartilhada entre as threads
    private static int i = 0;

    // Lock de leitura e escrita
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        // Cria um executor de threads para gerenciar as tarefas concorrentes
        ExecutorService executor = Executors.newCachedThreadPool();

        // Tarefa de escrita: apenas uma thread pode escrever por vez
        Runnable r1 = () -> {
            // Obtém o lock de escrita
            Lock writeLock = lock.writeLock();
            writeLock.lock(); // Adquire o lock

            // Seção crítica: apenas uma thread pode executar este código
            String name = Thread.currentThread().getName();
            System.out.println(name + " escrevendo " + i);
            i++;
            System.out.println(name + " escrito " + i);

            writeLock.unlock(); // Libera o lock
        };

        // Tarefa de leitura: múltiplas threads podem ler simultaneamente
        Runnable r2 = () -> {
            // Obtém o lock de leitura
            Lock readLock = lock.readLock();
            readLock.lock(); // Adquire o lock

            // Seção crítica: múltiplas threads podem executar este código simultaneamente
            System.out.println("Lendo: " + i);
            System.out.println("Lido: " + i);

            readLock.unlock(); // Libera o lock
        };

        // Executa 6 threads de escrita e 6 threads de leitura
        for (int j = 0; j < 6; j++) {
            executor.execute(r1);
            executor.execute(r2);
        }

        // Encerra o executor de threads
        executor.shutdown();
    }
}