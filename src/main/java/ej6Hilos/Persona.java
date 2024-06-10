package ej6Hilos;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Persona implements Runnable {
    private int id;
    private Semaphore banco;

    public Persona(int id, Semaphore banco) {
        this.id = id;
        this.banco = banco;
    }

    @Override
    public void run() {
        try {
            // Pasear por el parque
            long tiempoPaseo = 1000 + new Random().nextInt(2001);
            System.out.println( Thread.currentThread().getName()  +  " Persona " + id + " está paseando por " + tiempoPaseo + " ms.");
            Thread.sleep(tiempoPaseo);

            // Intentar sentarse en el banco
            System.out.println( Thread.currentThread().getName()+ " Persona " + id + " está esperando una plaza en el banco.");
            banco.acquire();
            System.out.println(Thread.currentThread().getName()+ " Persona " + id + " se ha sentado en el banco.");

            // Descansar en el banco
            long tiempoDescanso = 100 + new Random().nextInt(601);
            System.out.println(Thread.currentThread().getName()+" Persona " + id + " está descansando por " + tiempoDescanso + " ms.");
            Thread.sleep(tiempoDescanso);

            // Levantarse del banco
            System.out.println(Thread.currentThread().getName()+" Persona " + id + " se ha levantado del banco.");
            banco.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}