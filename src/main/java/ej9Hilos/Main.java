package ej9Hilos;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.TextArea;

public class Main {
    public JFrame frame;
    public JProgressBar progres1;
    public JProgressBar cliente6;
    public JLabel label5;
    public TextArea textote;
    public BarberoTienda tienda;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);

                window.tienda = new BarberoTienda();
                window.initComponents();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        progres1 = new JProgressBar();
        progres1.setBounds(10, 10, 200, 20);
        frame.getContentPane().add(progres1);

        cliente6 = new JProgressBar();
        cliente6.setBounds(10, 40, 200, 20);
        frame.getContentPane().add(cliente6);

        label5 = new JLabel("Clientes atendidos: 0");
        label5.setBounds(10, 70, 150, 20);
        frame.getContentPane().add(label5);

        textote = new TextArea();
        textote.setBounds(10, 100, 400, 150);
        frame.getContentPane().add(textote);
    }

    private void initComponents() {
        Barber barbero1 = new Barber(tienda, 1, progres1, cliente6, label5, textote);
        Barber barbero2 = new Barber(tienda, 2, progres1, cliente6, label5, textote);

        Thread th1 = new Thread(barbero1);
        Thread th2 = new Thread(barbero2);

        th1.start();
        th2.start();

        CustomerGen generadorClientes = new CustomerGen(tienda, cliente6, textote, label5); 
        Thread thGenClientes = new Thread(generadorClientes);
        thGenClientes.start();
    }
}