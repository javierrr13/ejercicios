package ej3Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 12345;
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor aritmético iniciado en el puerto " + puerto);
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String operacion = in.readLine();
                    double resultado = realizarOperacion(operacion);
                    out.println(resultado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double realizarOperacion(String operacion) {
        String[] partes = operacion.split(" ");
        String operador = partes[0];
        double num1 = Double.parseDouble(partes[1]);
        double num2 = operador.equals("sqrt") ? 0 : Double.parseDouble(partes[2]);

        switch (operador) {
            case "suma": return num1 + num2;
            case "resta": return num1 - num2;
            case "multiplica": return num1 * num2;
            case "divide": return num1 / num2;
            case "sqrt": return Math.sqrt(num1);
            default: throw new IllegalArgumentException("Operación no soportada: " + operador);
        }
    }
}
