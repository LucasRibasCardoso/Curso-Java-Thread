package Aula12_ReentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest01 {

    private static int i = -1;

    /*
     ReentrantLock é uma ferramenta poderosa para sincronizar threads em Java.
     Oferece mais flexibilidade e controle do que synchronized.
     É ideal para cenários onde a sincronização precisa ser mais complexa ou quando é necessário um controle mais
     preciso sobre a aquisição e liberação do lock.
     */
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable r1 = () -> {
            /*
             lock.lock():
             Adquire o lock (trava) associado ao objeto lock.
             Isso significa que apenas uma thread por vez pode executar o código dentro deste bloco.
             Se outra thread tentar adquirir o lock enquanto ele já estiver ocupado, ela será bloqueada
             até que o lock seja liberado.
            */
            lock.lock();

            String name = Thread.currentThread().getName();
            i ++;
            System.out.println(name + " lendo e incrementando " + i);

            /*
             lock.unlock():
             Libera o lock, permitindo que outras threads que estão esperando possam adquiri-lo.
             É crucial chamar unlock() para evitar deadlocks, onde threads ficam bloqueadas indefinidamente esperando por locks que nunca serão liberados.
            */
            lock.unlock();
        };

        for (int j = 0; j < 6; j++) {
            executor.execute(r1);
        }

        executor.shutdown();
    }
}
