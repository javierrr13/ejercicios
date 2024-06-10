package ej9Hilos;


import java.awt.TextArea;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Logica {
	
    public static void main(String args[]) {
        BarberoTienda tienda = new BarberoTienda();
        JProgressBar progres1 = new JProgressBar();
        JProgressBar cliente6 = new JProgressBar();
        JLabel label5 = new JLabel();
        TextArea textote = new TextArea();

        Barber barbero1 = new Barber(tienda, 1, progres1, cliente6, label5, textote);
        Barber barbero2 = new Barber(tienda, 2, progres1, cliente6, label5, textote);

        Thread th1 = new Thread(barbero1);
        Thread th2 = new Thread(barbero2);

        th1.start();
        th2.start();

        CustomerGen generadorClientes = new CustomerGen(tienda, cliente6, textote,label5);
        Thread thGenClientes = new Thread(generadorClientes);
        thGenClientes.start();
    }
}

class Barber implements Runnable {
    BarberoTienda tienda;
    int num;
    JProgressBar progres1;
    JProgressBar cliente6;
    int total = 5;
    JLabel texto;
    TextArea textote;
    JLabel label5; 

    public Barber(BarberoTienda tienda, int num, JProgressBar progres1, JProgressBar cliente6, JLabel label5,
            TextArea textote) {
        this.tienda = tienda;
        this.num = num;
        this.progres1 = progres1;
        this.progres1.setMinimum(0);
        this.progres1.setMaximum(20 - 1);
        this.cliente6 = cliente6;
        this.texto = label5;
        this.textote = textote;
        this.label5 = label5; 
    }

    @Override
    public void run() {
    	// TODO Auto-generated method stub
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textote.append("Barbero " + num + " comenzara ... " + "\n");
        int var = 0;
        while (total > 0) {
            tienda.cortePelo(num, progres1, cliente6, textote, label5); 
            total--;
            var++;
            texto.setText("Clientes atendidos " + var);
        }
    }
}


class Customer implements Runnable {
    String name;
    Date inTime;
    int n = 0;
    BarberoTienda tienda;
    TextArea textote;

    public Customer(BarberoTienda tienda, TextArea textote) {
        this.tienda = tienda;
        this.textote = textote;
    }

    public String getName() {
        return name;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    @Override
    public void run() {
    	// TODO Auto-generated method stub
        iracortarpelo();
    }

    private synchronized void iracortarpelo() {
        tienda.add(this, textote);
    }
}

class CustomerGen implements Runnable {
    BarberoTienda tienda;
    JProgressBar cliente;
    TextArea textote;

    public CustomerGen(BarberoTienda tienda, JProgressBar progressBar, TextArea textote, JLabel label5) {
        this.tienda = tienda;
        cliente = progressBar;
        cliente.setMinimum(0);
        cliente.setMaximum(3);
        this.textote = textote;
    }

    @Override
    public void run() {
    	// TODO Auto-generated method stub
        int i = 0;
        while (true) {
            i = i + 1;
            cliente.setValue(i);
            Customer customer = new Customer(tienda, textote);
            customer.setInTime(new Date());
            Thread thcust = new Thread(customer);
            customer.setName("Cliente thread " + thcust.getId());
            thcust.start();
            try {
                TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BarberoTienda {
    int sillas;
    LinkedList<Customer> listCustomer;
    int clientesAtendidos; // Nuevo atributo para el recuento de clientes atendidos

    public BarberoTienda() {
        sillas = 3;
        listCustomer = new LinkedList<Customer>();
        clientesAtendidos = 0;
    }

    public void cortePelo(int val, JProgressBar progres1, JProgressBar cliente7, TextArea textote, JLabel label5) {
        Customer customer;
        textote.append("Barbero " + val + " esperando para cortar\n");
        int i = 19;
        int aleatorio = 0;
        progres1.setValue(i);
        synchronized (listCustomer) {
            while (true) {
                while (listCustomer.size() == 0 || sillas == 0) {
                    textote.append("Barbero " + val + " est� esperando al cliente\n");
                    try {
                        listCustomer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                aleatorio = (int) (Math.random() * 10 + 1);
                progres1.setValue(i + aleatorio);
                textote.append("Barbero " + val + " encontr� cliente en cola\n");
                customer = listCustomer.poll();
                textote.append("Corte de pelo a cliente " + customer.getName() + "\n");
                long duration = (long) (Math.random() * 10);
                try {
                    TimeUnit.SECONDS.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cliente7.setValue(cliente7.getValue());
                textote.append("Corte de pelo completado al cliente " + customer.getName() + " en " + duration + " segundos\n");
                clientesAtendidos++; 
                cliente7.setValue(0); 
                progres1.setValue(0); 
                textote.append("Clientes atendidos: " + clientesAtendidos + "\n"); 
                label5.setText("Clientes atendidos: " + clientesAtendidos); 
                sillas++;
                listCustomer.notify();
            }
        }
    }

    public void add(Customer customer, TextArea textote) {
        textote.append("Cliente : " + customer.getName() + " entr� a la tienda\n");
        synchronized (listCustomer) {
            while (sillas == 0) {
                textote.append("No hay silla para el cliente\n");
                try {
                    listCustomer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            listCustomer.offer(customer);
            textote.append("Cliente : " + customer.getName() + " tiene la silla\n");
            sillas--;
            listCustomer.notify();
        }
    }
}