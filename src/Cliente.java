import comunicacao.Comunicador;
import comunicacao.Comunicador.Modo;
import comunicacao.ComunicadorTCP;
import comunicacao.ComunicadorUDP;
import comunicacao.FalhaDeComunicacaoEmTempoRealException;
import comunicacao.Interpretador;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Cliente {
    
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_SERVIDOR;
    private final int PORTA_CLIENTE;
    
    private final Interpretador INTERPRETADOR;
    private final ComunicadorTCP COMUNICADOR_TCP;
    private final ComunicadorUDP COMUNICADOR_UDP;
    
    
    public Cliente(InetAddress enderecoServidor, int portaServidor, int portaCliente) {
        this.ENDERECO_SERVIDOR = enderecoServidor;
        this.PORTA_SERVIDOR = portaServidor;
        this.PORTA_CLIENTE = portaCliente;
        
        this.INTERPRETADOR = new Interpretador();
        
        this.COMUNICADOR_TCP = new ComunicadorTCP(
                Modo.CLIENTE,
                this.INTERPRETADOR,
                gerenciadorDeException,
                10);
        
        this.COMUNICADOR_UDP = new ComunicadorUDP(
                Comunicador.Modo.CLIENTE,
                this.INTERPRETADOR,
                gerenciadorDeException,
                10,
                1024,
                this.PORTA_CLIENTE);
        
        this.INTERPRETADOR.definirComunicador(COMUNICADOR_TCP, COMUNICADOR_UDP);
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
    
    private void iniciar() {
       
        try {
            this.iniciarComunicacao();
        } catch(IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException("Não é possível estabelecer a conexão");
            // TODO: salvar exception no log e tentar se reconectar
        }
            
        java.util.LinkedList mensagens = new java.util.LinkedList();
        mensagens.add("Mensagem TCP 1 do cliente para o servidor");
        mensagens.add("Mensagem TCP 2 do cliente para o servidor");
        mensagens.add("Mensagem TCP 3 do cliente para o servidor");
        mensagens.add("Mensagem TCP 4 do cliente para o servidor");
        mensagens.add("Mensagem TCP 5 do cliente para o servidor");
        mensagens.add("Mensagem TCP 6 do cliente para o servidor");
        mensagens.add("Mensagem TCP 7 do cliente para o servidor");
        
        this.INTERPRETADOR.enviarMensagem(mensagens);
    }
    
    public void encerrar() {
        try {
            this.fecharComunicacao();
        } catch(IOException ioe) {
            throw new RuntimeException("Erro ao fechar a conexão");
            // TODO: salvar exception no log e tentar se reconectar
        }
    }
    
    private void iniciarComunicacao() throws IOException { 
        this.COMUNICADOR_TCP.iniciar(this.ENDERECO_SERVIDOR, this.PORTA_SERVIDOR);
        this.COMUNICADOR_UDP.iniciar(this.ENDERECO_SERVIDOR, this.PORTA_CLIENTE);
        
    }
    
    private void fecharComunicacao() throws IOException {
        this.COMUNICADOR_TCP.close();
    }
    
    public static void main(String[] args) {
        
        InetAddress enderecoServidor = null;
        try {
            enderecoServidor = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException uhe) {
            throw new RuntimeException("Erro: " + uhe.getMessage());
        }
        int portaServidor = 2573;
        int portaCliente = 1234;
        
        Cliente cliente = new Cliente(enderecoServidor, portaServidor, portaCliente);
        
        try{
            cliente.iniciar();
        } catch(FalhaDeComunicacaoEmTempoRealException e) {
            e.printStackTrace();
            System.out.println("morreu");
        }
        
        try{
            new Thread().sleep(1000);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        //cliente.encerrar();
    }
    
}
