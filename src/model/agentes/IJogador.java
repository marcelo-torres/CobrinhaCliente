package model.agentes;

import java.io.Closeable;
import localizacoes.ILocal;

public interface IJogador{
    
    public void iniciarPartida();
    public void desistirDeProcurarPartida();
    public void encerrarPartida();
    
    public void andarParaCima();
    public void andarParaBaixo();
    public void andarParaEsquerda();
    public void andarParaDireita();
    
    
    public void setNomeJogador(String nome_jogador);

}
