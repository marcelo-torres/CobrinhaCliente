package stub.comando.jogador;

import jogo.Jogador;

public class AndarParaEsquerda extends ComandoJogador {
    
    public AndarParaEsquerda(String codigo, Jogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.andarParaEsquerda();
    }
    
}