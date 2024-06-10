package ej1Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 12345;
        try (Socket socket = new Socket(servidor, puerto);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            long tiempoInicio = System.currentTimeMillis();
            String horaServidor = in.readLine();
            long tiempoFin = System.currentTimeMillis();
            long retraso = (tiempoFin - tiempoInicio) / 2;

            System.out.println("Hora recibida del servidor: " + horaServidor);
            System.out.println("Retraso estimado: " + retraso + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

