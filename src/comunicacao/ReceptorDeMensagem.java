package comunicacao;

public interface ReceptorDeMensagem<T> {
    
    public void receberMensagemTCP(T mensagem);
    
    public void receberMensagemUDP(T mensagem);
}
