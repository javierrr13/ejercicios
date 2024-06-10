package ej4Hilos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class GestionProdCons<T> implements Almacen<T> {
    private final Queue<T> cola;
    private final int capacidad;
    private final Semaphore productos; // Semáforo que cuenta los productos almacenados
    private final Semaphore espacios; // Semáforo que cuenta los espacios disponibles
    private final Semaphore mutex; // Semáforo binario para la exclusión mutua

    public GestionProdCons(int capacidad) {
        this.cola = new LinkedList<>();
        this.capacidad = capacidad;
        this.productos = new Semaphore(0); // Inicialmente, no hay productos almacenados
        this.espacios = new Semaphore(capacidad); // Inicialmente, todos los espacios están disponibles
        this.mutex = new Semaphore(1); // Exclusión mutua para acceder a la cola
    }

    @Override
    public void almacenar(T producto) {
        try {
            espacios.acquire(); // Espera hasta que haya un espacio disponible
            mutex.acquire(); // Entra en la sección crítica
            cola.add(producto);
            System.out.println("Producto almacenado: " + producto);
            mutex.release(); // Sale de la sección crítica
            productos.release(); // Incrementa el contador de productos almacenados
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public T retirar() {
        T producto = null;
        try {
            productos.acquire(); // Espera hasta que haya un producto disponible
            mutex.acquire(); // Entra en la sección crítica
            producto = cola.poll();
            System.out.println("Producto retirado: " + producto);
            mutex.release(); // Sale de la sección crítica
            espacios.release(); // Incrementa el contador de espacios disponibles
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return producto;
    }
}

