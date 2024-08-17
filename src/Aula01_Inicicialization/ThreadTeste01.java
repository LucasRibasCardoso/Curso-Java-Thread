package Aula01_Inicicialization;

public class ThreadTeste01 {
    public static void main(String[] args) {

        Runnable meuRunnable = new MeuRunnable();

        // Imprime o nome da Thread atual (Main)
        System.out.println(Thread.currentThread().getName());

        // Cria uma thread a partir da classe meuRunnable que implementa a interface Runnable
        Thread thread0 = new Thread(meuRunnable);

        // Cria uma thread com lambda, dessa maneira não precisa criar uma classe para implementar a interface Runnable
        Thread thread1 = new Thread(() -> System.out.println(
                Thread.currentThread().getName()
        ));

        // Cria uma thread a partir da classe meuRunnable que implementa a interface Runnable
        Thread thread2 = new Thread(meuRunnable);

        /*
         Pede para a JVM executar as thread, a ordem de execução pode variar conforme o processador achar melhor,
         cada thread será executada de forma independente das outras.

         Uma thread quando inicializada "thread.start()" não pode ser inicializada novamente, caso tentar,
         será retornado uma exception
         */
        thread0.start();
        thread1.start();
        thread2.start();
    }
}
