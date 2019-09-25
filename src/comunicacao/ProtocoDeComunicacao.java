package comunicacao;

import comunicacao.Comunicador.TipoMensagem;

public class ProtocoDeComunicacao {
    
    private final byte[] MENSAGEM;
    
    private TipoMensagem tipoMensagem;
    private byte[] mensagem;
    
    public ProtocoDeComunicacao(byte[] mensagem) {
        this.MENSAGEM = mensagem;
        this.processarMensagem(mensagem);
    }
    
    public TipoMensagem getTipoMensagem() {
        return this.tipoMensagem;
    }
    
    public byte[] getMensagem() {
        return this.mensagem;
    }
    
    private void processarMensagem(byte[] mensagem) {
        throw new UnsupportedOperationException("tem q implementar");
    }
    
    public static byte[] prepararMensagemControle(TipoMensagem tipoMensagem, byte[] mensagem) {
        throw new UnsupportedOperationException("tem q implementar");
    }
}
