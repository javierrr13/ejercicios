package ej3Sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente extends JFrame {
	
    private JTextField display;
    private String operador;
    private double num1, num2;

    public Cliente() {
        setTitle("Calculadora Aritmética");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 32));
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 32));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("=")) {
                num2 = Double.parseDouble(display.getText());
                try {
                    String resultado = enviarOperacion(operador, num1, num2);
                    display.setText(resultado);
                } catch (IOException ex) {
                    display.setText("Error");
                }
            } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                operador = command;
                num1 = Double.parseDouble(display.getText());
                display.setText("");
            } else {
                display.setText(display.getText() + command);
            }
        }

        private String enviarOperacion(String operador, double num1, double num2) throws IOException {
            String servidor = "localhost";
            int puerto = 12345;
            try (Socket socket = new Socket(servidor, puerto);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String operacion = String.format("%s %f %f", operador, num1, num2);
                out.println(operacion);
                return in.readLine();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cliente calculadora = new Cliente();
            calculadora.setVisible(true);
        });
    }
}
