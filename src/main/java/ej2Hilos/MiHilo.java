package ej2Hilos;

import java.util.concurrent.Semaphore;

public class MiHilo extends Thread {
	
	private String tictacm;
    private long intervalo;
    private Semaphore currentSemaphore;
    private Semaphore nextSemaphore;

    public MiHilo(String mess, long intervalo, Semaphore currentSemaphore, Semaphore nextSemaphore) {
        this.tictacm = mess;
        this.intervalo = intervalo;
        this.currentSemaphore = currentSemaphore;
        this.nextSemaphore = nextSemaphore;
    }

    public void run() {
        try {
            while (true) {
                currentSemaphore.acquire();
                System.out.println(tictacm);
                Thread.sleep(intervalo);
                nextSemaphore.release();
            }
        } catch (InterruptedException e) {
            System.out.println(tictacm + " thread was interrupted.");
        }
    }
}
