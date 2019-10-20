package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import java.net.InetAddress;
import java.util.LinkedList;
import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.ComandoExibirMensagem;
import stub.comando.controlador_de_partida.*;
import stub.comando.gerenciador_de_udp.*;
import stub.comunicacao.Comunicador;
import stub.comando.Comando;
import stub.comunicacao.FilaMonitorada;

/**
 * Eh o Stub do cliente. Responsavel por esconder da aplicacao que a implementacao
 * real do objeto Jogador esta em outra maquina.
 */
public class ControladorDeConexao extends Stub implements model.agentes.IJogadorVisaoAplicacaoCliente {
    
    private final FilaMonitorada FILA_RETORNO_INICIAR_PARTIDA = new FilaMonitorada(100);
    private final FilaMonitorada FILA_RETORNO_DESISTIR_DE_PROCURAR_PARTIDA = new FilaMonitorada(100);
    private final FilaMonitorada FILA_RETORNO_ENCERRAR_PARTIDA = new FilaMonitorada(100);
    
    private  IControladorGeralVisaoStubCliente CONTROLADOR_DE_PARTIDA;
    private final InetAddress ENDERECO_DO_SERVIDOR;
    private final GerenciadorDeConexaoUDPRemota GERENCIADOR_CONEXAO_UDP;   
    private IControladorGeralVisaoStubCliente controladorGeral;
    
    public ControladorDeConexao(
            IControladorGeralVisaoStubCliente controladorDePartida,
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
    
    /*public void setControladorGeral(IControladorGeralVisaoStubCliente controladorDePartida){
        this.controladorGeral = controladorDePartida;
    }*/
    
    private void registrarFilas() {
        this.INTERPRETADOR.cadastrarFilaDeRetorno("iniciarPartida", this.FILA_RETORNO_INICIAR_PARTIDA);
        this.INTERPRETADOR.cadastrarFilaDeRetorno("desistirDeProcurarPartida", this.FILA_RETORNO_DESISTIR_DE_PROCURAR_PARTIDA);
        this.INTERPRETADOR.cadastrarFilaDeRetorno("encerrarPartida", this.FILA_RETORNO_ENCERRAR_PARTIDA);
    }

    @Override
    public void receberMensagem(byte[] mensagem) {
        if(mensagem == null) {
           Logger.registrar(ERRO, new String[]{"GERENCIADOR_DE_CLIENTE"}, "Mensagem nula recebida. A mensagem sera ignorada.");
           return;
        }
        
        this.INTERPRETADOR.interpretar(mensagem);  
    }
    
    @Override
    protected void devolverRetorno(byte[] mensagemRetorno) {
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagemRetorno);
    }
    
    /* ########################### CHAMADAS DE RPC ########################## */
    
    @Override
    public void iniciarSessao(String nome_jogador) {
        byte[] mensagem = this.INTERPRETADOR.codificarIniciarSessao(nome_jogador);
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
    }
    
    @Override
    public boolean iniciarPartida() {  
        byte[] mensagem = this.INTERPRETADOR.codificarIniciarPartida();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        this.GERENCIADOR_CONEXAO_UDP.iniciarPedidoDeAberturaUDP();
        
        return (Boolean) this.FILA_RETORNO_INICIAR_PARTIDA.remover();
    }
    
    @Override
    public boolean desistirDeProcurarPartida() {
        byte[] mensagem = this.INTERPRETADOR.codificarDesistirDeProcurarPartida();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        this.GERENCIADOR_CONEXAO_UDP.iniciarFechamentoConexaoUDP();
        
        return (Boolean) this.FILA_RETORNO_DESISTIR_DE_PROCURAR_PARTIDA.remover();
    }
    
    @Override
    public boolean encerrarPartida() {
        byte[] mensagem = this.INTERPRETADOR.codificarEncerrarPartida();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
        this.GERENCIADOR_CONEXAO_UDP.iniciarFechamentoConexaoUDP();
        
        return (Boolean) this.FILA_RETORNO_ENCERRAR_PARTIDA.remover();
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
    public void encerrarSessao() {
        byte[] mensagem = this.INTERPRETADOR.codificarEncerrarSessao();
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
    }
    
    @Override
    protected LinkedList<Comando> criarComandosNecessarios() {
        
        LinkedList<Comando> listaDeComandos = new LinkedList<>();

        // Comandos do controlador de partida
        listaDeComandos.add(new NovoQuadro("novoQuadro", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new ExibirTelaSessao("exibirTelaSessao", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new ExibirTelaBusca("exibirTelaBusca", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new ExibirTelaJogo("exibirTelaJogo", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new ExibirTelaInicio("exibirTelaInicio", this.CONTROLADOR_DE_PARTIDA));
        
        listaDeComandos.add(new Perdeu("perdeu", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new Ganhou("ganhou", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new Empatou("empatou", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new AdversarioSaiu("adversarioSaiu", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new FalhaAoLogar("falhaAoLogar", this.CONTROLADOR_DE_PARTIDA));
        listaDeComandos.add(new Falha("falha", this.CONTROLADOR_DE_PARTIDA));
        
        listaDeComandos.add(new ProcurandoPartida("procurandoPartida", this.CONTROLADOR_DE_PARTIDA));
        
        // Comandos do gerenciador de UDP
        listaDeComandos.add(new ComandoExibirMensagem("exibirMensagem"));
        listaDeComandos.add(new AtenderPedidoInicioDeAberturaUDP("atenderPedidoInicioDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new ContinuarAberturaUDP("continuarAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new FecharConexaoUDP("fecharConexaoUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarFechamentoConexaoUDP("iniciarFechamentoConexaoUDP", this.GERENCIADOR_CONEXAO_UDP));
        listaDeComandos.add(new IniciarPedidoDeAberturaUDP("iniciarPedidoDeAberturaUDP", this.GERENCIADOR_CONEXAO_UDP));
        
        return listaDeComandos;
    }
    
}