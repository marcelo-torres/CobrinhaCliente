package comunicacao;

import static comunicacao.Comunicador.TipoMensagem.FECHAR_CONEXAO;
import static comunicacao.Comunicador.TipoMensagem.PEDIR_FECHAMENTO_CONEXAO;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Gerencia uma comunicacao TCP entre o cliente e o servidor. As mensagens a
 * serem enviadas sao salvas em uma lista ligada thread-safe.
 */
public class ComunicadorTCP extends Comunicador implements Closeable {
    
    /**
     * Responsavel pelo recebimento de mensagens. Quando uma mensagem eh
     * recebida encaminha para o ReceptorDeMensagem associado.
     */
    private static class Receptor implements Runnable, Closeable { 
    
        private final ObjectInputStream ENTRADA;
        private final FilaMonitorada<byte[]> FILA_RECEBIMENTO_MENSAGENS;
        private final Enviador ENVIADOR;
    
        private boolean executando;
        
        public Receptor(
                ObjectInputStream entrada, 
                FilaMonitorada<byte[]> filaDeRecebimentoDeMensagens,
                Enviador enviador) {
            
            this.ENTRADA = entrada;
            this.FILA_RECEBIMENTO_MENSAGENS = filaDeRecebimentoDeMensagens;
            this.ENVIADOR = enviador;
        }
        
        @Override
        public void run() {
            this.executando = true;
            while(this.emExecucao()) {   
                try {
                    TipoMensagem controle = (TipoMensagem) this.ENTRADA.readObject(); 
                    switch (controle) {
                        case FECHAR_CONEXAO:
                            this.pararExecucao();
                            continue;
                        case PEDIR_FECHAMENTO_CONEXAO:
                            this.pararExecucao();
                            this.ENVIADOR.enviarFechamentoDaConexao();
                            continue;
                        case RECEBER_MENSAGEM:
                            byte[] mensagem = (byte[]) this.ENTRADA.readObject();
                            this.FILA_RECEBIMENTO_MENSAGENS.adicionar(mensagem);
                            continue;
                        default:
                            throw new FalhaDeComunicacaoEmTempoRealException("Mensagem de controle desconhecida");
                    }
                } catch(EOFException eofe) { 
                    throw new FalhaDeComunicacaoEmTempoRealException("Conexão fechada: " + eofe.getMessage());
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                    throw new FalhaDeComunicacaoEmTempoRealException("Não foi possível receber a mensagem: " + ioe.getMessage());
                } catch(ClassNotFoundException cnfe) {
                    throw new RuntimeException("Classe não encontrada: " + cnfe.getMessage());
                }
            }
        }
        
        public synchronized boolean emExecucao() {
            return this.executando;
        }
        
        public synchronized void pararExecucao() {
            this.executando = false;
        }

        @Override
        public void close() throws IOException {
            this.ENTRADA.close();
        }
    }
    
    
    /**
     * Faz o envio da mensagem para o destino. O metodo run() executa enquanto
     * houverem mensagens na fila aguardando para serem enviadas. Tambem eh
     * possivel que o metodo run() seja interrompido pelos metodo
     * pararExecucao() ou close().
     */
    private static class Enviador implements Runnable, Closeable {
        
        private final ObjectOutputStream SAIDA;
        private final FilaMonitorada<byte[]>  FILA_ENVIO_MENSAGENS;
        
        private boolean executando;
        
        public Enviador(
                ObjectOutputStream saida, 
                FilaMonitorada<byte[]> filaDeMensagensParaEnviar) {
            
            this.SAIDA = saida;
            this.FILA_ENVIO_MENSAGENS = filaDeMensagensParaEnviar;
        }
        
        @Override
        public void run() {
            this.executando = true;
            
            while(this.emExecucao()) {    
                byte[] mensagem = this.FILA_ENVIO_MENSAGENS.remover();
                if(mensagem != null) {
                    try {
                        this.SAIDA.writeObject(TipoMensagem.RECEBER_MENSAGEM);
                        this.SAIDA.writeObject(mensagem);
                        this.SAIDA.flush();
                    } catch(IOException ioe) {
                        throw new FalhaDeComunicacaoEmTempoRealException("Não foi possível enviar a mensagem: " + ioe.getMessage());
                    }
                }
            }
        }
        
        public synchronized boolean emExecucao() {
            return this.executando;
        }
        
        public synchronized void pararExecucao() {
            this.executando = false;
        }
        
        @Override
        public void close() throws IOException {
            this.SAIDA.close();
        }
        
