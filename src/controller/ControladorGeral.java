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
    
    public void iniciarSessao(String nome_jogador) {
        this.nomeJogador = nome_jogador;
        jogador.iniciarSessao(nome_jogador);
    }
    
    public void encerrarSessao(){
        jogador.encerrarSessao();
    }
    
    public String getNomeJogador() {
        return nomeJogador;
    }
    
    public void procurarPartida(){
        jogador.iniciarPartida();
    }
    
    public void desistirDeProcurarPartida(){
        jogador.desistirDeProcurarPartida();
    }
    
    public void encerrarPartida(){
        jogador.encerrarPartida();
    }

    public void exibirTelaSessao(){
        gerenciador.exibirSessao();
        gerenciador.ctr_inicio.desativaTela();
    }

    public void exibirTelaBusca(){
        gerenciador.exibirBusca();
        gerenciador.ctr_sessao.desativaTela();
    }

    
    public void exibirTelaJogo(){
        gerenciador.exibirJogo();
        gerenciador.ctr_busca.desativaTela();
    }
    
    
    public void exibirTelaInicio(){
        gerenciador.exibirInicio();
        gerenciador.ctr_jogo.desativaTela();
    }
}
