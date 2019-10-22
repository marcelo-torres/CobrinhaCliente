package controller;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import view.Busca;

public class ControladorTelaBusca extends ControladorTela{
    
    public ControladorTelaBusca(GerenciadorDeTelas grc) {
        gerenciador = grc;
    }
    
    public void inicializarTelaBusca(){
        tela = new Busca(this);
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);

        criarMapaDeComponentes();
        
        URL url = getClass().getResource("/view/img/loading.gif");
        Icon icon = new ImageIcon(url);
        
        ((JLabel)getComponente("lbl_loading")).setIcon(icon);
        
        tela.setVisible(true);
    }
    
    public void fecharPrograma(){
        gerenciador.controlador.desistirDeProcurarPartida();
    }
}
