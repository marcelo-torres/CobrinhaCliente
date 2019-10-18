package model.agentes;


public interface IJogador{
    
    public void iniciarPartida();
    public void desistirDeProcurarPartida();
    public void encerrarPartida();
    
    public void andarParaCima();
    public void andarParaBaixo();
    public void andarParaEsquerda();
    public void andarParaDireita();
    
    
    public void setNomeJogador(String nome_jogador);

    public void saindo();

}
