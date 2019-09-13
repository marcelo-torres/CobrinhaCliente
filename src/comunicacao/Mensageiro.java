package comunicacao;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;

public class Mensageiro implements Closeable {
    
    public static class Entregador implements Runnable {
        
        protected final ReceptorDeMensagem DESTINATARIO;
        protected final FilaMonitorada<byte[]> FILA_RECEBIMENTO;
        
        protected boolean emEexecucao = false;
        
        public Entregador(ReceptorDeMensagem destinatario,
                FilaMonitorada<byte[]> filaDeRecebimentoDeMensagens) {
        
            this.DESTINATARIO = destinatario;
            this.FILA_RECEBIMENTO = filaDeRecebimentoDeMensagens;
        }
        
        
        @Override
        public void run() {
            this.emEexecucao = true;
            while(this.emExecucao()) {
                byte[] mensagem = this.FILA_RECEBIMENTO.remover();
                if(mensagem != null) {
                    this.DESTINATARIO.receberMensagem(mensagem);
                }
            }
        }
        
        
        public synchronized boolean emExecucao() {
            return emEexecucao;
        }
        
        public synchronized void parar() {
            this.FILA_RECEBIMENTO.fechar();
            this.emEexecucao = false;
        }
    }
    
    private final Interpretador INTERPRETADOR;
    private final ComunicadorTCP COMUNICADOR_TCP;
    private final ComunicadorUDP COMUNICADOR_UDP;
    
    private final int TAMANHO_FILA_RECEBIMENTO = 200;
    private final FilaMonitorada<byte[]> FILA_RECEBIMENTO_MENSAGENS;
    private final int TAMANHO_FILA_ENVIO_TCP = 100;
    private final FilaMonitorada<byte[]> FILA_ENVIO_MENSAGENS_TCP;  
    private final int TAMANHO_FILA_ENVIO_UDP = 100;
    private final FilaMonitorada<byte[]> FILA_ENVIO_MENSAGENS_UDP;
    
    private final int TAMANHO_MENSAGEM_UDP = 1024;
    
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_TCP_SERVIDOR;
    private final int PORTA_UDP_SERVIDOR;
    
    private Entregador entregador;
    private Thread threadDeEntrega;
    
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
        
        this.FILA_RECEBIMENTO_MENSAGENS = new FilaMonitorada<>(this.TAMANHO_FILA_RECEBIMENTO);
        this.FILA_ENVIO_MENSAGENS_TCP = new FilaMonitorada<>(this.TAMANHO_FILA_ENVIO_TCP);
        this.FILA_ENVIO_MENSAGENS_UDP = new FilaMonitorada<>(this.TAMANHO_FILA_ENVIO_UDP);
        
        this.COMUNICADOR_TCP = new ComunicadorTCP(
                Comunicador.Modo.CLIENTE,
                this.FILA_ENVIO_MENSAGENS_TCP,
                this.FILA_RECEBIMENTO_MENSAGENS,
                this.gerenciadorDeException);
        
        this.COMUNICADOR_UDP = new ComunicadorUDP(
                Comunicador.Modo.CLIENTE,
                this.FILA_ENVIO_MENSAGENS_UDP,
                this.FILA_RECEBIMENTO_MENSAGENS,
                this.gerenciadorDeException,
                this.TAMANHO_MENSAGEM_UDP,
                portaEscutarUDP);
    }
    
    
    public void iniciar() throws IOException {
        try {
            this.COMUNICADOR_TCP.iniciar(this.ENDERECO_SERVIDOR, this.PORTA_TCP_SERVIDOR);
        } catch(IOException ioe) {
            ioe.printStackTrace(); // REGISTRAR NO LOG
            throw new IOException("Não é possível se comunicar com o servidor.");
        } catch(IllegalThreadStateException itse) {
            itse.printStackTrace(); // REGISTRAR NO LOG
            return;
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
            return;
        }
        
        this.prepararThreadDeEntrega();
        this.threadDeEntrega.start();
    }
    
    @Override
    public void close() {
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
        
        this.entregador.parar();
        if(this.threadDeEntrega.isAlive()) {
            this.threadDeEntrega.interrupt();
            System.out.println("matou 1");
        }
    }
    
    
    public void enviarMensagemTCP(byte[] mensagem) {
        this.COMUNICADOR_TCP.enviarMensagem(mensagem);
    }
    
    public void enviarMensagemUDP(byte[] mensagem) {
        this.COMUNICADOR_UDP.enviarMensagem(mensagem);
    }
    
    private void prepararThreadDeEntrega() {
        this.entregador = new Entregador(this.INTERPRETADOR, this.FILA_RECEBIMENTO_MENSAGENS);
        this.threadDeEntrega = new Thread(this.entregador);
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
