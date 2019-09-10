package comunicacao;

import java.util.LinkedList;
import java.util.concurrent.*;

public class FilaMonitorada<T>{
    
    private LinkedList<T> fila = new LinkedList<T>();

    private Semaphore semaforoGeral = new Semaphore(1);
    private Semaphore semaforoContador = new Semaphore(0);
    
    private boolean fechada = false;

    
    // TODO adicionar tamanho maximo
    
    /*public boolean fechar() {
        
        
        semaforoGeral.release();
    }*/
    
    public boolean adicionar(T item) {
        if(this.fechada) return false;
        
        try {
            semaforoGeral.acquire();
            fila.add(item);
            semaforoGeral.release();
            semaforoContador.release();
            
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return false;
    }

    public boolean colocarNoInicio(T item) {
        if(this.fechada) return false;
        
        try {
            semaforoGeral.acquire();
            fila.addFirst(item);
            semaforoGeral.release();
            semaforoContador.release();

            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return false;
    }

    public T remover() {
        if(this.fechada) return null;
        
        try {

            semaforoContador.acquire();	
            semaforoGeral.acquire();
            T item = fila.removeFirst();
            semaforoGeral.release();				

            return item;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        return null;
    }
}