package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import static Logger.Logger.Tipo.INFO;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jogo.ErroApresentavelException;
import nucleo.ControladorCliente;
import stub.comunicacao.Comunicador;
import stub.comunicacao.Mensageiro;

/**
 * blablablala escrever
 */
public class ControladorDeConexao implements nucleo.Jogador, Closeable {
    
    private final Semaphore SEMAFORO_ATICAO_UDP = new Semaphore(0);
    
    private final ControladorCliente CONTROLADOR_CLIENTE;
    private final GerenciadorDeException GERENCIADOR_DE_EXCEPTION;
    private final Mensageiro MENSAGEIRO;
    private final InterpretadorCliente INTERPRETADOR = new InterpretadorCliente();
    
    private final InetAddress ENDERECO_DO_SERVIDOR;
    
    private Receptor receptor;
    private Thread threadDeRecepcao;
    
    private Pattern PADRAO_NUMERO = Pattern.compile("\\d+");
    private boolean hostProntoParaReceberUDP = false;
    
    
    
    public ControladorDeConexao(
            ControladorCliente controladorCliente,
            int portaEscutarUDP,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor) {
        
        this.CONTROLADOR_CLIENTE = controladorCliente;
        this.GERENCIADOR_DE_EXCEPTION = new GerenciadorDeException(this);
        
        this.MENSAGEIRO = new Mensageiro(
                Comunicador.Modo.SERVIDOR,
                enderecoDoServidor,
                portaTCPDoServidor,
                this.GERENCIADOR_DE_EXCEPTION);
        
        this.ENDERECO_DO_SERVIDOR = enderecoDoServidor;
        
        this.iniciar();
    }
    
    
    
    private void iniciar() {
        try {
            this.MENSAGEIRO.iniciarTCP();
            this.iniciarServicoDeRecepcao();
        } catch(IOException ioe) {
            Logger.registrar(ERRO, new String[]{"ControladorDeConexao"}, "Erro ao tentar iniciar a comunicaca: " + ioe.getMessage(), ioe);
            throw new ErroApresentavelException("Nao foi possivel iniciar a comunicacao com o servidor");
        }
    }
    
    @Override
    public void close() {
        this.MENSAGEIRO.close();
        this.receptor.parar();
        this.threadDeRecepcao.interrupt();
    }
    
    public void receberMensagem(byte[] mensagem) {
        if(mensagem == null) {
            System.out.println("[!] Mano, vc ta jogando uma mensagem nula no interpretador! O que vc tem na cabeça tiw? Programa direito zeh mane");
        }
        
        String mensagemInterpretada = this.INTERPRETADOR.interpretar(mensagem);
        
        String codigo = mensagemInterpretada.substring(0, 3);
        String conteudo = mensagemInterpretada.substring(4, mensagemInterpretada.length());
        
        switch(codigo) {
            case "MSG":
                System.out.println("[STUB] Mensagem recebida: " + conteudo);
                break;
            case "COM":
                if(conteudo.startsWith("UDP_ABRIR")) {
                    Matcher matcher = PADRAO_NUMERO.matcher(conteudo);
                    if(matcher.find()) {
                        String numero = matcher.group(0);
                        int portaDoOutroLado = Integer.valueOf(numero);
                        this.atenderPedidoInicioDeAberturaUDP(portaDoOutroLado);
                    } else {
                        throw new RuntimeException("Mensagem indecifravel");
                    }
                } else if(conteudo.startsWith("UDP_ABERTO")) {
                    Matcher matcher = PADRAO_NUMERO.matcher(conteudo);
                    if(matcher.find()) {
                        String numero = matcher.group(0);
                        int portaDoOutroLado = Integer.valueOf(numero);
                        this.continuarAberturaUDP(portaDoOutroLado);
                    } else {
                        throw new RuntimeException("Mensagem indecifravel");
                    }
                } else {
                    Logger.registrar(ERRO, new String[]{"STUB"}, "Comando desconhecido recebido: " + conteudo);
                }
                
                break;
            default:
                System.out.println("[STUB] Mensagem ESTRANHA recebida: " + mensagemInterpretada);
        }
    }
    
    
    private void iniciarPedidoDeAberturaUDP() {
        try {
            if(!this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
                this.MENSAGEIRO.iniciarUDP(-1);
                String mensagem = "COM UDP_ABRIR " + this.MENSAGEIRO.getPortaEscutaUDP();
                this.MENSAGEIRO.inserirFilaEnvioTCPNaFrente(mensagem.getBytes());
            }
        } catch(IOException ioe) {
            Logger.registrar(ERRO, new String[]{"INTERPRETADOR"}, "Erro ao tentar iniciar a comunicacao.", ioe);
            this.SEMAFORO_ATICAO_UDP.release();
            throw new RuntimeException("Nao foi possivel iniciar a comunicacao com o servidor");
        }
    }
    
