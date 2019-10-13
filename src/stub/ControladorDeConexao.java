package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import aplicacao.jogo.ControladorDePartida;
import java.net.InetAddress;
import java.util.regex.Pattern;
import java.util.LinkedList;
import nucleo.ControladorCliente;
import stub.comando.Comando;
import stub.comando.ComandoExibirMensagem;
import stub.comando.controlador_de_partida.AdversarioSaiu;
import stub.comando.controlador_de_partida.VoceGanhou;
import stub.comando.controlador_de_partida.VocePerdeu;
import stub.comando.gerenciador_de_udp.AtenderPedidoInicioDeAberturaUDP;
import stub.comando.gerenciador_de_udp.ContinuarAberturaUDP;
import stub.comando.gerenciador_de_udp.FecharConexaoUDP;
import stub.comando.gerenciador_de_udp.IniciarFechamentoConexaoUDP;
import stub.comando.gerenciador_de_udp.IniciarPedidoDeAberturaUDP;
import stub.comunicacao.Comunicador;

/**
 * Eh o Stub do cliente. Responsavel por esconder da aplicacao que a implementacao
 * real do objeto Jogador esta em outra maquina.
 */
public class ControladorDeConexao extends Stub implements aplicacao.jogo.Jogador {
    
    private final ControladorDePartida CONTROLADOR_DE_PARTIDA;
    private final InetAddress ENDERECO_DO_SERVIDOR;
    private final GerenciadorDeConexaoUDPRemota GERENCIADOR_CONEXAO_UDP;   
    
    public ControladorDeConexao(
            ControladorDePartida controladorDePartida,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor) {
        super(Comunicador.Modo.CLIENTE,
                enderecoDoServidor,
                portaTCPDoServidor);
        
        this.CONTROLADOR_DE_PARTIDA = controladorDePartida;        
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
        byte[] mensagem = this.INTERPRETADOR.codificarIniciarPartida();
        System.out.println("VOU ATIVAR A PARTIDA");
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        
        
        try {
            this.GERENCIADOR_CONEXAO_UDP.iniciarPedidoDeAberturaUDP();
            this.GERENCIADOR_CONEXAO_UDP.aguardarComunicacaoSerEstabelecida();
        } catch (InterruptedException e) {
            Logger.registrar(ERRO, new String[]{"CONTROLADOR_DE_CONEXAO"}, "Espera no iniciarPartida() interrompida", e);
        }
    }

    @Override
    public void desistirDeProcurarPartida() {
        byte[] mensagem = this.INTERPRETADOR.codificarDesistirDeProcurarPartida();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        this.GERENCIADOR_CONEXAO_UDP.iniciarFechamentoConexaoUDP();
    }
    
    @Override
    public void encerrarPartida() {
        byte[] mensagem = this.INTERPRETADOR.codificarEncerrarPartida();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        this.GERENCIADOR_CONEXAO_UDP.iniciarFechamentoConexaoUDP();
    }

    @Override
    public void andarParaCima() {
        byte[] mensagem = this.INTERPRETADOR.codificarAndarParaCima();
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }

    @Override
    public void andarParaBaixo() {
        byte[] mensagem = this.INTERPRETADOR.codificarAndarParaBaixo();
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }

    @Override
    public void andarParaEsquerda() {
        byte[] mensagem = this.INTERPRETADOR.codificarAndarParaEsquerda();
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }

    @Override
    public void andarParaDireita() {
        byte[] mensagem = this.INTERPRETADOR.codificarAndarParaDireita();
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }
    
    @Override
    protected LinkedList<Comando> criarComandosNecessarios() {
        
        LinkedList<Comando> listaDeComandos = new LinkedList<>();
        
        listaDeComandos.add(new AdversarioSaiu("adversarioSaiu", CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new VoceGanhou("voceGanhou", CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new VocePerdeu("vocePerdeu", CONTROLADOR_DE_PARTIDA));
        
        listaDeComandos.add(new ComandoExibirMensagem("exibirMensagem"));
        listaDeComandos.add(new AtenderPedidoInicioDeAberturaUDP("atenderPedidoInicioDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new ContinuarAberturaUDP("continuarAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new FecharConexaoUDP("fecharConexaoUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarFechamentoConexaoUDP("iniciarFechamentoConexaoUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarPedidoDeAberturaUDP("iniciarPedidoDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        
        return listaDeComandos;
    }
}