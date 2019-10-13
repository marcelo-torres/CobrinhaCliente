package stub.comando.jogador;

import jogo.Jogador;

public class DesistirDeProcurarPartida extends ComandoJogador {
    
    public DesistirDeProcurarPartida(String codigo, Jogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.desistirDeProcurarPartida();
    }
    
}