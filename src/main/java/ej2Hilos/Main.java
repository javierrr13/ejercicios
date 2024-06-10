package ej2Hilos;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		    Semaphore ticSemaphore = new Semaphore(1);
	        Semaphore tacSemaphore = new Semaphore(0);

	        MiHilo ticThread = new MiHilo("TIC", 1000, ticSemaphore, tacSemaphore);
	        MiHilo tacThread = new MiHilo("TAC", 1000, tacSemaphore, ticSemaphore);

	        ticThread.start();
	        tacThread.start();
	}

}
