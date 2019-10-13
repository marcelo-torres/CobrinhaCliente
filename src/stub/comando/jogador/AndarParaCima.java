package stub.comando.jogador;

import jogo.Jogador;

public class AndarParaCima extends ComandoJogador {
    
    public AndarParaCima(String codigo, Jogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.andarParaCima();
    }
    
}