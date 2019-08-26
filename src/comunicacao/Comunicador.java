package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcelo
 */
public class Comunicador implements Runnable {

    private String enderecoIPServidor;
    private int portaServidor;
    private Interpretador interpretador;
    
    private Socket socket;
    
    
    public Comunicador(String enderecoIPServidor, int portaServidor, Interpretador interpretador) {
        this.enderecoIPServidor = enderecoIPServidor;
        this.portaServidor = portaServidor;
        this.interpretador = interpretador;
        this.interpretador.definirComunicador(this);
    }
    
    
    @Override
    public void run() {
        this.abrirSocket(this.enderecoIPServidor, this.portaServidor);
        System.out.println("cliente conectando");
       
        InputStream entradaTCP;
        OutputStream saidaTCP;
        
        try {
            entradaTCP = this.socket.getInputStream();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            //Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            saidaTCP = this.socket.getOutputStream();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            //Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        while(true) {
            
            // definir entrada
            this.interpretador.processar();
            
            
        }
        

    }
    
    private void abrirSocket(String enderecoIPServidor, int portaServidor) {
        try {
            this.socket = new Socket(enderecoIPServidor, portaServidor);
        } catch (IOException ioe) {
            throw new RuntimeException("Nao eh possivel se conectar ao servidor", ioe);
        }
    }
    
    // comunicacaoUDP socket
    // tcp
    
    
    //escutar
    
    // enviarMensagem(mensagem)
    
    public static void main(String[] args) {
        Comunicador comunicador = new Comunicador("127.0.0.1", 1234);
        new Thread(comunicador).start();
    }
}
