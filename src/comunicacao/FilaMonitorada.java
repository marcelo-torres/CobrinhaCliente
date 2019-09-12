package comunicacao;

import java.util.LinkedList;
import java.util.concurrent.*;

public class FilaMonitorada<T>{
    
    private LinkedList<T> fila = new LinkedList<T>();

    private Semaphore semaforoGeral = new Semaphore(1);
    private Semaphore semaforoContador = new Semaphore(0);
    
    private boolean fechada = false;
    
    public FilaMonitorada() {
        //System.out.println("Iniciando com o semáforo: " + semaforoGeral);
    }
    
    public void fechar() {
        if(this.fechada) return;
        this.fechada = true;
        
        try {
            //System.out.println("[inserir 1] tentando acquire com o semáforo: " + semaforoGeral);
            semaforoGeral.acquire();
            fila.add(null);
            semaforoGeral.release();
            semaforoContador.release();
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    
    public boolean adicionar(T item) {
        if(this.fechada) return false;
        
        try {
            //System.out.println("[inserir 1] tentando acquire com o semáforo: " + semaforoGeral);
            semaforoGeral.acquire();
            //System.out.println("[inserir 2] acquire obtido e o semáforo: " + semaforoGeral);
            fila.add(item);
            semaforoGeral.release();
            //System.out.println("[inserir 3] semáforo geral released: " + semaforoGeral);
            semaforoContador.release();
            
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
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
            //e.printStackTrace();
        }
        
        return false;
    }

    public T remover() {
        if(this.fechada) return null;
        
        try {
            semaforoContador.acquire();
            //System.out.println("[remover 1] tentando acquire com o semáforo: " + semaforoGeral);
            semaforoGeral.acquire();
            //System.out.println("[remover 2] acquire obtido e o semáforo: " + semaforoGeral);
            T item = fila.removeFirst();
            semaforoGeral.release();		
            //System.out.println("[remover 3] semáforo released: " + semaforoGeral);
            return item;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } 

        return null;
    }
}