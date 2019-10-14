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
    
    // Ações
    private boolean up, down, right, left, start;
    private boolean gameover;
    
    public ControladorTelaJogo(GerenciadorDeTelas grc) {
        gerenciador = grc;
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
        ((JLabel)getComponente("lbl_nome")).setText("Olá, " + gerenciador.jogador.getNome());
        
        Iterator it = mapaDeComponentes.values().iterator();
        
        while(it.hasNext()){
            Component c = (Component)it.next();
            c.setEnabled(true);
        }
        
        tela.pack();
        
        tela.setVisible(true);
    }
    
    public void finalizarTelaJogo(){
        gerenciador.exibirInicio();
        tela.setVisible(false);
        tela = null;
    }
    
    public void iniciarJogo() {
        gameover = false;
        painel.setFPS(10);
        painel.atualizaPainel();
    }
    
    public void update(){
        if(gameover){
            return;
        }
        int comando = 0;
        if(up){
            comando = 1;
        }
        
        if(down){
            comando = 2;
        }
        
        if(left && !up && !down){
            comando = 3;
        }
        
        if(right && !up && !down){
            comando = 4;
        }
        
        painel.atualizaPainel();
        
    }
    
    public void setArena(Arena ar){
        arena = ar;
    }
    
    public void setStart(boolean b){
        start = b;
    }
    
    public void setUp(boolean b){
        up = b;
    }
    
    public void setDown(boolean b){
        down = b;
    }
    
    public void setLeft(boolean b){
        left = b;
    }
    
    public void setRight(boolean b){
        right = b;
    }
    
    public boolean isGameOver(){
        return gameover;
    }
}