package controller;

import javax.swing.JTextField;
import view.Inicio;

public class ControladorTelaInicio extends ControladorTela{

    public ControladorTelaInicio(GerenciadorDeTelas grc) {
        gerenciador = grc;
    }
    
    public void inicializarTelaInicio(){
        tela = new Inicio(this);
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);
        
        criarMapaDeComponentes();
        
        ((JTextField)getComponente("txt_nome")).setText(gerenciador.controlador.getNomeJogador());
        
        tela.setVisible(true);
    }

    public void finalizarTelaInicio(){
        gerenciador.controlador.setNomeJogador(((JTextField)getComponente("txt_nome")).getText());
        gerenciador.exibirBusca();
        tela.setVisible(false);
        tela = null;
    }
}
