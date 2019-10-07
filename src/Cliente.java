import java.io.Closeable;
import stub.comunicacao.FalhaDeComunicacaoEmTempoRealException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import nucleo.ControladorCliente;

public class Cliente implements Closeable {
    
    private final int PORTA_ESCUTAR_UDP;
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_TCP_SERVIDOR;

    private final ControladorCliente CONTROLADOR_CLIENTE;
    
    
    public Cliente(int portaEscutarUDP, InetAddress enderecoServidor, int portaTCPServidor) {
        this.PORTA_ESCUTAR_UDP = portaEscutarUDP;
        this.ENDERECO_SERVIDOR = enderecoServidor;
        this.PORTA_TCP_SERVIDOR = portaTCPServidor;
        
        this.CONTROLADOR_CLIENTE = new ControladorCliente(this.PORTA_ESCUTAR_UDP, this.ENDERECO_SERVIDOR, this.PORTA_TCP_SERVIDOR);
    }
    
    private void iniciar() {
        try {
            this.CONTROLADOR_CLIENTE.executarSequenciaDeTestes();
            
                
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO: salvar exception no log e tentar se reconectar
        }
    }
    
    @Override
    public void close() {
        System.out.println("encerrando");
        this.CONTROLADOR_CLIENTE.close();
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
        
        Cliente cliente = new Cliente(portaEscutarUDP, enderecoServidor, portaTCPServidor);
        
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
        
        cliente.close();

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