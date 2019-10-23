package controller;

import javax.swing.JOptionPane;
import localizacoes.ILocal;
import model.send.Arena;

public class GerenciadorDeTelas{
    ControladorTelaBusca ctr_busca;
    ControladorTelaJogo ctr_jogo;
    ControladorTelaInicio ctr_inicio;
    ControladorTelaSessao ctr_sessao;
    ControladorGeral controlador;
    ILocal atual;
    
    public GerenciadorDeTelas(ControladorGeral ctr){
        controlador = ctr;
        ctr_busca = new ControladorTelaBusca(this);
        ctr_jogo = new ControladorTelaJogo(this);
        ctr_inicio = new ControladorTelaInicio(this);
        ctr_sessao = new ControladorTelaSessao(this);
        ctr_inicio.inicializarTelaInicio();
    }
    
    public void exibirSessao(){
        ctr_inicio.desativaTela();
        ctr_sessao.inicializarTelaSessao();
        atual = ctr_sessao;
    }
    
    public void exibirInicio(){
        ctr_sessao.desativaTela();
        ctr_inicio.inicializarTelaInicio();
        atual = ctr_inicio;
    }
    
    public void exibirBusca(){
        ctr_sessao.desativaTela();
        ctr_busca.inicializarTelaBusca();
        atual = ctr_busca;
    }
    
    public void exibirJogo(){
        ctr_busca.desativaTela();
        ctr_jogo.inicializarTelaJogo();
        atual = ctr_jogo;
    }

    public void finalizarJogo(){
        //ctr_jogo.desativaTela();
        //ctr_sessao.inicializarTelaSessao();
        //atual = ctr_sessao;
    }
    
    public void novoQuadro(Arena arena){
        if(atual == ctr_jogo){
            ctr_jogo.novoQuadro(arena);
        }
    }

    public void falha(String mensagemTextual) {
        JOptionPane.showMessageDialog(null, mensagemTextual);
    }
}
