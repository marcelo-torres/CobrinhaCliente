package comunicacao;

import java.util.LinkedList;
import java.util.concurrent.*;

public class FilaMonitorada<T>{
    
    private LinkedList<T> fila = new LinkedList<T>();

    private Semaphore semGeral = new Semaphore(1);
    private Semaphore semContador = new Semaphore(0);

    
    // TODO adicionar tamanho maximo
    
    
    public void adicionar(T item) {

        try {
            semGeral.acquire();
            fila.add(item);
            semGeral.release();

            semContador.release();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void recolocar(T item) {

        try {
            semGeral.acquire();
            fila.addFirst(item);
            semGeral.release();

            semContador.release();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public T remover() {

        try {

            semContador.acquire();	

            semGeral.acquire();

            T item = fila.removeFirst();

            semGeral.release();				

            return item;

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        return null;
    }
}