package controller;

import localizacoes.ILocal;
import model.send.Arena;

public class GerenciadorDeTelas{
    ControladorTelaBusca ctr_busca;
    ControladorTelaJogo ctr_jogo;
    ControladorTelaInicio ctr_inicio;
    ControladorGeral controlador;
    ILocal atual;
    
    public GerenciadorDeTelas(ControladorGeral ctr){
        ctr_busca = new ControladorTelaBusca(this);
        ctr_jogo = new ControladorTelaJogo(this);
        ctr_inicio = new ControladorTelaInicio(this);
        ctr_inicio.inicializarTelaInicio();
        controlador = ctr;
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
    
    public void novoQuadro(Arena arena){
        if(atual == ctr_jogo){
            ctr_jogo.novoQuadro(arena);
        }
    }
}
