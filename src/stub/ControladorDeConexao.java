package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import model.agentes.ControladorDePartida;
import java.net.InetAddress;
import java.util.LinkedList;
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
import localizacoes.ILocal;
import stub.comando.Comando;
import stub.comando.controlador_de_partida.EntregarQuadroComando;
import stub.comando.controlador_de_partida.FalhaAoLogar;
import stub.comando.controlador_de_partida.IrParaOHall;
import stub.comando.controlador_de_partida.Logar;
import stub.comunicacao.FilaMonitorada;

/**
 * Eh o Stub do cliente. Responsavel por esconder da aplicacao que a implementacao
 * real do objeto Jogador esta em outra maquina.
 */
public class ControladorDeConexao extends Stub implements model.agentes.IJogador {
    
    private final FilaMonitorada FILA_RETORNO_VD = new FilaMonitorada(100);
    private final FilaMonitorada FILA_RETORNO_LOCAL_ATUAL = new FilaMonitorada(100);
    
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
        this.registrarFilas();
        super.iniciar();
    }
    
    private void registrarFilas() {
        this.INTERPRETADOR.cadastrarFilaDeRetorno("getVD", this.FILA_RETORNO_VD);
        this.INTERPRETADOR.cadastrarFilaDeRetorno("getLocalAtual", this.FILA_RETORNO_LOCAL_ATUAL);
    }

    @Override
    public void receberMensagem(byte[] mensagem) {
        if(mensagem == null) {
           Logger.registrar(ERRO, new String[]{"GERENCIADOR_DE_CLIENTE"}, "Mensagem nula recebida. A mensagem sera ignorada.");
           return;
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
    
    
    /*@Override
    public double getVD() {
        byte[] mensagem = this.INTERPRETADOR.codificarGetVD();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        
        // aguarda o retorno
        Double retorno = (Double) this.FILA_RETORNO_VD.remover();
        return retorno;
    }

    @Override
    public ILocal getLocalAtual() {
        byte[] mensagem = this.INTERPRETADOR.codificarGetLocalAtual();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        
        // aguarda o retorno
        ILocal retorno = (ILocal) this.FILA_RETORNO_LOCAL_ATUAL.remover();
        return retorno;
    }

    @Override
    public void setLocalAtual(ILocal local) {
        byte[] mensagem = this.INTERPRETADOR.codificarSetLocalAtual(local);
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
    }*/
    
    @Override
    protected LinkedList<Comando> criarComandosNecessarios() {
        
        LinkedList<Comando> listaDeComandos = new LinkedList<>();
        
        listaDeComandos.add(new AdversarioSaiu("adversarioSaiu", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new VoceGanhou("voceGanhou", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new VocePerdeu("vocePerdeu", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new IrParaOHall("irParaOHall", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new Logar("logar", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new FalhaAoLogar("falhaAoLogar", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new EntregarQuadroComando("entregarQuadro", this.CONTROLADOR_DE_PARTIDA));
        
        listaDeComandos.add(new ComandoExibirMensagem("exibirMensagem"));
        listaDeComandos.add(new AtenderPedidoInicioDeAberturaUDP("atenderPedidoInicioDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new ContinuarAberturaUDP("continuarAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new FecharConexaoUDP("fecharConexaoUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarFechamentoConexaoUDP("iniciarFechamentoConexaoUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarPedidoDeAberturaUDP("iniciarPedidoDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        
        return listaDeComandos;
    }
    
    @Override
    protected void devolverRetorno(byte[] mensagemRetorno) {
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagemRetorno);
    }

    @Override
    public void setNomeJogador(String nome_jogador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}