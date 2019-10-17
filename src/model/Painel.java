 package model;

import model.send.Arena;
import model.send.Alimento;
import model.send.Cobra;
import controller.ControladorTelaJogo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("Serial")

public class Painel extends JPanel implements KeyListener {
    
    // Controlador
    private final ControladorTelaJogo controlador;
    
    // Render
    private Graphics2D g2d;
    private BufferedImage image;
        
    // Game Stuff
    Arena arena;
    Pixel cabeca1;
    ArrayList<Pixel> cobra1;
    Pixel cabeca2;
    ArrayList<Pixel> cobra2;
    ArrayList<Pixel> alimentos;

    int jogadorAtual;
    
    public Painel(Arena ar, ControladorTelaJogo ctr) {
        setPreferredSize(new Dimension(ar.getLargura(), ar.getAltura()));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        setBackground(new Color(76, 42, 97));
        arena = ar;
        controlador = ctr;
    }
    
    public void setJogadorAtual(int numeroJogador){
        jogadorAtual = numeroJogador;
    }
    
    public void atualizaPainel(){
        if(jogadorAtual != 1){
            Color c = arena.getCobra1().getCor();
            arena.getCobra1().setCor(arena.getCobra2().getCor());
            arena.getCobra2().setCor(c);
        }
        setCobras();
        setAlimentos();
        requestRender();
    }
    
    public void setCobras(){
        cobra1 = new ArrayList<>();
        cobra2 = new ArrayList<>();
        
        Cobra cobra = (Cobra)arena.getCobra1();
        
        cabeca1 = new Pixel(arena.getTamanho());
        cabeca1.setColor(cobra.getCor());
        cabeca1.setPosition(cobra.getPosicoes().get(0)[0], cobra.getPosicoes().get(0)[1]);
        cobra1.add(cabeca1);
        
        for(int i = 1; i < cobra.getPosicoes().size(); i++){
            Pixel pixel = new Pixel(arena.getTamanho());
            pixel.setColor(cobra.getCor());
            pixel.setPosition(cobra.getPosicoes().get(i)[0], cobra.getPosicoes().get(i)[1]);
            cobra1.add(pixel);
        }
        
        cobra = (Cobra)arena.getCobra2();

        cabeca2 = new Pixel(arena.getTamanho());
        cabeca2.setColor(cobra.getCor());
        cabeca2.setPosition(cobra.getPosicoes().get(0)[0], cobra.getPosicoes().get(0)[1]);
        cobra2.add(cabeca2);
        
        for(int i = 1; i < cobra.getPosicoes().size(); i++){
            Pixel pixel = new Pixel(arena.getTamanho());
            pixel.setColor(cobra.getCor());
            pixel.setPosition(cobra.getPosicoes().get(i)[0], cobra.getPosicoes().get(i)[1]);
            cobra2.add(pixel);
        }
    }
    
    public void setAlimentos(){
        alimentos = new ArrayList<>();
        
        for(int i = 0; i < arena.getAlimentos().size(); i++){
            Alimento alimento = (Alimento)arena.getAlimentos().get(i);
            
            Pixel pixel = new Pixel(arena.getTamanho());            
            pixel.setColor(alimento.getCor());
            pixel.setPosition(alimento.getX(), alimento.getY());
            alimentos.add(pixel);
        }
    }

    public void iniciarPainel() {
        image = new BufferedImage(arena.getLargura(), arena.getLargura(), BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
    }
    
    private void requestRender() {
        render(g2d);
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }
    
    public void render(Graphics2D g2d) {
        g2d.setColor(new Color(69, 42, 84));
        g2d.fillRect(0, 0, arena.getLargura(), arena.getAltura());
        
        g2d.setColor(cabeca1.getColor());
        for(Pixel e : cobra1) {
            e.render(g2d);
        }
        
        g2d.setColor(cabeca2.getColor());
        for(Pixel e : cobra2) {
            e.render(g2d);
        }
        
        for(Pixel e : alimentos) {
            g2d.setColor(e.getColor());
            e.render(g2d);
        }
        
    }
    
    public void escreverMensagemDerrota(){
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(new Color(176, 28, 28));
        g2d.drawString("Você foi derrotado! Bua bua bua :c\nClique no botão \"Sair\" para voltar à tela inicial e jogar novamente." , 200, 200);
    }
    
    public void escreverMensagemVitoria() {
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(new Color(76, 176, 56));
        g2d.drawString("Temos um vencedor! Você é o bixão mesmo hein!\nClique no botão \"Sair\" para voltar à tela inicial e jogar novamente." , 200, 200);
    }
    
    public void escreverMensagemEmpate() {
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(new Color(126, 138, 118));
        g2d.drawString("Empate! Nenhuma cobra foi a maioral.\nClique no botão \"Sair\" para voltar à tela inicial e jogar novamente." , 200, 200);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(controlador.isGameOver() || !controlador.isStarted()) return;

        int k = e.getKeyCode();
        
        switch(k){
            case KeyEvent.VK_UP:
                controlador.up();
                break;
            case KeyEvent.VK_DOWN:
                controlador.down();
                break;
            case KeyEvent.VK_RIGHT:
                controlador.right();
                break;
            case KeyEvent.VK_LEFT:
                controlador.left();
                break;
            
            case KeyEvent.VK_W:
                controlador.up();
                break;
            case KeyEvent.VK_S:
                controlador.down();
                break;
            case KeyEvent.VK_D:
                controlador.right();
                break;
            case KeyEvent.VK_A:
                controlador.left();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
