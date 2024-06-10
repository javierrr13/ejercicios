package ej6Hilos;


import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de plazas del banco: ");
        int plazas = scanner.nextInt();
        
        System.out.print("Ingrese el número de personas en el parque: ");
        int personas = scanner.nextInt();

        Semaphore banco = new Semaphore(plazas);

        for (int i = 1; i <= personas; i++) {
            new Thread(new Persona(i, banco)).start();
        }

        scanner.close();
    }
}
