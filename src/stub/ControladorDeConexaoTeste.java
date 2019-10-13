package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import java.net.InetAddress;
import java.util.regex.Pattern;
import java.util.LinkedList;
import nucleo.ControladorCliente;
import stub.comando.Comando;
import stub.comando.ComandoExibirMensagem;
import stub.comando.gerenciador_de_udp.AtenderPedidoInicioDeAberturaUDP;
import stub.comando.gerenciador_de_udp.ContinuarAberturaUDP;
import stub.comando.gerenciador_de_udp.IniciarPedidoDeAberturaUDP;
import stub.comunicacao.Comunicador;

/**
 * blablablala escrever
 */
public class ControladorDeConexaoTeste extends Stub implements aplicacao.jogo.Jogador {
    
    //private final Semaphore SEMAFORO_ATICAO_UDP = new Semaphore(0);
    
    private final ControladorCliente CONTROLADOR_CLIENTE;
 
    private final InetAddress ENDERECO_DO_SERVIDOR;
    
    private final GerenciadorDeConexaoUDPRemota GERENCIADOR_CONEXAO_UDP;
    

    
    private Pattern PADRAO_NUMERO = Pattern.compile("\\d+");
    private boolean hostProntoParaReceberUDP = false;
    
    
    
    public ControladorDeConexaoTeste(
            ControladorCliente controladorCliente,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor) {
        super(Comunicador.Modo.CLIENTE,
                enderecoDoServidor,
                portaTCPDoServidor);
        
        
        this.CONTROLADOR_CLIENTE = controladorCliente;        
        this.ENDERECO_DO_SERVIDOR = enderecoDoServidor;
        this.GERENCIADOR_CONEXAO_UDP = new GerenciadorDeConexaoUDPRemota(this.MENSAGEIRO, this.ENDERECO_DO_SERVIDOR, this.INTERPRETADOR);
        
        this.INTERPRETADOR.cadastrarComandos(this.criarComandosNecessarios());
        
        super.iniciar();
    }

    @Override
    public void receberMensagem(byte[] mensagem) {
        if(mensagem == null) {
            System.out.println("[!] Mano, vc ta jogando uma mensagem nula no interpretador! O que vc tem na cabeça tiw? Programa direito zeh mane");
        }
        
        this.INTERPRETADOR.interpretar(mensagem);  
    }
    
    /* ########################### CHAMADAS DE RPC ########################## */
    
    @Override
    public void iniciarPartida() {  
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [TCP] Jogador chama iniciarPartida()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
        
        try {
            this.GERENCIADOR_CONEXAO_UDP.iniciarPedidoDeAberturaUDP();
            this.GERENCIADOR_CONEXAO_UDP.aguardarComunicacaoSerEstabelecida();
        } catch (InterruptedException e) {
            Logger.registrar(ERRO, new String[]{"CONTROLADOR_DE_CONEXAO"}, "Espera no iniciarPartida() interrompida", e);
        }
    }

    @Override
    public void desistirDeProcurarPartida() {
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [TCP] Jogador chama desistirDeProcurarPartida()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
        /*if(!this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            try {
                super.MENSAGEIRO.iniciarUDP();
            } catch(IOException ioe) {
                Logger.registrar(ERRO, new String[]{"JOGADOR"}, "Nao foi possivel iniciar a partida devido a uma falha ao iniciar a comunicacao UDP: " + ioe.getMessage(), ioe);
                throw new ErroApresentavelException("Não foi possível iniciar a partida. Erro ao estabelecer uma conexão com o servidor");
            }
        }*/
    }
    
    @Override
    public void encerrarPartida() {
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [TCP] Jogador chama encerrarPartida()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
        //if(this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            this.MENSAGEIRO.close();
        //}
    }

    @Override
    public void andarParaCima() {
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [UDP] Jogador chama andarParaCima()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }

    @Override
    public void andarParaBaixo() {
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [UDP] Jogador chama andarParaBaixo()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }

    @Override
    public void andarParaEsquerda() {
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [UDP] Jogador chama andarParaEsquerda()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }

    @Override
    public void andarParaDireita() {
        byte[] mensagem = this.INTERPRETADOR.codificarExibirMensagem("MSG [UDP] Jogador chama andarParaDireita()");
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }
    
    private LinkedList<Comando> criarComandosNecessarios() {
        
        LinkedList<Comando> listaDeComandos = new LinkedList<>();
        
        // resolver essa bagaca aqui
        //listaDeComandos.add(new AdversarioSaiu("adversarioSaiu",));
        
        listaDeComandos.add(new ComandoExibirMensagem("exibirMensagem"));
        listaDeComandos.add(new AtenderPedidoInicioDeAberturaUDP("atenderPedidoInicioDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new ContinuarAberturaUDP("continuarAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarPedidoDeAberturaUDP("iniciarPedidoDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        
        return listaDeComandos;
    }
}
