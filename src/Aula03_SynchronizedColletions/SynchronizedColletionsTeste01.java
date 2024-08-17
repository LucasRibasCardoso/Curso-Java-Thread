package Aula03_SynchronizedColletions;


import java.util.*;

public class SynchronizedColletionsTeste01 {

    /*
     Para termos certeza que as operações em coleções possam ser feitas com sucesso e integridade,
     precisamos sincronizar a coleção, seja List, Map, Set..., para fazermos isso, podemos utilizar o
     Collections.synchronizedXXX, dessa forma as thread respeitaram essa barreria e as operações serão feitas
     da maneira correta.
     */

    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    /*
     Exemplos:
     private static Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
     private static Map<Integer, String> map = Collections.synchronizedMap(new HashMap<>());
    */

    public static void main(String[] args) throws InterruptedException {

        MyRunnable myRunnable = new MyRunnable();

        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);

        t0.start();
        t1.start();
        t2.start();

        Thread.sleep(500);
        System.out.println(list);

    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            list.add("Lucas");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inserted in list");
        }
    }
}
