import stub.comunicacao.FalhaDeComunicacaoEmTempoRealException;
import stub.InterpretadorCliente;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Cliente {
    
    private final int PORTA_ESCUTAR_UDP;
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_TCP_SERVIDOR;
    private final int PORTA_UDP_SERVIDOR;
    
    private final InterpretadorCliente INTERPRETADOR;
    
    
    public Cliente(int portaEscutarUDP, InetAddress enderecoServidor, int portaTCPServidor, int portaUDPServidor) {
        this.PORTA_ESCUTAR_UDP = portaEscutarUDP;
        this.ENDERECO_SERVIDOR = enderecoServidor;
        this.PORTA_TCP_SERVIDOR = portaTCPServidor;
        this.PORTA_UDP_SERVIDOR = portaUDPServidor;
        
        this.INTERPRETADOR = new InterpretadorCliente(this.PORTA_ESCUTAR_UDP, this.ENDERECO_SERVIDOR, this.PORTA_TCP_SERVIDOR, this.PORTA_UDP_SERVIDOR);
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
            this.INTERPRETADOR.iniciar();
            this.INTERPRETADOR.algumMetodoQueVaiPrecisarUsarConexaoUDP();
            
            LinkedList<String> mensagens = new LinkedList();
            mensagens.add("Mensagem TCP 1");
            mensagens.add("Mensagem TCP 2");
            mensagens.add("Mensagem TCP 3");
            mensagens.add("Mensagem TCP 4");
            for(String mensagem : mensagens) {
                this.INTERPRETADOR.enviarMensagemTCPLembrarDeApagarEsteMetodo(mensagem.getBytes());
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO: salvar exception no log e tentar se reconectar
        }
    }
    
    public void encerrar() {
        System.out.println("encerrando");
        this.INTERPRETADOR.close();
    }
    
    public static void main(String[] args) {
        
        int portaEscutarUDP = 1235;
        InetAddress enderecoServidor = null;
        try {
            enderecoServidor = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException uhe) {
            throw new RuntimeException("Erro: " + uhe.getMessage());
        }     
        int portaTCPServidor = 2573;
        int portaUDPServidor = -1;
        
        Cliente cliente = new Cliente(portaEscutarUDP, enderecoServidor, portaTCPServidor, portaUDPServidor);
        
        try{
            cliente.iniciar();
        } catch(FalhaDeComunicacaoEmTempoRealException e) {
            e.printStackTrace();
        }
        
        try{
            new Thread().sleep(10 * 1000);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        cliente.encerrar();

        /*
        try{
            new Thread().sleep(3 * 1000);
        } catch(Exception e) {
            e.printStackTrace();
        }
         
        Ver threads q estao ativas
        Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "  " + " Is Alive: " + t.isAlive()));
        */
    }
    
}