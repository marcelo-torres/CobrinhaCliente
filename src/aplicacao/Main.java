import controller.ControladorGeral;
import controller.GerenciadorDeTelas;
import model.Jogador;

public class Main {
    
    public static void main(String[] args) {
        Jogador jg1 = new Jogador();
        Jogador jg2 = new Jogador();
        
        ControladorGeral controladorGeral = new ControladorGeral();
        
        GerenciadorDeTelas jogo1 = new GerenciadorDeTelas(controladorGeral, jg1);
        
        GerenciadorDeTelas jogo2 = new GerenciadorDeTelas(controladorGeral, jg2);
        
    }
}
