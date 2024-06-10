package ej1Sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 12345;
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor de la hora iniciado en el puerto " + puerto);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    out.println(horaActual);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
