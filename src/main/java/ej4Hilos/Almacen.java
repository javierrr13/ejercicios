package ej4Hilos;

public interface Almacen<T> {
	 public void almacenar(T producto);
	 public T retirar();
	}
