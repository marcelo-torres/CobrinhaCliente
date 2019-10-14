package stub.comando.jogador;

import model.agentes.IJogador;
import stub.comando.Parametros;

public class AndarParaDireita extends ComandoJogador {
    
    public AndarParaDireita(String codigo, IJogador jogador) {
        super(codigo, jogador);
    }

    @Override
    public void executar() {
        super.JOGADOR.andarParaDireita();
    }
    
    @Override
    public void definirParametros(Parametros parametros) {
       throw new UnsupportedOperationException("Nenhum parametro necessario");
    }
}