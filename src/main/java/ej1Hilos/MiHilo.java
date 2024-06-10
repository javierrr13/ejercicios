package ej1Hilos;

public class MiHilo extends Thread {
	private static int TiempoEspera;
	
	public MiHilo() {		
		this.TiempoEspera= 3333;
	}
	
	public void run() {
		 System.out.println("El " + Thread.currentThread().getName() + " va a dormir " + TiempoEspera + " ms");
			try {
	            Thread.sleep(TiempoEspera);
	        } catch (InterruptedException e) {
	            System.out.println( Thread.currentThread().getName() + " ha sido interrumpido ");
	        }
	        System.out.println(Thread.currentThread().getName() + " Finalizo .");
	}
}
