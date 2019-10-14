package controller;

import model.Jogador;

public class GerenciadorDeTelas{
    ControladorTelaBusca ctr_busca;
    ControladorTelaJogo ctr_jogo;
    ControladorTelaInicio ctr_inicio;
    ControladorGeral controlador;
    Jogador jogador;

    public GerenciadorDeTelas(ControladorGeral ctr, Jogador jg){
        ctr_busca = new ControladorTelaBusca(this);
        ctr_jogo = new ControladorTelaJogo(this);
        ctr_inicio = new ControladorTelaInicio(this);
        ctr_inicio.inicializarTelaInicio();
        controlador = ctr;
        jogador = jg;
    }
    
    public void exibirBusca(){
        ctr_busca.inicializarTelaBusca();
    }
    
    public void exibirJogo(){
        ctr_jogo.inicializarTelaJogo();
    }
    
    public void exibirInicio(){
        ctr_inicio.inicializarTelaInicio();
    }
}
