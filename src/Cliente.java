import comunicacao.Comunicador;
import comunicacao.Comunicador.Modo;
import comunicacao.ComunicadorTCP;
import comunicacao.ComunicadorUDP;
import comunicacao.FalhaDeComunicacaoEmTempoRealException;
import comunicacao.Interpretador;
import comunicacao.Mensageiro;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Cliente {
    
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_SERVIDOR;
    private final int PORTA_CLIENTE;
    
    private final Interpretador INTERPRETADOR;
    private final Mensageiro MENSAGEIRO;
    
    
    public Cliente(InetAddress enderecoServidor, int portaServidor, int portaCliente) {
        this.ENDERECO_SERVIDOR = enderecoServidor;
        this.PORTA_SERVIDOR = portaServidor;
        this.PORTA_CLIENTE = portaCliente;
        
        this.INTERPRETADOR = new Interpretador();
        this.MENSAGEIRO = new Mensageiro(this.INTERPRETADOR, portaCliente, enderecoServidor, portaServidor, portaServidor);
        
        /*Interpretador interpretador,
            int portaEscutarUDP,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor,
            int portaUDPDoServidor
        
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
        
        this.INTERPRETADOR.definirComunicador(COMUNICADOR_TCP, COMUNICADOR_UDP);*/
    }
    
    private Thread.UncaughtExceptionHandler gerenciadorDeException = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread th, Throwable ex) {
            System.out.println("Uncaught exception: " + ex);
            ex.printStackTrace();
            /*try {
                if(false)//COMUNICADOR_TCP.close();
            } catch(IOException ioe) {
                //throw new ComunicadorException("Erro no comunicador", ex);
            }*/
        }
    };
    
    private void iniciar() {
        try {
            //this.iniciarComunicacao();
            this.MENSAGEIRO.iniciar();
            
            LinkedList<String> mensagens = new LinkedList();
            mensagens.add("Mensagem TCP 1");
            mensagens.add("Mensagem TCP 2");
            mensagens.add("Mensagem TCP 3");
            mensagens.add("Mensagem TCP 4");
            for(String mensagem : mensagens) {
                this.MENSAGEIRO.enviarMensagemTCP(mensagem.getBytes());
            }
            
            Thread a = new Thread(new Runnable() {
                Mensageiro m = MENSAGEIRO;
                @Override
                public void run() {
                    while(true) {
                        m.entregarMensagem();
                    }
                }
            });
            a.start();
            
            try{new Thread().sleep(5 * 1000);} catch(Exception e){}
            try{a.interrupt();} catch(Exception e){}
        } catch(IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException("Não é possível estabelecer a conexão");
            // TODO: salvar exception no log e tentar se reconectar
        }
    }
    
    public void encerrar() {
        System.out.println("encerrando");
        this.MENSAGEIRO.close();
    }
    
    public static void main(String[] args) {
        
        InetAddress enderecoServidor = null;
        try {
            enderecoServidor = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException uhe) {
            throw new RuntimeException("Erro: " + uhe.getMessage());
        }
        int portaServidor = 2573;
        int portaCliente = 1235;
        
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
        
        cliente.encerrar();
    }
    
}
