package ej1Hilos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 10; 
        Thread[] hilos = new Thread[n];

        for (int i = 0; i < n; i++) {
            hilos[i] = new MiHilo();
            hilos[i].start();
        }

        for (int i = 0; i < n; i++) {
            try {
                hilos[i].join(); 
            } catch (InterruptedException e) {
                System.out.println("[-] Ejecucion de hilos interrumpida");
            }
        }

        System.out.println("[+] Hilos ejecutados con exito.");
    }
	

}
