package stub.comando.jogador;

import stub.comando.Parametros;
import model.agentes.IControladorGeralVisaoStubCliente;

public class IniciarPartida extends ComandoJogador {
    
    public IniciarPartida(String codigo, IControladorGeralVisaoStubCliente jogador) {
        super(codigo, false, jogador);
    }

    @Override
    public Object executar(Parametros parametros) {
        super.JOGADOR.iniciarPartida();
        return null;
    }
}