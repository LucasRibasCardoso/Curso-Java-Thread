package Aula09_CyclicBarrier;

import java.util.concurrent.*;

public class CyclicBarrierTest01 {

    // cria uma lista para armazenar os resultados de cada parte da conta
    private static BlockingQueue<Double> results = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        /*
         O cyclicBarrier é utilizado para criarmos uma barreira para poder sincronizar threads, por exemplo,
         executá-las até certo ponto, e quando atingirem aquele ponto, aí, sim, continuam.

         Para isso usamos a classe CyclicBarrier, onde devemos passar o parâmetro "parties" que será a barreira,
         cada vez que uma thread executar o método await(), será incrementado 1 no contador, quando atingir o
         número da barreira (nesse caso 3), as threads serão liberadas para

         Nesse exemplo vamos resolver uma equação matemática dividindo ela em 3 partes, cada parte ficará
         com uma thread, quando cada parte foi resolvida, aí juntaremos os resultados em uma thread de finalização
         que exibirá o resultado final de todas as partes juntas.

         1 thread  2 thread  3 thread
         (432*3) + (3^14) + (45*127/12) = ?
         */

        /*
         thread de finalização, será executada somente depois da barreira ser atingida, ou seja, quando as outras 3
         threads estiverem executadas, essa thread de finalização será executada pela classe CyclicBarrier para
         finalizar as operações.
         */
        Runnable finalization = () -> {
            double finalResult =  0;

            finalResult += results.poll();
            finalResult += results.poll();
            finalResult += results.poll();

            System.out.println(Thread.currentThread().getName() +
                    " -> Final Result: " + finalResult);
        };


        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,finalization);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // thread 1 -> executa a primeira parte da conta
        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(432d * 3d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName()); // Só será executado após a thread finalization
        };

        // thread 2 -> executa a segunda parte da conta
        Runnable r2 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName()); // Só será executado após a thread finalization
        };

        // // thread 3 -> executa a terceira parte da conta
        Runnable r3 = () -> {
            System.out.println(Thread.currentThread().getName());
            results.add(45d * 127d / 12d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName()); // Só será executado após a thread finalization
        };

        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);

        executor.shutdown();
    }

    /*
        Esse método await é uma função estática personalizada que encapsula a chamada ao método await() do
        objeto CyclicBarrier, tratando as exceções que podem ser lançadas durante o processo.

        cyclicBarrier.await() = Esse método faz com que a thread que o chamou espere até que o número de
        threads necessárias (3) atinja a barreira.
     */
    public static void await (CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
