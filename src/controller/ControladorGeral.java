package controller;

import model.send.Arena;
import model.agentes.IControladorGeralVisaoStubCliente;
import model.agentes.IJogadorVisaoAplicacaoCliente;

public class ControladorGeral implements IControladorGeralVisaoStubCliente{
    
    IJogadorVisaoAplicacaoCliente jogador;
    private String nomeJogador;
    GerenciadorDeTelas gerenciador;

    public ControladorGeral() {
        this.gerenciador = new GerenciadorDeTelas(this);
    }
        
    @Override
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

    @Override
    public void exibirTelaSessao(){
        gerenciador.exibirSessao();
    }

    @Override
    public void exibirTelaBusca(){
        gerenciador.exibirBusca();
    }

    
    @Override
    public void exibirTelaJogo(){
        gerenciador.exibirJogo();
    }
    
    
    @Override
    public void exibirTelaInicio(){
        gerenciador.exibirInicio();
    }

    public void finalizarTelaJogo(){
        gerenciador.finalizarJogo();
    }
    
    @Override
    public void perdeu() {
        gerenciador.ctr_jogo.perdeu();
    }

    @Override
    public void ganhou() {
        gerenciador.ctr_jogo.ganhou();
    }

    @Override
    public void empatou() {
        gerenciador.ctr_jogo.empatou();
    }

    @Override
    public void adversarioSaiu() {
        gerenciador.ctr_jogo.ganhou();
        gerenciador.exibirSessao();    }

    @Override
    public void falhaAoLogar(String nome_invalido) {
        gerenciador.ctr_inicio.falhaAoLogar(nome_invalido);
    }

    @Override
    public void falha(String mensagemTextual) {
        gerenciador.falha(mensagemTextual);
    }

/*
    @Override
    public void procurandoPartida() {
       gerenciador.exibirBusca();
    }
*/

    @Override
    public void setJogador(IJogadorVisaoAplicacaoCliente jogador) {
        this.jogador = jogador;
    }

}
