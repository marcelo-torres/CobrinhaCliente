package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import static Logger.Logger.Tipo.INFO;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import jogo.ErroApresentavelException;
import stub.comunicacao.Comunicador;
import stub.comunicacao.Mensageiro;

/**
 * blablablala escrever
 */
public class ControladorDeConexao implements nucleo.Jogador, Closeable {
    
    private final GerenciadorDeException GERENCIADOR_DE_EXCEPTION;
    private final Mensageiro MENSAGEIRO;
    private final InterpretadorCliente INTERPRETADOR = new InterpretadorCliente();
    
    private Receptor receptor;
    private Thread threadDeRecepcao;
    
    public ControladorDeConexao(
            int portaEscutarUDP,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor,
            int portaUDPDoServidor) {
        
        this.GERENCIADOR_DE_EXCEPTION = new GerenciadorDeException(this);
        
        this.MENSAGEIRO = new Mensageiro(
                Comunicador.Modo.CLIENTE,
                portaEscutarUDP,
                enderecoDoServidor,
                portaTCPDoServidor,
                portaUDPDoServidor,
                this.GERENCIADOR_DE_EXCEPTION);
    }
    
    public void iniciar() {
        try {
            this.MENSAGEIRO.iniciarTCP();
            this.MENSAGEIRO.iniciarUDP(1235); // nao eh aqui mas vai ficar aqui por enquanto
            this.iniciarServicoDeRecepcao();
        } catch(IOException ioe) {
            Logger.registrar(ERRO, new String[]{"JOGADOR"}, "Erro ao tentar iniciar a comunicaca: " + ioe.getMessage(), ioe);
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
        System.out.println("[Interpretador] Mensagem recebida: " + new String(mensagem));
    }
    
    
    
    /* ########################### CHAMADAS DE RPC ########################## */
    
    @Override
    public void iniciarPartida() {
        String mensagem = "[TCP] Jogador chama iniciarPartida()";
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
        if(!this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            try {
                this.MENSAGEIRO.definirDestinatario(InetAddress.getLocalHost(), 1235);
            } catch(IOException ioe) {
                Logger.registrar(ERRO, new String[]{"JOGADOR"}, "Nao foi possivel iniciar a partida devido a uma falha ao iniciar a comunicacao UDP: " + ioe.getMessage(), ioe);
                throw new ErroApresentavelException("Não foi possível iniciar a partida. Erro ao estabelecer uma conexão com o servidor");
            }
        }
    }

    @Override
    public void desistirDeProcurarPartida() {
        String mensagem = "[TCP] Jogador chama desistirDeProcurarPartida()";
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
        String mensagem = "[TCP] Jogador chama encerrarPartida()";
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
        if(this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            this.MENSAGEIRO.close();
        }
    }

    @Override
    public void andarParaCima() {
        String mensagem = "[UDP] Jogador chama andarParaCima()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaBaixo() {
        String mensagem = "[UDP] Jogador chama andarParaBaixo()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaEsquerda() {
        String mensagem = "[UDP] Jogador chama andarParaEsquerda()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaDireita() {
        String mensagem = "[UDP] Jogador chama andarParaDireita()";
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }
    
    /* ###################################################################### */
    
    
    
    
    
    public void enviarMensagemTCPLembrarDeApagarEsteMetodo(byte[] mensagem) {
        this.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
    }
    
    public void enviarMensagemUDPLembrarDeApagarEsteMetodo(byte[] mensagem) {
        this.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }
    
    public void algumMetodoQueVaiPrecisarUsarConexaoUDP() {
        try {
            this.MENSAGEIRO.iniciarUDP(1235);
        } catch(IOException ioe) {
            // Transparencia total eh impossivel
            throw new RuntimeException("Nao foi possivel executar o metodo algumMetodoQueVaiPrecisarUsarConexaoUDP");
        }
    }
    
    
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
