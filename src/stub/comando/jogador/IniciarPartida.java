package stub.comando.jogador;

import jogo.Jogador;

public class IniciarPartida extends ComandoJogador {
    
    public IniciarPartida(String codigo, Jogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.iniciarPartida();
    }
    
}