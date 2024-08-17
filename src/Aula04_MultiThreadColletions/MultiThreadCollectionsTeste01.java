package Aula04_MultiThreadColletions;


import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiThreadCollectionsTeste01 {

    /*
     Classes Thread-Safe são classes de coleções, são utilizadas para trabalhar de forma mais fácil com multiThread
     e evitando a concorrência entre as thread.

     CopyOnWriteArrayList = Usada para Listas, boa para leitura de diversas threads, péssima para inserção e remoção.

     ConcurrentHashMap = Usada para Map, permite que várias threads acessem e modifiquem o mapa simultaneamente
                         sem causar concorrência.

     LinkedBlockingQueue = Usada para Filas, bastante utilizada em casos, por exemplo, onde uma thread vai removendo
                           elementos e outra adicionando.
    */

    private static List<String> list = new CopyOnWriteArrayList<>();
    // private static Map<Integer, String> map = new ConcurrentHashMap<>();
    // private static Queue<Integer> queue = new LinkedBlockingQueue<>();

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
        // System.out.println(map);
        // System.out.println(queue);
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            list.add("Test");
            // map.put(new Random().nextInt(), "Test");
            // queue.add(1);

            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " inserted");
        }
    }
}

