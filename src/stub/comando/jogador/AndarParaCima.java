package stub.comando.jogador;

import stub.comando.Parametros;
import model.agentes.IControladorGeralVisaoStubCliente;

public class AndarParaCima extends ComandoJogador {
    
    public AndarParaCima(String codigo, IControladorGeralVisaoStubCliente jogador) {
        super(codigo, false, jogador);
    }

    @Override
    public Object executar(Parametros parametros) {
        super.JOGADOR.andarParaCima();
        return null;
    }
}