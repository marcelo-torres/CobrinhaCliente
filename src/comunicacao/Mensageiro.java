package comunicacao;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;

public class Mensageiro implements Closeable {
    
    private final Interpretador INTERPRETADOR;
    private final ComunicadorTCP COMUNICADOR_TCP;
    private final ComunicadorUDP COMUNICADOR_UDP;
    
    private final FilaMonitorada<byte[]> FILA_ENVIO_MENSAGENS;
    private final int TAMANHO_FILA_ENVIO = 100;
    private final FilaMonitorada<byte[]> FILA_RECEBIMENTO_MENSAGENS;
    private final int TAMANHO_FILA_RECEBIMENTO = 100;
    
    private final int TAMANHO_MENSAGEM_UDP = 1024;
    
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_TCP_SERVIDOR;
    private final int PORTA_UDP_SERVIDOR;
    
    public Mensageiro(
            Interpretador interpretador,
            int portaEscutarUDP,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor,
            int portaUDPDoServidor) {
    
        this.INTERPRETADOR = interpretador;
        
        this.ENDERECO_SERVIDOR = enderecoDoServidor;
        this.PORTA_TCP_SERVIDOR = portaTCPDoServidor;
        this.PORTA_UDP_SERVIDOR = portaUDPDoServidor;
        
        this.FILA_ENVIO_MENSAGENS = new FilaMonitorada<>();
        this.FILA_RECEBIMENTO_MENSAGENS = new FilaMonitorada<>();
        
        this.COMUNICADOR_TCP = new ComunicadorTCP(
                Comunicador.Modo.CLIENTE,
                this.FILA_ENVIO_MENSAGENS,
                this.FILA_RECEBIMENTO_MENSAGENS,
                this.gerenciadorDeException);
        
        this.COMUNICADOR_UDP = new ComunicadorUDP(
                Comunicador.Modo.CLIENTE,
                this.FILA_ENVIO_MENSAGENS,
                this.FILA_RECEBIMENTO_MENSAGENS,
                this.gerenciadorDeException,
                this.TAMANHO_MENSAGEM_UDP,
                portaEscutarUDP);
        
        this.INTERPRETADOR.definirComunicador(COMUNICADOR_TCP, COMUNICADOR_UDP);
    }
    
    
    public void iniciar() throws IOException {
        try {
            this.COMUNICADOR_TCP.iniciar(this.ENDERECO_SERVIDOR, this.PORTA_TCP_SERVIDOR);
        } catch(IOException ioe) {
            ioe.printStackTrace(); // REGISTRAR NO LOG
            throw new IOException("Não é possível se comunicar com o servidor.");
        } catch(IllegalThreadStateException itse) {
            itse.printStackTrace(); // REGISTRAR NO LOG
        }
        
        try {
            this.COMUNICADOR_UDP.iniciar(this.ENDERECO_SERVIDOR, this.PORTA_UDP_SERVIDOR);
        } catch(IOException ioe) {
            this.COMUNICADOR_TCP.encerrarConexao();
            this.COMUNICADOR_TCP.close();
            ioe.printStackTrace(); // REGISTRAR NO LOG
            throw new IOException("Não é possível se comunicar com o servidor.");
        } catch(IllegalThreadStateException itse) {
            this.COMUNICADOR_TCP.encerrarConexao();
            this.COMUNICADOR_TCP.close();
            itse.printStackTrace(); // REGISTRAR NO LOG
        }
    }
    
    @Override
    public void close() {
        if(true)return;
        try {
            this.COMUNICADOR_TCP.encerrarConexao();
            this.COMUNICADOR_UDP.encerrarComunicacao();

            try {
            new Thread().sleep(1000);
            } catch(Exception e) {}

            this.COMUNICADOR_TCP.close();
            this.COMUNICADOR_UDP.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    
    public void enviarMensagemTCP(byte[] mensagem) {
        this.COMUNICADOR_TCP.enviarMensagem(mensagem);
    }
    
    public void enviarMensagemUDP(byte[] mensagem) {
        this.COMUNICADOR_UDP.enviarMensagem(mensagem);
    }
    
    //@Override
    public void entregarMensagem() {
        byte[] mensagem = this.FILA_RECEBIMENTO_MENSAGENS.remover();
        this.INTERPRETADOR.receberMensagem(mensagem);
    }
    
    
    private Thread.UncaughtExceptionHandler gerenciadorDeException = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread th, Throwable ex) {
            System.out.println("Uncaught exception: " + ex);
            ex.printStackTrace();
            try {
                if(false)COMUNICADOR_TCP.close();
            } catch(IOException ioe) {
                //throw new ComunicadorException("Erro no comunicador", ex);
            }
        }
    };
    
}
