package controller;

import model.agentes.IJogador;
import model.send.Arena;

public class ControladorGeral {
    
    IJogador jogador;
    private String nomeJogador;
    GerenciadorDeTelas gerenciador;

    public ControladorGeral(IJogador jg) {
        jogador = jg;
        gerenciador = new GerenciadorDeTelas(this);
    }
        
    public void novoQuadro(Arena arena){
        gerenciador.novoQuadro(arena);
    }
    
    public void cima(){
        jogador.andarParaCima();
    }
    
    public void baixo(){
        jogador.andarParaBaixo();
    }
    
    public void esquerda(){
        jogador.andarParaEsquerda();
    }
    
    public void direita(){
        jogador.andarParaDireita();
    }
    
    public void setNomeJogador(String nome_jogador) {
        this.nomeJogador = nome_jogador;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }
}