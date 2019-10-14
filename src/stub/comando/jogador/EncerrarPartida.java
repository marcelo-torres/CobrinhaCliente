package stub.comando.jogador;

import model.agentes.IJogador;
import stub.comando.Parametros;

public class EncerrarPartida extends ComandoJogador {
    
    public EncerrarPartida(String codigo, IJogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.encerrarPartida();
    }
    
    @Override
    public void definirParametros(Parametros parametros) {
       throw new UnsupportedOperationException("Nenhum parametro necessario");
    }
}