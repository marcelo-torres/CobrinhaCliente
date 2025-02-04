package controller;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Painel;
import model.send.Arena;
import view.Jogo;

public class ControladorTelaJogo extends ControladorTela{

    Arena arena;
    Painel painel;
    
    private boolean gameover, started;
    
    public ControladorTelaJogo(GerenciadorDeTelas grc) {
        gerenciador = grc;
        started = false;
    }
    
    public void inicializarTelaJogo(){
        tela = new Jogo(this);
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);
        
        criarMapaDeComponentes();

        JPanel jpGame = ((JPanel)getComponente("jpn_game"));
        jpGame.setLayout(new CardLayout());
        
        arena = new Arena(500, 1000, 10);
        
        painel = new Painel(arena, this);
        
        jpGame.add(painel);

        ((JLabel)getComponente("lbl_nome")).setText("Olá, " + gerenciador.controlador.getNomeJogador());
        
        Iterator it = mapaDeComponentes.values().iterator();
        
        while(it.hasNext()){
            Component c = (Component)it.next();
            c.setEnabled(true);
        }
        
        tela.pack();
        
        tela.setVisible(true);
        
        painel.iniciarPainel();
        
        iniciarJogo();
        
        started = true;
        
    }
    
    public void iniciarJogo() {
        gameover = false;
        painel.atualizaPainel();
    }
    
    public void ganhou(){
        gameover = true;
        painel.escreverMensagemVitoria();
    }
    
    public void empatou(){
        gameover = true;
        painel.escreverMensagemEmpate();
    }
    
    public void perdeu(){
        gameover = true;
        painel.escreverMensagemDerrota();
    }
    
    public void up(){
        gerenciador.controlador.cima();
    }
    
    public void down(){
        gerenciador.controlador.baixo();
    }
    
    public void left(){
        gerenciador.controlador.esquerda();
    }
    
    public void right(){
        gerenciador.controlador.direita();
    }
    
    public void encerrar(){
        gerenciador.controlador.encerrarPartida();
    }
    
    public boolean isGameOver(){
        return gameover;
    }
    
    public boolean isStarted(){
        return started;
    }

    public void novoQuadro(Arena arena) {
        if(!started) return;
        this.arena = arena;
        painel.atualizaPainel();
    }
}