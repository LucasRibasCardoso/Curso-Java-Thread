package Aula02_Synchronized;


public class SynchronizedTeste01 {

    private static int i = 0;

    public static void main(String[] args) {

        MeuRunnable runnable = new MeuRunnable();

        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        Thread t4 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static class MeuRunnable implements Runnable {

        /*
        O método "run" possuí um bloco synchronized em um trecho que caso as thread acessem simultaneamente
        poderia haver problema por conta das operações aritméticas, então o bloco synchronized faz com que
        cada thread só pode executar aquele trecho por vez, dessa forma evitando a concorrência, porém
        mantendo o paralelismo.
         */

        @Override
        public void run() {
            int j;
            synchronized (this) {
                i++;
                j = i * 2;
            }

            double jElevadoA100 = Math.pow(j, 100);
            double raizQuadradaDeJ = Math.sqrt(jElevadoA100);
            System.out.println(raizQuadradaDeJ);

        }
    }

}


