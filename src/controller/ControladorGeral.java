package controller;

import model.send.Arena;
import model.agentes.IControladorGeralVisaoStubCliente;
import model.agentes.IJogadorVisaoAplicacaoCliente;

public class ControladorGeral implements IControladorGeralVisaoStubCliente{
    
    IJogadorVisaoAplicacaoCliente jogador;
    private String nomeJogador;
    GerenciadorDeTelas gerenciador;

    public ControladorGeral(IJogadorVisaoAplicacaoCliente jg) {
        jogador = jg;
        gerenciador = new GerenciadorDeTelas(this);
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
        gerenciador.ctr_inicio.desativaTela();
    }

    @Override
    public void exibirTelaBusca(){
        gerenciador.exibirBusca();
        gerenciador.ctr_sessao.desativaTela();
    }

    
    @Override
    public void exibirTelaJogo(){
        gerenciador.exibirJogo();
        gerenciador.ctr_busca.desativaTela();
    }
    
    
    @Override
    public void exibirTelaInicio(){
        gerenciador.exibirInicio();
        gerenciador.ctr_jogo.desativaTela();
    }

    @Override
    public void perdeu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ganhou() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void empatou() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adversarioSaiu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void falhaAoLogar(String mensagemTextual) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void falha(String nome_inválido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void procurandoPartida() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
