package Aula05_AtomicsClasses;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicsClassesTeste01 {

    /*
    Fornece uma maneira atômica de manipular um valor int. Suporta operações como incremento,
    decremento, comparação e troca, e atualização condicional.
     */
    private static AtomicInteger i = new AtomicInteger(0);

    /*
    Fornece operações atômicas para um valor boolean. Útil para flags de controle que precisam
    ser atualizadas de forma segura entre várias threads.
     */
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    /*
    Permite manipular referências de objetos de forma atômica. Muito útil quando você precisa
    garantir que um objeto seja atualizado ou substituído de maneira segura.
     */
    private static AtomicReference<String> atomicReference = new AtomicReference<>("test");


    public static void main(String[] args) {

        MyRunnable myRunnable = new MyRunnable();

        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);

        t0.start();
        t1.start();
        t2.start();
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();

            // System.out.println(name + " : " + i.incrementAndGet());
            // System.out.println(name + " : " + atomicBoolean.compareAndExchange(false, true));

            atomicReference.set("Novo valor");
            System.out.println(name + " : " + atomicReference.compareAndSet("Novo valor", "Lucas"));
        }
    }
}