        public void enviarPedidoFechamentoDaConexao() throws IOException {
            this.SAIDA.writeObject(PEDIR_FECHAMENTO_CONEXAO);
            this.SAIDA.flush();
        }
        
        public void enviarFechamentoDaConexao() throws IOException {
            this.SAIDA.writeObject(FECHAR_CONEXAO);
            this.SAIDA.flush();
        }
    }
    
    /* ###################################################################### */
    
    private Socket socket;  
    
    private Thread threadReceptor;
    private Thread threadEnviador;
    private Receptor receptor;
    private Enviador enviador; 
    private UncaughtExceptionHandler GERENCIADOR_DE_EXCEPTION;
    
    public ComunicadorTCP(Modo modo,
            FilaMonitorada<byte[]> filaDeEnvioDeMensagens,
            FilaMonitorada<byte[]> filaDeRecebimentoDeMensagens,
            UncaughtExceptionHandler gerenciadorDeException) {

            super(Comunicador.Modo.SERVIDOR, filaDeEnvioDeMensagens, filaDeRecebimentoDeMensagens);
            
            if(gerenciadorDeException == null) {
                throw new IllegalArgumentException("O gerenciador de exception não pode ser nulo");
            }
            this.GERENCIADOR_DE_EXCEPTION = gerenciadorDeException;
    }
    
    
    @Override
    public void iniciar(InetAddress enderecoServidor, int portaServidor) throws IOException {
        Socket socketNovo = this.abrirSocket(enderecoServidor, portaServidor);
        this.carregarSocket(socketNovo);
        this.prepararThreadsDeComunicacao();
        this.threadEnviador.start();
        this.threadReceptor.start();
    }
    
    public void iniciar(Socket socketNovo) throws IOException {
        this.carregarSocket(socketNovo);
        this.prepararThreadsDeComunicacao();
        this.threadEnviador.start();
        this.threadReceptor.start();
    }
    
    public void encerrarConexao() throws IOException {
        this.enviador.enviarPedidoFechamentoDaConexao();
    }
    
    @Override
    public void close() throws IOException {
        this.enviador.close();
        this.receptor.close();
        this.socket.close();

        this.threadEnviador.interrupt();
        this.threadReceptor.interrupt();
    }
    
    @Override
    public synchronized void enviarMensagem(byte[] mensagem) {
        super.FILA_ENVIO_MENSAGENS.adicionar(mensagem);
    }

    
    private Socket abrirSocket(InetAddress enderecoServidor, int portaServidor) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(enderecoServidor, portaServidor);
        } catch (IOException ioe) {
            throw new IOException ("Nao eh possivel se conectar ao servidor", ioe);
        }
        return socket;
    }
    
    private void carregarSocket(Socket socket) {
        if(socket == null) {
            throw new IllegalArgumentException("O socket não pode ser nulo");
        }
        if(socket.isClosed()) {
            throw new IllegalArgumentException("O socket não pode estar fechado");
        }
        this.socket = socket;
    }
    
    private void prepararThreadsDeComunicacao() throws IOException {
        ObjectOutputStream saida = null;
        ObjectInputStream entrada = null;
        
        try {
            if(null == super.MODO) {
                throw new RuntimeException("Não é possível prepararar as threads de comunicacação");
            } else switch (super.MODO) {
                case SERVIDOR:
                    saida = new ObjectOutputStream(socket.getOutputStream());
                    entrada = new ObjectInputStream(socket.getInputStream());
                    break;
                case CLIENTE:
                    entrada = new ObjectInputStream(socket.getInputStream());
                    saida = new ObjectOutputStream(socket.getOutputStream());
                    break;
                default:
                    throw new RuntimeException("Não é possível prepararar as threads de comunicacação");
            }
        } catch(IOException ioe) {
            throw new IOException("Não é possível estabelecer a comunicação: " + ioe.getMessage());
        }
        
        this.enviador = new Enviador(saida, super.FILA_ENVIO_MENSAGENS);
        this.receptor = new Receptor(entrada, super.FILA_RECEBIMENTO_MENSAGENS, this.enviador);

        this.criarThreadEnviador();
        this.criarThreadReceptor();
    }
    
    private void criarThreadReceptor() {
        this.threadReceptor = new Thread(this.receptor);
        this.threadReceptor.setUncaughtExceptionHandler(GERENCIADOR_DE_EXCEPTION);
        this.threadReceptor.setName("Receptor_TCP");
    }
    
    private void criarThreadEnviador() {
        this.threadEnviador = new Thread(this.enviador);
        this.threadEnviador.setUncaughtExceptionHandler(GERENCIADOR_DE_EXCEPTION);
        this.threadEnviador.setName("Enviador_TCP");
    }
}