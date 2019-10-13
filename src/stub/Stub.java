package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import static Logger.Logger.Tipo.INFO;
import java.io.Closeable;
import java.net.InetAddress;
import stub.comunicacao.Comunicador;
import stub.comunicacao.Mensageiro;

public abstract class Stub implements Closeable {
    
    private final GerenciadorDeException GERENCIADOR_DE_EXCEPTION;
    protected final Mensageiro MENSAGEIRO;
    
    private Receptor receptor;
    private Thread threadDeRecepcao;
    
    public Stub(Comunicador.Modo modo,
                int portaEscutarUDP,
                InetAddress enderecoDoServidor,
                int portaTCPDoServidor) {
        
        this.GERENCIADOR_DE_EXCEPTION = new ControladorDeConexao.GerenciadorDeException(this);
        
        this.MENSAGEIRO = new Mensageiro(
                modo,
                portaEscutarUDP,
                enderecoDoServidor,
                portaTCPDoServidor,
                this.GERENCIADOR_DE_EXCEPTION);
    }
    
    public abstract void receberMensagem(byte[] mensagem);
    
    @Override
    public void close() {
        this.MENSAGEIRO.close();
        this.receptor.parar();
        this.threadDeRecepcao.interrupt();
    }
    
    
    /* ###################################################################### */
    
    protected void prepararThreadDeEntrega() {
        this.receptor = new Receptor(this, this.MENSAGEIRO);
        this.threadDeRecepcao = new Thread(this.receptor);
        this.threadDeRecepcao.setName("Entrega_Mensagem");
    }
    
    protected void iniciarServicoDeRecepcao() {
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
        
        protected final Stub STUB;
        protected final Mensageiro MENSAGEIRO;
        
        protected boolean emEexecucao = false;
        
        public Receptor(Stub stub, Mensageiro mensageiro) {
            this.STUB = stub;
            this.MENSAGEIRO = mensageiro;
        }
        
        
        @Override
        public void run() {
            this.emEexecucao = true;
            while(this.emExecucao()) {
                byte[] mensagem = this.MENSAGEIRO.removerFilaRecebimento();
                if(mensagem != null) {
                    this.STUB.receberMensagem(mensagem);
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
    
    
    public class GerenciadorDeConexaoUDPRemota {
    
    }
    
    
     /**
     * Gerenciador de exceptions nao capturadas por metodos, isto eh, que
     * ocorreram em outras threads.
     */
    public class GerenciadorDeException implements Thread.UncaughtExceptionHandler {
    
        private final Stub STUB;
        
        public GerenciadorDeException(Stub controlador) {
            this.STUB = controlador;
        }
        
        @Override
        public void uncaughtException(Thread th, Throwable ex) {
            Logger.registrar(ERRO, new String[]{"STUB"}, "Erro na comunicacao: " + ex.getMessage());
            Logger.registrar(INFO, new String[]{"STUB"}, "Encerrando devido a falha de comunicacao");
            this.STUB.close();
        }
        
    }
}
