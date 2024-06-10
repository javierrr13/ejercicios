package ej8Hilos;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CadenaDeMontaje {
    private static final int CAPACIDAD = 10; // Capacidad máxima de la cadena de montaje
    private static final Queue<Integer> cadena = new LinkedList<>();
    private static final Object lock = new Object();
    private static int totalProductosEmpaquetados = 0;

    public static void main(String[] args) {
        Thread colocador = new Thread(new Colocador());
        Thread empaquetador1 = new Thread(new Empaquetador(1));
        Thread empaquetador2 = new Thread(new Empaquetador(2));
        Thread empaquetador3 = new Thread(new Empaquetador(3));

        colocador.start();
        empaquetador1.start();
        empaquetador2.start();
        empaquetador3.start();
    }

    static class Colocador implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (lock) {
                        while (cadena.size() >= CAPACIDAD) {
                            lock.wait();
                        }
                        int producto = random.nextInt(3) + 1;
                        cadena.add(producto);
                        System.out.println("Colocador ha colocado producto tipo " + producto);
                        lock.notifyAll();
                    }
                    Thread.sleep(500); // Simular tiempo de colocación
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Empaquetador implements Runnable {
        private final int tipoProducto;

        public Empaquetador(int tipoProducto) {
            this.tipoProducto = tipoProducto;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (lock) {
                        while (!cadena.contains(tipoProducto)) {
                            lock.wait();
                        }
                        cadena.remove(tipoProducto);
                        totalProductosEmpaquetados++;
                        System.out.println("Empaquetador de tipo " + tipoProducto + " ha empaquetado un producto. Total empaquetados: " + totalProductosEmpaquetados);
                        lock.notifyAll();
                    }
                    Thread.sleep(500); // Simular tiempo de empaquetado
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
