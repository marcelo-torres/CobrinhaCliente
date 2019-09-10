package comunicacao;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Comunicador {
    
    /**
     * Uma fila de mensagens que garante o acesso somente a uma thread.
     * @param <T> Tipo da mensagem
     */
    protected static class GerenciadorDeFilaDeMensagens {
        
        private final List<byte[]> MENSAGENS;
        private final int TAMANHO_MAXIMO;
        
        public GerenciadorDeFilaDeMensagens(int tamanhoMaximo) {
            this.MENSAGENS = Collections.synchronizedList(new LinkedList<byte[]>());
            this.TAMANHO_MAXIMO = tamanhoMaximo;
        }
        
        public synchronized boolean inserir(byte[] mensagem) {
            if(this.MENSAGENS.size() == this.TAMANHO_MAXIMO) {
                return false;
            }
            this.MENSAGENS.add(mensagem);
            return true;
        }
        
        public synchronized byte[] remover() {
            if(this.MENSAGENS.size() <= 0) {
                return null;
            }
            return this.MENSAGENS.remove(0);
        }
        
        public synchronized int tamanho() {
            return this.MENSAGENS.size();
        } 
    }
    
    public enum Modo {
        SERVIDOR(1), CLIENTE(2);
        
        private final int MODO;
        
        Modo(int valorOpcao){
            MODO = valorOpcao;
        }
        
        public int getModo() {
            return this.MODO;
        }
    }
    
    protected enum TipoMensagem {
        FECHAR_CONEXAO(0), PEDIR_FECHAMENTO_CONEXAO(1), RECEBER_MENSAGEM(2);
        
        private final int TIPO_MENSAGEM;
        
        TipoMensagem(int tipoMensagem) {
            TIPO_MENSAGEM = tipoMensagem;
        }
        
        public int getTipoMensagem() {
            return this.TIPO_MENSAGEM;
        }
    }

    protected final Modo MODO;
    protected final FilaMonitorada<byte[]> FILA_ENVIO_MENSAGENS;
    protected final FilaMonitorada<byte[]> FILA_RECEBIMENTO_MENSAGENS;
    
    public Comunicador(Modo modo,
            FilaMonitorada<byte[]> filaDeEnvioDeMensagens,
            FilaMonitorada<byte[]> filaDeRecebimentoDeMensagens) {
        
        if(modo == null
                || filaDeEnvioDeMensagens == null
                || filaDeRecebimentoDeMensagens == null) {
            throw new IllegalArgumentException("Não é possível criar o comunicador, parâmetro nulo");
        }
        
        this.MODO = modo;
        this.FILA_ENVIO_MENSAGENS = filaDeEnvioDeMensagens;
        this.FILA_RECEBIMENTO_MENSAGENS = filaDeRecebimentoDeMensagens;
    }
    
    public abstract void iniciar(InetAddress enderecoServidor, int portaServidor) throws IOException;
    
    public abstract void enviarMensagem(byte[] mensagem);
}
