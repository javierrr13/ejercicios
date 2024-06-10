package ej10Hilos;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Piscina {

    private static final int CALLES = 8;
    private static final Semaphore semaforo = new Semaphore(CALLES, true);

    private static int hombres = 0;
    private static int mujeres = 0;
    private static int niños = 0;
    private static int niñas = 0;
    private static int submarinistas = 0;

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            String tipo = getRandomTipoUsuario();
            String nombre = "Usuario" + i + " (" + tipo + ")";
            new Thread(new Usuario(nombre, tipo)).start();
        }
    }

    private static String getRandomTipoUsuario() {
        Random random = new Random();
        int tipo = random.nextInt(5);  // 0-4 para 5 tipos de usuario
        switch (tipo) {
            case 0: return "hombre";
            case 1: return "mujer";
            case 2: return "niño";
            case 3: return "niña";
            default: return "submarinista";
        }
    }

    static class Usuario implements Runnable {
        private final String nombre;
        private final String tipo;

        public Usuario(String nombre, String tipo) {
            this.nombre = nombre;
            this.tipo = tipo;
        }

        @Override
        public void run() {
            try {
                if (tipo.equals("submarinista")) {
                    semaforo.acquire(2);
                    incrementarContador("submarinista");
                } else {
                    semaforo.acquire();
                    incrementarContador(tipo);
                }

                mostrarEstado("entra");

                // Simula el tiempo de nado
                Thread.sleep(new Random().nextInt(31) + 50);

                if (tipo.equals("submarinista")) {
                    semaforo.release(2);
                    decrementarContador("submarinista");
                } else {
                    semaforo.release();
                    decrementarContador(tipo);
                }

                mostrarEstado("sale");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void incrementarContador(String tipo) {
            switch (tipo) {
                case "hombre": hombres++; break;
                case "mujer": mujeres++; break;
                case "niño": niños++; break;
                case "niña": niñas++; break;
                case "submarinista": submarinistas++; break;
            }
        }

        private synchronized void decrementarContador(String tipo) {
            switch (tipo) {
                case "hombre": hombres--; break;
                case "mujer": mujeres--; break;
                case "niño": niños--; break;
                case "niña": niñas--; break;
                case "submarinista": submarinistas--; break;
            }
        }

        private synchronized void mostrarEstado(String accion) {
            System.out.printf("%s %s. Hombres: %d, Mujeres: %d, Niños: %d, Niñas: %d, Submarinistas: %d\n",
                nombre, accion, hombres, mujeres, niños, niñas, submarinistas);
        }
    }
}
