package stub.comando.jogador;

import jogo.Jogador;

public class AndarParaDireita extends ComandoJogador {
    
    public AndarParaDireita(String codigo, Jogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.andarParaDireita();
    }
    
}