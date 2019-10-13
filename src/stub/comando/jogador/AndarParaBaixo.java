package stub.comando.jogador;

import jogo.Jogador;

public class AndarParaBaixo extends ComandoJogador {
    
    public AndarParaBaixo(String codigo, Jogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.andarParaBaixo();
    }
    
}