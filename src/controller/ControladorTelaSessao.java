package controller;

import javax.swing.JLabel;
import view.Sessao;

public class ControladorTelaSessao extends ControladorTela{

    public ControladorTelaSessao(GerenciadorDeTelas grc) {
        gerenciador = grc;
    }
    
    public void inicializarTelaSessao(){
        tela = new Sessao(this);
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);
        
        criarMapaDeComponentes();
        
        ((JLabel)getComponente("lbl_nome")).setText("Bem-vindo, " + gerenciador.controlador.getNomeJogador() + "!");
        
        tela.setVisible(true);
    }

    public void procurarPartida(){
        gerenciador.controlador.procurarPartida();
    }
    
    public void encerrarSessao(){
        gerenciador.controlador.encerrarSessao();
    }
}
