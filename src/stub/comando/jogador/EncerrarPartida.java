package stub.comando.jogador;

import stub.comando.Parametros;
import model.agentes.IControladorGeralVisaoStubCliente;

public class EncerrarPartida extends ComandoJogador {
    
    public EncerrarPartida(String codigo, IControladorGeralVisaoStubCliente jogador) {
        super(codigo, false, jogador);
    }

    @Override
    public Object executar(Parametros parametros) {
        super.JOGADOR.encerrarPartida();
        return null;
    }
}