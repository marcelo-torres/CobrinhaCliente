import controller.ControladorGeral;
import java.io.Closeable;
import stub.comunicacao.FalhaDeComunicacaoEmTempoRealException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import model.agentes.IControladorGeralVisaoStubCliente;
import stub.ControladorDeConexao;
import stub.comunicacao.GerenciadorDePortas;

public class Cliente implements Closeable {
    
    private final InetAddress ENDERECO_SERVIDOR;
    private final int PORTA_TCP_SERVIDOR;
    private final GerenciadorDePortas GERENCIADOR_DE_PORTAS;

    private final IControladorGeralVisaoStubCliente CONTROLADOR_GERAL;
    private final ControladorDeConexao CONTROLADOR_DECONEXAO;
    
    
    public Cliente(InetAddress enderecoServidor, int portaTCPServidor) {
        this.ENDERECO_SERVIDOR = enderecoServidor;
        this.PORTA_TCP_SERVIDOR = portaTCPServidor;
        this.GERENCIADOR_DE_PORTAS = new GerenciadorDePortas();
        
        this.CONTROLADOR_GERAL = new ControladorGeral();          
        this.CONTROLADOR_DECONEXAO = new ControladorDeConexao(this.CONTROLADOR_GERAL, this.ENDERECO_SERVIDOR, this.PORTA_TCP_SERVIDOR, this.GERENCIADOR_DE_PORTAS);
        this.CONTROLADOR_GERAL.setJogador(this.CONTROLADOR_DECONEXAO);
    }
    
    public Cliente(InetAddress enderecoServidor, int portaTCPServidor, int inicioIntervaloUDP, int fimIntervaloUDP) {
        this.ENDERECO_SERVIDOR = enderecoServidor;
        this.PORTA_TCP_SERVIDOR = portaTCPServidor;
        this.GERENCIADOR_DE_PORTAS = new GerenciadorDePortas(inicioIntervaloUDP, fimIntervaloUDP);
        
        this.CONTROLADOR_GERAL = new ControladorGeral();          
        this.CONTROLADOR_DECONEXAO = new ControladorDeConexao(this.CONTROLADOR_GERAL, this.ENDERECO_SERVIDOR, this.PORTA_TCP_SERVIDOR, this.GERENCIADOR_DE_PORTAS);
        this.CONTROLADOR_GERAL.setJogador(this.CONTROLADOR_DECONEXAO);
    }
    
    public void iniciar() {
        try {
            // #### AQUI QUE AS COISAS ESTAO INICIADA
            this.CONTROLADOR_DECONEXAO.iniciarStub();
        } catch(Exception e) {
            this.CONTROLADOR_GERAL.falha(e.getMessage());
        }
    }
    
    @Override
    public void close() {
        //this.CONTROLADOR_CLIENTE.close();
    }
    
    public static void main(String[] args) {
        
        InetAddress enderecoServidor = null;
        try {
            enderecoServidor = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException uhe) {
            throw new RuntimeException("Erro: " + uhe.getMessage());
        }     
        int portaTCPServidor = 1855;
        
        Cliente cliente = new Cliente(enderecoServidor, portaTCPServidor);
        
        try{
            cliente.iniciar();
        } catch(FalhaDeComunicacaoEmTempoRealException e) {
            e.printStackTrace();
        }

        /*
        try{
            new Thread().sleep(20 * 1000);
        } catch(Exception e) {
            e.printStackTrace();
        }
        */
         
        //Ver threads q estao ativas
        //Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "  " + " Is Alive: " + t.isAlive()));
        
    }
    
}