    private void atenderPedidoInicioDeAberturaUDP(int portaUDPServidor) {
        try {
            if(!this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
                this.MENSAGEIRO.iniciarUDP(-1);
            }
            String mensagem = "COM UDP_ABERTO " + this.MENSAGEIRO.getPortaEscutaUDP();
            this.MENSAGEIRO.inserirFilaEnvioTCPNaFrente(mensagem.getBytes());
            this.MENSAGEIRO.definirDestinatario(ENDERECO_DO_SERVIDOR, portaUDPServidor);
            this.hostProntoParaReceberUDP = true;
        } catch(IOException ioe) {
            Logger.registrar(ERRO, new String[]{"INTERPRETADOR"}, "Erro ao tentar iniciar a comunicacao.", ioe);
            this.SEMAFORO_ATICAO_UDP.release();
            throw new RuntimeException("Nao foi possivel iniciar a comunicacao com o servidor");
        }
    }
    
    private void continuarAberturaUDP(int portaUDPServidor) {
        this.MENSAGEIRO.definirDestinatario(this.ENDERECO_DO_SERVIDOR, portaUDPServidor);
        this.hostProntoParaReceberUDP = true;
        this.SEMAFORO_ATICAO_UDP.release();
    }
    
    
    
    /* ########################### CHAMADAS DE RPC ########################## */
    
    @Override
    public void iniciarPartida() {
        String mensagem = "MSG [TCP] Jogador chama iniciarPartida()";
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());       
        
        try {
            this.iniciarPedidoDeAberturaUDP();
            this.SEMAFORO_ATICAO_UDP.acquire();
        } catch (InterruptedException e) {
            Logger.registrar(ERRO, new String[]{"CONTROLADOR_DE_CONEXAO"}, "Espera no iniciarPartida() interrompida", e);
        }
    }

    @Override
    public void desistirDeProcurarPartida() {
        String mensagem = "MSG [TCP] Jogador chama desistirDeProcurarPartida()";
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
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
        String mensagem = "MSG [TCP] Jogador chama encerrarPartida()";
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
        //if(this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            this.MENSAGEIRO.close();
        //}
    }

    @Override
    public void andarParaCima() {
        String mensagem = "MSG [UDP] Jogador chama andarParaCima()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaBaixo() {
        String mensagem = "MSG [UDP] Jogador chama andarParaBaixo()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaEsquerda() {
        String mensagem = "MSG [UDP] Jogador chama andarParaEsquerda()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaDireita() {
        String mensagem = "MSG [UDP] Jogador chama andarParaDireita()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }
    
    /* ###################################################################### */
    
    private void prepararThreadDeEntrega() {
        this.receptor = new Receptor(this, this.MENSAGEIRO);
        this.threadDeRecepcao = new Thread(this.receptor);
        this.threadDeRecepcao.setName("Entrega_Mensagem");
    }
    
    private void iniciarServicoDeRecepcao() {
        if(this.receptor == null || this.threadDeRecepcao == null) {
            this.prepararThreadDeEntrega();
            this.threadDeRecepcao.start();
        }
    }
    
    
    
    /* ############################## CLASSES ############################### */
    
    /**
     * Possui como funcao retirar mensagens da fila de recebimento e entregar
     * ao ControladorDeConexao.
     */
    public static class Receptor implements Runnable {
        
        protected final ControladorDeConexao CONTROLADOR_DE_CONEXAO;
        protected final Mensageiro MENSAGEIRO;
        
        protected boolean emEexecucao = false;
        
        public Receptor(ControladorDeConexao controladorDeConexao, Mensageiro mensageiro) {
            this.CONTROLADOR_DE_CONEXAO = controladorDeConexao;
            this.MENSAGEIRO = mensageiro;
        }
        
        
        @Override
        public void run() {
            this.emEexecucao = true;
            while(this.emExecucao()) {
                byte[] mensagem = this.MENSAGEIRO.removerFilaRecebimento();
                if(mensagem != null) {
                    this.CONTROLADOR_DE_CONEXAO.receberMensagem(mensagem);
                }
            }
        }
        
        
        public synchronized boolean emExecucao() {
            return emEexecucao;
        }
        
        public synchronized void parar() {
            this.MENSAGEIRO.fecharFilaRecebimento();
            this.emEexecucao = false;
        }
    }
    
    /**
     * Gerenciador de exceptions nao capturadas por metodos, isto eh, que
     * ocorreram em outras threads.
     */
    public class GerenciadorDeException implements Thread.UncaughtExceptionHandler {
    
        private final ControladorDeConexao CONTROLADOR_DE_CONEXAO;
        
        public GerenciadorDeException(ControladorDeConexao controlador) {
            this.CONTROLADOR_DE_CONEXAO = controlador;
        }
        
        @Override
        public void uncaughtException(Thread th, Throwable ex) {
            Logger.registrar(ERRO, new String[]{"INTERPRETADOR"}, "Erro na comunicacao: " + ex.getMessage());
            Logger.registrar(INFO, new String[]{"INTERPRETADOR"}, "Encerrando devido a falha de comunicacao");
            this.CONTROLADOR_DE_CONEXAO.close();
        }
        
    }
}
